package com.fincomun.utilities;

import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfiguracionPropiedadesUtilities {

    static Properties propiedades = new Properties();

    private ConfiguracionPropiedadesUtilities() {

    }

    static {

        try {

            InputStream entrada = new FileInputStream("enrolamiento/conf/enrolamiento.properties");
            propiedades.load(entrada);

        } catch (Exception e) {

            log.error("");
            log.error("ERROR AL ABRIR EL ARCHIVO DE ENROLAMIENTO PROPERTIES");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

        }

    }

    public static String propiedad(String nombre) {

        String valor = propiedades.getProperty(nombre);

        if (valor != null) {

            valor = valor.trim();

        }

        return valor;

    }

}
