package com.fincomun.model;

import lombok.Data;
import java.util.List;

@Data
public class ResultadosBitacoraModel {

    private String total_registros;

    private List<DatosBitacoraModel> registros;

}
