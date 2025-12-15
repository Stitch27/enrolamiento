package com.fincomun.model;

import lombok.Data;

@Data
public class ValidarInformacionModel {

    private String numero_cliente;

    private String telefono_celular;

    private String correo_electronico;

    private String curp;

    private String firma;

    private String tokenJwt;

}
