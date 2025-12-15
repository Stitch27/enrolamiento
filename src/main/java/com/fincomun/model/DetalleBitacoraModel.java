package com.fincomun.model;

import lombok.Data;

@Data
public class DetalleBitacoraModel {

    private String nombre;

    private String a_paterno;

    private String a_materno;

    private String n_cliente;

    private RegistrosBitacoraModel registros;

}
