package com.reservei.clientsapi.controller;

import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.domain.record.EmailData;
import com.reservei.clientsapi.domain.record.TokenData;
import com.reservei.clientsapi.exception.InvalidTokenException;
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
    public ResponseEntity<ClientDto> findById(@PathVariable Long id,
                                              @RequestHeader("Authorization") String token) throws Exception {
        ClientDto dto = clientService.findById(id, token);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/publicId/{publicId}")
    @Operation(summary = "Busca um cliente por publicId", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<ClientDto> findByPublicId(@PathVariable String publicId,
                                                    @RequestHeader("Authorization") String token) throws Exception {
        ClientDto dto = clientService.findByPublicId(publicId, token);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/email")
    @Operation(summary = "Busca um cliente por email", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Boolean> findByEmail(@RequestBody EmailData data) {
        Client client = clientService.findByEmail(data.email());
        if(client != null) return ResponseEntity.ok().body(true);
        return ResponseEntity.ok().body(false);
    }

    @PutMapping("/{publicId}")
    @Operation(summary = "Atualiza os dados de um cliente por public id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<ClientDto> updateById(@PathVariable String publicId, @RequestBody @Valid ClientData data,
                                                @RequestHeader("Authorization") String token) throws Exception {
        ClientDto dto = clientService.updateByPublicId(publicId, data, token);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{publicId}")
    @Operation(summary = "Reativa a conta de um cliente por public id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> reactivateById(@PathVariable String publicId,
                                                     @RequestHeader("Authorization") String token) throws Exception {
        MessageDto dto = clientService.reactivateById(publicId, token);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{publicId}")
    @Operation(summary = "Deleta um cliente por public id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> deleteById(@PathVariable String publicId,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        MessageDto dto = clientService.deleteById(publicId, token);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/validate/token")
    @Operation(summary = "Valida o token de um usuário", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<String> getUsername(@RequestBody TokenData data) {
        String username = clientService.getUsername(data);
        return ResponseEntity.ok().body(username);
    }

    @GetMapping("/healthCheck")
    @Operation(summary = "Health check da aplicação", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "500", description = "Erro do servidor")
    })
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
