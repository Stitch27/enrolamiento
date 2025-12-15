package com.fincomun.utilities;

import java.sql.Connection;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConexionUtilities {

    public Connection conexion() {

        try {

            DataSource conexion = LocalizadorUtilities.obtener_instancia().informacion_oracle();
            return conexion.getConnection();

        } catch (Exception e) {

            log.error("");
            log.error("ERROR AL ABRIR LA CONEXION CON LA BASE DE DATOS DE ORACLE");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

}
