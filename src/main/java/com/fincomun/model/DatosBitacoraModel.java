package com.fincomun.model;

import lombok.Data;

@Data
public class DatosBitacoraModel {

    private String nombre;

    private String a_paterno;

    private String a_materno;

    private String n_cliente;

    private String folio_r;

    private RegistrosBitacoraModel registros;

}
