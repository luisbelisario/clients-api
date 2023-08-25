package com.reservei.clientsapi.controller;
import com.reservei.clientsapi.domain.dto.AdminDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.model.Admin;
import com.reservei.clientsapi.domain.record.AdminData;
import com.reservei.clientsapi.domain.record.EmailData;
import com.reservei.clientsapi.exception.CpfRegisteredException;
import com.reservei.clientsapi.service.AdminService;
import com.reservei.clientsapi.service.adminValidator.CpfCnpjValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admins")
@Tag(name = "Admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CpfCnpjValidator cpfCnpjValidator;


    @PostMapping
    @Operation(summary = "Cria um novo admin", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<AdminDto> create(@RequestBody @Valid AdminData data, UriComponentsBuilder uriBuilder) throws Exception {
        if (!CpfCnpjValidator.isValidCpfCnpj(data.getCpfCnpj())) {
            throw new CpfRegisteredException("CPF/CNPJ Inválido");
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

    @GetMapping("publicId/{publicId}")
    @Operation(summary = "Busca um admin por id", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<AdminDto> findByPublicId(@PathVariable String publicId,
                                                   @RequestHeader("Authorization") String token) throws Exception {
        AdminDto dto = adminService.findByPublicId(publicId, token);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/email")
    @Operation(summary = "Busca um cliente por email", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Boolean> findByEmail(@RequestBody EmailData data) {
        Admin admin = adminService.findByEmail(data.email());
        if(admin != null) return ResponseEntity.ok().body(true);
        return ResponseEntity.ok().body(false);
    }


    @PutMapping("/{publicId}")
    @Operation(summary = "Atualiza os dados de um admin pelo public id", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<AdminDto> updateByPublicId(@PathVariable String publicId,
                                               @RequestBody @Valid AdminData data,
                                               @RequestHeader("Authorization") String token) throws Exception {
        AdminDto dto = adminService.updateByPublicId(publicId, data, token);
        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{publicId}")
    @Operation(summary = "Reativa a conta de um admin pelo public id", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> reactivateById(@PathVariable String publicId,
                                                     @RequestHeader("Authorization") String token) throws Exception {
        MessageDto dto = adminService.reactivateById(publicId, token);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{publicId}")
    @Operation(summary = "Deleta um admin pelo public id", responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> deleteById(@PathVariable String publicId,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        MessageDto dto = adminService.deleteById(publicId, token);
        return ResponseEntity.ok().body(dto);
    }
}
