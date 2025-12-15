package com.fincomun.utilities;

import java.util.Hashtable;
import javax.naming.Context;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import javax.naming.InitialContext;

@Slf4j
public class LocalizadorUtilities {

    private static LocalizadorUtilities instancia;

    private LocalizadorUtilities() {

    }

    public static LocalizadorUtilities obtener_instancia() {

        if (instancia == null) {

            instancia = new LocalizadorUtilities();

        }

        return instancia;

    }

    public DataSource informacion_oracle() {

        try {

            Hashtable tabla = new Hashtable();

            tabla.put(Context.SECURITY_PRINCIPAL, PropiedadesUtilities.Weblogic.WEBLOGIC_USUARIO);
            tabla.put(Context.SECURITY_CREDENTIALS, PropiedadesUtilities.Weblogic.WEBLOGIC_CLAVE);

            InitialContext contexto = new InitialContext(tabla);

            return (DataSource) contexto.lookup("jdbc/Oracle");

        } catch (Exception e) {

            log.error("");
            log.error("ERROR AL INICIAR LA CONEXION CON LA BASE DE DATOS DE ORACLE");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

}
