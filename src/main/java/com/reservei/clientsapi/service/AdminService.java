package com.reservei.clientsapi.service;

import com.reservei.clientsapi.config.feign.UserClient;
import com.reservei.clientsapi.domain.dto.AdminDto;
import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.model.Admin;
import com.reservei.clientsapi.domain.model.Admin;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.AdminData;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.domain.record.UserData;
import com.reservei.clientsapi.exception.ApiCommunicationException;
import com.reservei.clientsapi.exception.ClientNotFoundException;
import com.reservei.clientsapi.exception.GenericException;
import com.reservei.clientsapi.exception.InactiveAccountException;
import com.reservei.clientsapi.repository.AdminRepository;
import com.reservei.clientsapi.service.clientvalidator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Consumer;

@Service
public class AdminService {
    final AdminRepository adminRepository;

    @Autowired
    UserClient userClient;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public AdminDto create(AdminData data) throws Exception {
        Admin admin = Admin.toAdmin(data);
        String password = generatePassword(data.password());
        UserData dataUser = new UserData(admin.getPublicId(),
                admin.getEmail(), password, admin.getRole());
        try {
            userClient.createUser(dataUser);
        } catch (Exception ex) {
            throw new ApiCommunicationException("Falha na comunicação com o serviço de usuários");
        }
        return AdminDto.toDto(adminRepository.save(admin));
    }

    public AdminDto findById(Long id) throws Exception {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Admin não encontrado para o id informado"));
        if (admin.getDeletedAt() != null) {
            throw new InactiveAccountException("Admin com a conta inativa");
        }
        return AdminDto.toDto(admin);
    }

    public AdminDto updateById(Long id, AdminData data) throws Exception {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Admin não encontrado para o id informado"));
        if (admin.getDeletedAt() != null) {
            throw new InactiveAccountException("Admin com a conta inativa");
        }
        Admin updatedAdmin = Admin.updateAdmin(admin, data);
        String password = generatePassword(data.password());
        try {
            UserData dataUser = new UserData(updatedAdmin.getPublicId(),
                    updatedAdmin.getEmail(), password, updatedAdmin.getRole());
            userClient.updateUser(updatedAdmin.getPublicId(), dataUser);
        } catch (Exception ex) {
            throw new ApiCommunicationException("Falha na comunicação com o serviço de usuários");
        }
        adminRepository.save(updatedAdmin);
        return AdminDto.toDto(updatedAdmin);
    }

    public MessageDto performActionOnAdmin(Long id, Consumer<Admin> action, String type, String errorMessage) throws Exception {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Admin não encontrado para o id informado"));

        if (action == null) {
            throw new IllegalArgumentException("Action é necessária");
        }

        String accountStatus = (admin.getDeletedAt() == null) ? "ativada" : "desativada";

        if ((admin.getDeletedAt() == null && type.equals("Activated")) ||
                (admin.getDeletedAt() != null && type.equals("Disabled"))) {
            throw new GenericException("Admin já está com a conta " + accountStatus);
        }

        action.accept(admin);
        adminRepository.save(admin);
        return MessageDto.toDto("Admin " + errorMessage);
    }

    public MessageDto reactivateById(Long id) throws Exception {
        return performActionOnAdmin(id, admin -> admin.setDeletedAt(null), "Activated", "reativado com sucesso");
    }

    public MessageDto deleteById(Long id) throws Exception {
        return performActionOnAdmin(id, admin -> admin.setDeletedAt(LocalDate.now()), "Disabled", "desativado com sucesso");
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    private static String generatePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
