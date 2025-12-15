package com.fincomun.model;

import lombok.Data;

@Data
public class CurpModel {

    private String curp_ingresada;

    private String curp_registrada;

    private String fecha_nacimiento;

    private String fecha_registro;

    private String hora_registro;

    private String valor_estatus;

    private String descripcion_estatus;

    private String genero;

    private String entidad_nacimiento;

}
