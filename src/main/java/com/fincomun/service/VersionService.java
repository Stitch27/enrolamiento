package com.fincomun.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

@Service
public class VersionService {

    @Value("${spring.application.name}")
    private String nombre;

    @Value("${spring.application.version}")
    private String version;

    public ResponseEntity<Object> consultar_version() {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        resultado.put("codigo", "100");
        resultado.put("descripcion", "Petición realizada con éxito.");
        respuesta.put("resultado", resultado);
        respuesta.put("nombre", nombre);
        respuesta.put("version", version);

        return new ResponseEntity(respuesta, HttpStatus.OK);

    }

}
