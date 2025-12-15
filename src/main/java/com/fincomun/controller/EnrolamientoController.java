package com.fincomun.controller;

import lombok.extern.slf4j.Slf4j;
import com.fincomun.model.AvisoContratoModel;
import com.fincomun.model.BitacoraFechaModel;
import com.fincomun.model.BitacoraIncodeModel;
import com.fincomun.model.BitacoraNombreModel;
import com.fincomun.model.DetalleClienteModel;
import org.springframework.http.ResponseEntity;
import com.fincomun.model.ValidarDatosIneModel;
import com.fincomun.model.ValidarDatosCurpModel;
import com.fincomun.service.EnrolamientoService;
import com.fincomun.model.RegistrarBitacoraModel;
import com.fincomun.model.ValidarInformacionModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RestController
@RequestMapping(value = "/fincomun/middleware", produces = "application/json; charset=UTF-8")
public class EnrolamientoController {

    @Autowired
    private EnrolamientoService servicio;

    @PostMapping(value = "/validar-informacion")
    public ResponseEntity<Object> validar_informacion(@RequestBody(required = false) ValidarInformacionModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO VALIDAR INFORMACION");
        log.info("");
        log.info("");

        return servicio.validar_informacion(peticion);

    }

    @PostMapping(value = "/aceptar/aviso-contrato")
    public ResponseEntity<Object> aceptar_aviso_contrato(@RequestBody(required = false) AvisoContratoModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO ACEPTAR AVISO & CONTRATO");
        log.info("");
        log.info("");

        return servicio.aceptar_aviso_contrato(peticion);

    }

    @PostMapping(value = "/registrar/bitacora")
    public ResponseEntity<Object> registrar_bitacora(@RequestBody(required = false) RegistrarBitacoraModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO REGISTRAR BITACORA");
        log.info("");
        log.info("");

        return servicio.registrar_bitacora(peticion);

    }

    @PostMapping(value = "/validar/datos-curp")
    public ResponseEntity<Object> validar_datos_curp(@RequestBody(required = false) ValidarDatosCurpModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO VALIDAR DATOS CURP");
        log.info("");
        log.info("");

        return servicio.validar_datos_curp(peticion);

    }

    @PostMapping(value = "/bitacora/incode")
    public ResponseEntity<Object> bitacora_incode(@RequestBody(required = false) BitacoraIncodeModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO BITACORA INCODE");
        log.info("");
        log.info("");

        return servicio.bitacora_incode(peticion);

    }

    @PostMapping(value = "/validar/datos-ine")
    public ResponseEntity<Object> validar_datos_ine(@RequestBody(required = false) ValidarDatosIneModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO VALIDAR DATOS INE");
        log.info("");
        log.info("");

        return servicio.validar_datos_ine(peticion);

    }

    @PostMapping(value = "/consultar/bitacora-nombre")
    public ResponseEntity<Object> consultar_bitacora_nombre(@RequestBody(required = false) BitacoraNombreModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO CONSULTAR BITACORA POR NOMBRE");
        log.info("");
        log.info("");

        return servicio.consultar_bitacora_nombre(peticion);

    }

    @PostMapping(value = "/consultar/bitacora-fecha")
    public ResponseEntity<Object> consultar_bitacora_fecha(@RequestBody(required = false) BitacoraFechaModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO CONSULTAR BITACORA POR FECHAS");
        log.info("");
        log.info("");

        return servicio.consultar_bitacora_fecha(peticion);

    }

    @PostMapping(value = "/consultar/detalle/cliente")
    public ResponseEntity<Object> consultar_detalle_cliente(@RequestBody(required = false) DetalleClienteModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO CONSULTAR DETALLE CLIENTE");
        log.info("");
        log.info("");

        return servicio.consultar_detalle_cliente(peticion);

    }

}
