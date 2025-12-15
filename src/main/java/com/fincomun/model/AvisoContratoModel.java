package com.fincomun.model;

import lombok.Data;

@Data
public class AvisoContratoModel {

    private String numero_cliente;

    private String aviso_privacidad;

    private String contrato_servicios;

    private String tokenJwt;

}
