package com.fincomun.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import com.fincomun.service.EnrolamientoEncriptadoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RestController
@RequestMapping(value = "/fincomun/middleware/encriptado", produces = "application/json; charset=UTF-8")
public class EnrolamientoEncriptadoController {

    @Autowired
    private EnrolamientoEncriptadoService servicio;

    @PostMapping(value = "/validar-informacion")
    public ResponseEntity<Object> validar_informacion(@RequestBody(required = false) String peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO VALIDAR INFORMACION (PETICION ENCRIPTADA)");
        log.info("");
        log.info("");

        return servicio.validar_informacion(peticion);

    }

    @PostMapping(value = "/aceptar/aviso-contrato")
    public ResponseEntity<Object> aceptar_aviso_contrato(@RequestBody(required = false) String peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO ACEPTAR AVISO & CONTRATO (PETICION ENCRIPTADA)");
        log.info("");
        log.info("");

        return servicio.aceptar_aviso_contrato(peticion);

    }

    @PostMapping(value = "/registrar/bitacora")
    public ResponseEntity<Object> registrar_bitacora(@RequestBody(required = false) String peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO REGISTRAR BITACORA (PETICION ENCRIPTADA)");
        log.info("");
        log.info("");

        return servicio.registrar_bitacora(peticion);

    }

    @PostMapping(value = "/validar/datos-curp")
    public ResponseEntity<Object> validar_datos_curp(@RequestBody(required = false) String peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO VALIDAR DATOS CURP (PETICION ENCRIPTADA)");
        log.info("");
        log.info("");

        return servicio.validar_datos_curp(peticion);

    }

    @PostMapping(value = "/bitacora/incode")
    public ResponseEntity<Object> bitacora_incode(@RequestBody(required = false) String peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO BITACORA INCODE (PETICION ENCRIPTADA)");
        log.info("");
        log.info("");

        return servicio.bitacora_incode(peticion);

    }

    @PostMapping(value = "/validar/datos-ine")
    public ResponseEntity<Object> validar_datos_ine(@RequestBody(required = false) String peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO VALIDAR DATOS INE (PETICION ENCRIPTADA)");
        log.info("");
        log.info("");

        return servicio.validar_datos_ine(peticion);

    }

}
