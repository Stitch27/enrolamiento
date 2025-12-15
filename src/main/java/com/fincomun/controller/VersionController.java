package com.fincomun.controller;

import com.fincomun.service.VersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping(value = "/fincomun/middleware")
public class VersionController {

    @Autowired
    private VersionService servicio;

    @GetMapping(value = "/consultar/version")
    public ResponseEntity<Object> consultar_version() {

        return servicio.consultar_version();

    }

}
