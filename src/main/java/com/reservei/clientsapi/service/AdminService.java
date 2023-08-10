package com.reservei.clientsapi.service;

import com.reservei.clientsapi.domain.dto.AdminDto;
import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.model.Admin;
import com.reservei.clientsapi.domain.model.Admin;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.AdminData;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.exception.ClientNotFoundException;
import com.reservei.clientsapi.exception.GenericException;
import com.reservei.clientsapi.exception.InactiveAccountException;
import com.reservei.clientsapi.repository.AdminRepository;
import com.reservei.clientsapi.service.clientvalidator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Consumer;

@Service
public class AdminService {
    final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public AdminDto create(AdminData data) throws Exception {
        Admin admin = Admin.toClient(data);
        return AdminDto.toDto(adminRepository.save(admin));
    }

    public AdminDto findById(Long id) throws Exception {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Admin not found for the informed id"));
        if (admin.getDeletedAt() != null) {
            throw new InactiveAccountException("Admin with inactive account");
        }
        return AdminDto.toDto(admin);
    }

    public AdminDto updateById(Long id, AdminData data) throws Exception {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found for the informed id"));
        if (admin.getDeletedAt() != null) {
            throw new InactiveAccountException("Client with inactive account");
        }
        Admin updatedAdmin = Admin.updateClient(admin, data);
        adminRepository.save(updatedAdmin);
        return AdminDto.toDto(updatedAdmin);
    }
    public MessageDto performActionOnAdmin(Long id, Consumer<Admin> action, String type, String errorMessage) throws Exception {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Admin not found for the informed id"));

        if (action == null) {
            throw new IllegalArgumentException("Action is required");
        }

        if (admin.getDeletedAt() == null && type.equals("Activated")) {
            throw new GenericException("This admin is already active");
        }
        action.accept(admin);
        adminRepository.save(admin);
        return MessageDto.toDto("Admin " + errorMessage);
    }

    public MessageDto reactivateById(Long id) throws Exception {
        return performActionOnAdmin(id, admin -> admin.setDeletedAt(null), "Activated","reactivated successfully");
    }

    public MessageDto deleteById(Long id) throws Exception {
        return performActionOnAdmin(id, admin -> admin.setDeletedAt(LocalDate.now()),"Disabled", "deleted successfully");
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }


}
