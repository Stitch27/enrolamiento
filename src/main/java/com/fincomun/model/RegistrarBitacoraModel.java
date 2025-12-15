package com.fincomun.model;

import lombok.Data;

@Data
public class RegistrarBitacoraModel {

    private String numero_cliente;

    private String curp;

    private String nombre;

    private String apellido_paterno;

    private String apellido_materno;

    private String latitud;

    private String longitud;

    private String estatus;

    private String folio;

    private String tokenJwt;

}
