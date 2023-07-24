package com.reservei.clientsapi.controller;

import com.reservei.clientsapi.domain.record.ClientData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clients")
public class ClientController {

    @PostMapping
    public void create(@RequestBody ClientData data) {

    }
}
