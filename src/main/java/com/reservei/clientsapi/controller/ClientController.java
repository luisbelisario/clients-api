package com.reservei.clientsapi.controller;

import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.domain.record.EmailData;
import com.reservei.clientsapi.service.ClientService;
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
@RequestMapping("/clients")
@Tag(name = "Clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    @Operation(summary = "Cria um novo cliente", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<ClientDto> create(@RequestBody @Valid ClientData data, UriComponentsBuilder uriBuilder) throws Exception {
        ClientDto dto = clientService.create(data);
        URI uri = uriBuilder.path("/clients/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um cliente por id", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<ClientDto> findById(@PathVariable Long id) throws Exception {
        ClientDto dto = clientService.findById(id);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/publicId/{publicId}")
    @Operation(summary = "Busca um cliente por publicId", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<ClientDto> findByPublicId(@PathVariable String publicId) throws Exception {
        ClientDto dto = clientService.findByPublicId(publicId);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/email")
    @Operation(summary = "Busca um cliente por email", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Boolean> findByEmail(@RequestBody EmailData data) throws Exception {
        Client client = clientService.findByEmail(data.email());
        if(client != null) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.ok().body(false);
    }

    @PutMapping("/{publicId}")
    @Operation(summary = "Atualiza os dados de um cliente por id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<ClientDto> updateById(@PathVariable String publicId, @RequestBody @Valid ClientData data) throws Exception {
        ClientDto dto = clientService.updateByPublicId(publicId, data);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{publicId}")
    @Operation(summary = "Reativa a conta de um cliente por id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> reactivateById(@PathVariable String publicId) throws Exception {
        MessageDto dto = clientService.reactivateById(publicId);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{publicId}")
    @Operation(summary = "Deleta um cliente por id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> deleteById(@PathVariable String publicId) throws Exception {
        MessageDto dto = clientService.deleteById(publicId);

        return ResponseEntity.ok().body(dto);
    }
}
