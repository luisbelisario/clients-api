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
                .orElseThrow(() -> new ClientNotFoundException("Client not found for the informed id"));
        if (admin.getDeletedAt() != null) {
            throw new InactiveAccountException("Client with inactive account");
        }
        return AdminDto.toDto(admin);
    }


    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }


}
