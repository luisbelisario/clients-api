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
import com.reservei.clientsapi.domain.record.TokenData;
import com.reservei.clientsapi.domain.record.UserData;
import com.reservei.clientsapi.exception.*;
import com.reservei.clientsapi.repository.AdminRepository;
import com.reservei.clientsapi.service.adminValidator.CpfCnpjValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Consumer;

@Service
public class AdminService {
    final AdminRepository adminRepository;

    @Autowired
    UserClient userClient;

    @Autowired
    private CpfCnpjValidator cpfCnpjValidator;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public AdminDto create(AdminData data) throws Exception {
        Admin admin = Admin.toAdmin(data);
        cpfCnpjValidator.validateCreate(admin);
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
                .orElseThrow(() -> new ClientNotFoundException("Admin não encontrado para o public id informado"));
        if (admin.getDeletedAt() != null) {
            throw new InactiveAccountException("Admin com a conta inativa");
        }
        return AdminDto.toDto(admin);
    }

    public AdminDto findByPublicId(String publicId, String token) throws Exception {
        Admin admin = adminRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ClientNotFoundException("Admin não encontrado para o public id informado"));
        validateToken(token, admin);
        if (admin.getDeletedAt() != null) {
            throw new InactiveAccountException("Cliente com a conta inativa");
        }
        return AdminDto.toDto(admin);
    }

    public AdminDto updateByPublicId(String publicId, AdminData data, String token) throws Exception {
        Admin admin = adminRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ClientNotFoundException("Admin não encontrado para o public id informado"));
        validateToken(token, admin);
        if (admin.getDeletedAt() != null) {
            throw new InactiveAccountException("Admin com a conta inativa");
        }
        Admin updatedAdmin = Admin.updateAdmin(admin, data);
        cpfCnpjValidator.validateUpdate(admin, updatedAdmin);
        String password = generatePassword(data.password());
        try {
            UserData dataUser = new UserData(updatedAdmin.getPublicId(),
                    updatedAdmin.getEmail(), password, updatedAdmin.getRole());
            userClient.updateUser(updatedAdmin.getPublicId(), dataUser, token);
        } catch (Exception ex) {
            throw new ApiCommunicationException("Falha na comunicação com o serviço de usuários");
        }
        adminRepository.save(updatedAdmin);
        return AdminDto.toDto(updatedAdmin);
    }

    public MessageDto performActionOnAdmin(String publicId, String token, Consumer<Admin> action, String type, String errorMessage) throws Exception {
        Admin admin = adminRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ClientNotFoundException("Admin não encontrado para o public id informado"));
        validateToken(token, admin);
        if (action == null) {
            throw new IllegalArgumentException("Action é necessária");
        }



        String accountStatus = (admin.getDeletedAt() == null) ? "ativada" : "desativada";

        if ((admin.getDeletedAt() == null && type.equals("Activate")) ||
                (admin.getDeletedAt() != null && type.equals("Deactivate"))) {
            throw new GenericException("Admin já está com a conta " + accountStatus);
        }

        action.accept(admin);
        if(type.equals("Activate")) {
            try{
                userClient.reactivateUser(admin.getPublicId(), token);
            } catch (Exception ex) {
                throw new ApiCommunicationException("Falha na comunicação com o serviço de usuários");
            }
        } else {
            try {
                userClient.deleteUser(admin.getPublicId(), token);
            } catch (Exception ex) {
                throw new ApiCommunicationException("Falha na comunicação com o serviço de usuários");
            }
        }
        adminRepository.save(admin);
        return MessageDto.toDto("Admin " + errorMessage);
    }

    public MessageDto reactivateById(String publicId, String token) throws Exception {
        return performActionOnAdmin(publicId, token, admin -> admin.setDeletedAt(null), "Activate", "reativado com sucesso");
    }

    public MessageDto deleteById(String publicId, String token) throws Exception {
        return performActionOnAdmin(publicId, token, admin -> admin.setDeletedAt(LocalDate.now()), "Deactivate", "desativado com sucesso");
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    private static String generatePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private void validateToken(String token, Admin admin) throws InvalidTokenException {
        if( (userClient.validateToken(TokenData.toData(token)).equals("invalid")) ||
                !(userClient.validateToken(TokenData.toData(token)).equals(admin.getEmail()))) {
            throw new InvalidTokenException("Token inválido ou expirado");
        }
    }

    public Admin findByCpfCnpj(String cpfCnpj) {
        return adminRepository.findByCpfCnpj(cpfCnpj);
    }
}
