package com.reservei.clientsapi.controller;

import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
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
    @Operation(summary = "Cria um novo cliente")
    public ResponseEntity<ClientDto> create(@RequestBody ClientData data, UriComponentsBuilder uriBuilder) {
        ClientDto dto = clientService.create(data);
        URI uri = uriBuilder.path("/clients/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um cliente por id")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id) {
        ClientDto dto = clientService.findById(id);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um cliente por id")
    public ResponseEntity<ClientDto> updateById(@PathVariable Long id, @RequestBody ClientData data) {
        ClientDto dto = clientService.updateById(id, data);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Reativa a conta de um cliente por id")
    public ResponseEntity<MessageDto> reactivateById(@PathVariable Long id) {
        MessageDto dto = clientService.reactivateById(id);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um cliente por id")
    public ResponseEntity<MessageDto> deleteById(@PathVariable Long id) {
        MessageDto dto = clientService.deleteById(id);

        return ResponseEntity.ok().body(dto);
    }
}
