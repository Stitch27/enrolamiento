package com.fincomun.model;

import lombok.Data;

@Data
public class ValidarDatosCurpModel {

    private String numero_cliente;

    private String curp_ingresada;

    private String curp_registrada;

    private String nombre;

    private String apellido_paterno;

    private String apellido_materno;

    private String fecha_nacimiento;

    private String genero;

    private String entidad_nacimiento;

    private String telefono_celular;

    private String tokenJwt;

}
