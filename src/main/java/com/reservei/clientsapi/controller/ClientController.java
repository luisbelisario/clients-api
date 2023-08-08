package com.reservei.clientsapi.controller;

import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.record.ClientData;
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

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um cliente por id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<ClientDto> updateById(@PathVariable Long id, @RequestBody @Valid ClientData data) throws Exception {
        ClientDto dto = clientService.updateById(id, data);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Reativa a conta de um cliente por id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> reactivateById(@PathVariable Long id) throws Exception {
        MessageDto dto = clientService.reactivateById(id);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um cliente por id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> deleteById(@PathVariable Long id) throws Exception {
        MessageDto dto = clientService.deleteById(id);

        return ResponseEntity.ok().body(dto);
    }
}
