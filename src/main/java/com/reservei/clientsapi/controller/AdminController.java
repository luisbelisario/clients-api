package com.reservei.clientsapi.controller;

import com.reservei.clientsapi.domain.dto.AdminDto;
import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.record.AdminData;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.exception.CpfRegisteredException;
import com.reservei.clientsapi.service.AdminService;
import com.reservei.clientsapi.util.CpfValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/admins")
@Tag(name = "Admins")
public class AdminController {
    final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo admin", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<AdminDto> create(@RequestBody @Valid AdminData data, UriComponentsBuilder uriBuilder) throws Exception {
        if (!CpfValidator.isValidCpfCnpj(data.getCpfCnpj())) {
            throw new CpfRegisteredException("Invalid CPF/CNPJ");
        }

        AdminDto dto = adminService.create(data);
        URI uri = uriBuilder.path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um admin por id", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<AdminDto> findById(@PathVariable Long id) throws Exception {
        AdminDto dto = adminService.findById(id);
        return ResponseEntity.ok().body(dto);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um admin por id", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<AdminDto> updateById(@PathVariable Long id, @RequestBody @Valid AdminData data) throws Exception {
        AdminDto dto = adminService.updateById(id, data);
        return ResponseEntity.ok().body(dto);
    }
    @PatchMapping("/{id}")
    @Operation(summary = "Reativa a conta de um admin por id", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> reactivateById(@PathVariable Long id) throws Exception {
        MessageDto dto = adminService.reactivateById(id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um admin por id", responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> deleteById(@PathVariable Long id) throws Exception {
        MessageDto dto = adminService.deleteById(id);

        return ResponseEntity.ok().body(dto);
    }
}
