package com.fincomun.utilities;

import java.util.UUID;
import javax.crypto.Cipher;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.time.format.DateTimeFormatter;
import org.springframework.security.crypto.codec.Base64;

@Slf4j
public class ValidacionesUtilities {

    private static final int MAX_LENGTH = 40;

    private static final Pattern PATRON_CURP = Pattern.compile(
            "^[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]{2}$"
    );

    private static final Pattern PATRON_CORREO_ELECTRONICO = Pattern.compile(
            "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"
    );

    private static final Pattern PATRON_TELEFONO_CELULAR = Pattern.compile(
            "^[0-9]{10}$"
    );

    private static final Pattern PATRON_LATITUD = Pattern.compile(
            "^[-+]?([1-8]?[0-9](\\.\\d+)?|90(\\.0+)?)$"
    );

    private static final Pattern PATRON_LONGITUD = Pattern.compile(
            "^[-+]?((1[0-7][0-9]|[1-9]?[0-9])(\\.\\d+)?|180(\\.0+)?)$"
    );

    private static final Pattern PATRON_ENTERO_POSITIVO = Pattern.compile(
            "^[1-9][0-9]*$"
    );

    private static final Pattern PATRON_FECHA_NACIMIENTO = Pattern.compile(
            "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$"
    );

    private static final Pattern PATRON_FECHAS = Pattern.compile(
            "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$"
    );

    public static boolean validar_curp(String curp) {

        return PATRON_CURP.matcher(curp).matches();

    }

    public static boolean validar_correo_electronico(String correo_electronico) {

        return PATRON_CORREO_ELECTRONICO.matcher(correo_electronico).matches();

    }

    public static boolean validar_telefono_celular(String telefono_celular) {

        return PATRON_TELEFONO_CELULAR.matcher(telefono_celular).matches();

    }

    public static boolean validar_latitud(String latitud) {

        return PATRON_LATITUD.matcher(latitud).matches();

    }

    public static boolean validar_longitud(String longitud) {

        return PATRON_LONGITUD.matcher(longitud).matches();

    }

    public static boolean validar_entero_positivo(String numero) {

        return PATRON_ENTERO_POSITIVO.matcher(numero).matches();

    }

    public static boolean validar_fecha_nacimiento(String fecha_nacimiento) {

        return PATRON_FECHA_NACIMIENTO.matcher(fecha_nacimiento).matches();

    }

    public static boolean validar_fecha(String fecha) {

        return PATRON_FECHAS.matcher(fecha).matches();

    }

    public static String limpiar_campo(String valor) {

        String normalizar = Normalizer.normalize(valor, Normalizer.Form.NFD);

        String eliminar_acentos = normalizar.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return eliminar_acentos.toUpperCase();

    }

    public static String encriptar_respuesta(String salida) {

        try {

            Cipher cifrar = Cipher.getInstance(PropiedadesUtilities.Encriptacion.VALOR_TIPO_CIFRADO);
            SecretKeySpec especificaciones = new SecretKeySpec(PropiedadesUtilities.Encriptacion.VALOR_CLAVE_ESPECIFICA.getBytes(), PropiedadesUtilities.Encriptacion.VALOR_TIPO_ALGORITMO);
            IvParameterSpec parametro = new IvParameterSpec(PropiedadesUtilities.Encriptacion.VALOR_PARAMETRO_ESPECIFICO.getBytes());

            cifrar.init(Cipher.ENCRYPT_MODE, especificaciones, parametro);
            byte[] encriptado = cifrar.doFinal(salida.getBytes());

            return new String(Base64.encode(encriptado));

        } catch (Exception e) {

            log.error("EXCEPCION AL ENCRIPTAR");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return "";

        }

    }

    public static String generar_folio() {

        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy-HHmmss"));
        String aleatorio = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return "FOL-" + fecha + "-" + aleatorio;

    }

    public static String generar_folio_validacion() {

        String prefijo = "FOL-VALIDACION-USUARIO-";

        String variable = System.currentTimeMillis() + "-" + (int) (Math.random() * 10000);

        String folio = prefijo + variable;

        if (folio.length() > MAX_LENGTH) {

            folio = folio.substring(0, MAX_LENGTH);

        }

        return folio;

    }

}
