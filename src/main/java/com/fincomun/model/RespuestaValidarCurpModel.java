package com.fincomun.model;

import lombok.Data;

@Data
public class RespuestaValidarCurpModel {

    private String curp;

    private String nombre;

    private String apellido_paterno;

    private String apellido_materno;

    private String fecha_nacimiento;

    private String genero;

    private String entidad_nacimiento;

}
