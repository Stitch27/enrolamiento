package com.fincomun.service;

import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import javax.crypto.Cipher;
import org.json.JSONObject;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import com.fincomun.model.TokenModel;
import com.fincomun.model.ClienteModel;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.springframework.http.HttpStatus;
import com.fincomun.model.ResultadoIneModel;
import com.fincomun.model.AvisoContratoModel;
import org.springframework.stereotype.Service;
import com.fincomun.model.BitacoraIncodeModel;
import com.fincomun.model.ValidarDatosIneModel;
import org.springframework.http.ResponseEntity;
import com.fincomun.model.ValidarDatosCurpModel;
import com.fincomun.model.RegistrarBitacoraModel;
import com.fincomun.component.ServiciosComponent;
import com.fincomun.model.ValidarInformacionModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fincomun.utilities.PropiedadesUtilities;
import com.fincomun.utilities.ValidacionesUtilities;
import com.fincomun.model.RespuestaValidarCurpModel;
import com.fincomun.component.ProcedimientosComponent;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class EnrolamientoEncriptadoService {

    @Autowired
    private ServiciosComponent servicios;

    @Autowired
    private ProcedimientosComponent procedimientos;

    public ResponseEntity<Object> validar_informacion(String solicitud) {

        HashMap<String, Object> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(solicitud)) {

            resultado.put("codigo", 101);
            resultado.put("descripcion", "Petición vacía.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        ValidarInformacionModel peticion = solicitud_validar_informacion(solicitud);

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", -30);
            resultado.put("descripcion", "Cadena encriptada inválida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        log.info("DATOS DE LA PETICION");
        log.info("");
        log.info("");
        log.info("NUMERO DEL CLIENTE: " + peticion.getNumero_cliente());
        log.info("TELEFONO: " + peticion.getTelefono_celular());
        log.info("CORREO ELECTRONICO: " + peticion.getCorreo_electronico());
        log.info("CURP: " + peticion.getCurp());
        log.info("TOKEN: " + peticion.getTokenJwt());
        log.info("");
        log.info("");

        if (Objects.isNull(peticion.getTokenJwt()) || peticion.getTokenJwt().trim().isEmpty()) {

            resultado.put("codigo", -31);
            resultado.put("descripcion", "Ingresar el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        String resultado_token = servicios.valida_token(peticion.getTokenJwt().trim());

        if (Objects.isNull(resultado_token)) {

            resultado.put("codigo", -32);
            resultado.put("descripcion", "No fue posible ejecutar el servicio de validación para el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        try {

            ObjectMapper mapeador = new ObjectMapper();
            TokenModel token = mapeador.readValue(resultado_token, TokenModel.class);

            if (!token.getCodigo().equals(0)) {

                resultado.put("codigo", token.getCodigo());
                resultado.put("descripcion", token.getMensaje());
                respuesta.put("resultado", resultado);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

        } catch (Exception e) {

            resultado.put("codigo", -33);
            resultado.put("descripcion", "La respuesta del servicio de validación del token de acceso no es correcta.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getNumero_cliente()) || peticion.getNumero_cliente().trim().isEmpty()) {

            resultado.put("codigo", 102);
            resultado.put("descripcion", "Ingresar el número del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getCorreo_electronico()) || peticion.getCorreo_electronico().trim().isEmpty()) {

            resultado.put("codigo", 103);
            resultado.put("descripcion", "Ingresar el correo electrónico del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getTelefono_celular()) || peticion.getTelefono_celular().trim().isEmpty()) {

            resultado.put("codigo", 104);
            resultado.put("descripcion", "Ingresar el teléfono celular del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getCurp()) || peticion.getCurp().trim().isEmpty()) {

            resultado.put("codigo", 105);
            resultado.put("descripcion", "Ingresar la CURP del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getFirma()) || peticion.getFirma().trim().isEmpty()) {

            resultado.put("codigo", 106);
            resultado.put("descripcion", "Ingresar la firma del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_correo_electronico(peticion.getCorreo_electronico().trim())) {

            resultado.put("codigo", 107);
            resultado.put("descripcion", "El correo electrónico no es válido.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_telefono_celular(peticion.getTelefono_celular().trim())) {

            resultado.put("codigo", 108);
            resultado.put("descripcion", "Teléfono celular no válido.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_curp(peticion.getCurp().trim())) {

            resultado.put("codigo", 109);
            resultado.put("descripcion", "CURP no válida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        List<ClienteModel> clientes = procedimientos.validar_cliente(peticion.getTelefono_celular());

        if (Objects.isNull(clientes)) {

            resultado.put("codigo", 110);
            resultado.put("descripcion", "No fue posible validar la información del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!clientes.isEmpty()) {

            Integer registrar = procedimientos.registrar_enrolamiento(peticion.getNumero_cliente().trim(), peticion.getTelefono_celular().trim(),
                    peticion.getCorreo_electronico().trim(), peticion.getCurp().trim(), peticion.getFirma().trim(), 2);

            if (registrar != 0) {

                resultado.put("codigo", 111);
                resultado.put("descripcion", "No fue posible registrar el enrolamiento del cliente.");
                respuesta.put("resultado", resultado);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            resultado.put("codigo", 112);
            resultado.put("descripcion", "El teléfono celular ya se encuentra asociado a un cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        } else {

            Integer registrar = procedimientos.registrar_enrolamiento(peticion.getNumero_cliente().trim(), peticion.getTelefono_celular().trim(),
                    peticion.getCorreo_electronico().trim(), peticion.getCurp().trim(), peticion.getFirma().trim(), 1);

            if (registrar != 0) {

                resultado.put("codigo", 111);
                resultado.put("descripcion", "No fue posible registrar el enrolamiento del cliente.");
                respuesta.put("resultado", resultado);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            resultado.put("codigo", 0);
            resultado.put("descripcion", "Petición realizada con éxito.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

    }

    public ResponseEntity<Object> aceptar_aviso_contrato(String solicitud) {

        HashMap<String, Object> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(solicitud)) {

            resultado.put("codigo", 101);
            resultado.put("descripcion", "Petición vacía.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        AvisoContratoModel peticion = solicitud_aviso_contrato(solicitud);

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", -30);
            resultado.put("descripcion", "Cadena encriptada inválida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        log.info("DATOS DE LA PETICION");
        log.info("");
        log.info("");
        log.info("NUMERO DEL CLIENTE: " + peticion.getNumero_cliente());
        log.info("AVISO DE PRIVACIAD: " + peticion.getAviso_privacidad());
        log.info("CONTRATO DE SERVICIOS ELECTRONICOS: " + peticion.getContrato_servicios());
        log.info("TOKEN: " + peticion.getTokenJwt());
        log.info("");
        log.info("");

        if (Objects.isNull(peticion.getTokenJwt()) || peticion.getTokenJwt().trim().isEmpty()) {

            resultado.put("codigo", -31);
            resultado.put("descripcion", "Ingresar el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        String resultado_token = servicios.valida_token(peticion.getTokenJwt().trim());

        if (Objects.isNull(resultado_token)) {

            resultado.put("codigo", -32);
            resultado.put("descripcion", "No fue posible ejecutar el servicio de validación para el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        try {

            ObjectMapper mapeador = new ObjectMapper();
            TokenModel token = mapeador.readValue(resultado_token, TokenModel.class);

            if (!token.getCodigo().equals(0)) {

                resultado.put("codigo", token.getCodigo());
                resultado.put("descripcion", token.getMensaje());
                respuesta.put("resultado", resultado);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

        } catch (Exception e) {

            resultado.put("codigo", -33);
            resultado.put("descripcion", "La respuesta del servicio de validación del token de acceso no es correcta.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getNumero_cliente()) || peticion.getNumero_cliente().trim().isEmpty()) {

            resultado.put("codigo", 102);
            resultado.put("descripcion", "Ingresar el número del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getAviso_privacidad()) || peticion.getAviso_privacidad().trim().isEmpty()) {

            resultado.put("codigo", 103);
            resultado.put("descripcion", "Ingresar el aviso de privacidad.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getContrato_servicios()) || peticion.getContrato_servicios().trim().isEmpty()) {

            resultado.put("codigo", 104);
            resultado.put("descripcion", "Ingresar el contrato de servicios electrónicos.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!peticion.getAviso_privacidad().trim().equals(PropiedadesUtilities.Variables.VALOR_AVISO_CONTRATO)) {

            resultado.put("codigo", 105);
            resultado.put("descripcion", "El valor del campo aviso de privacidad no es correcto.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!peticion.getContrato_servicios().trim().equals(PropiedadesUtilities.Variables.VALOR_AVISO_CONTRATO)) {

            resultado.put("codigo", 106);
            resultado.put("descripcion", "El valor del campo de contrato de servicios no es correcto.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        Integer registrar = procedimientos.registrar_aviso_contrato(peticion.getNumero_cliente().trim(),
                peticion.getAviso_privacidad().trim(), peticion.getContrato_servicios().trim());

        if (registrar != 0) {

            resultado.put("codigo", 107);
            resultado.put("descripcion", "No fue posible registrar el aviso de privacidad y el contrato de servicios.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        resultado.put("codigo", 0);
        resultado.put("descripcion", "Petición realizada con éxito.");
        respuesta.put("resultado", resultado);

        log.info("RESULTADO");
        log.info("");
        log.info(new JSONObject(respuesta).toString());
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");

        return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

    }

    public ResponseEntity<Object> registrar_bitacora(String solicitud) {

        HashMap<String, Object> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(solicitud)) {

            resultado.put("codigo", 101);
            resultado.put("descripcion", "Petición vacía.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        RegistrarBitacoraModel peticion = solicitud_registrar_bitacora(solicitud);

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", -30);
            resultado.put("descripcion", "Cadena encriptada inválida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        log.info("DATOS DE LA PETICION");
        log.info("");
        log.info("");
        log.info("NUMERO DEL CLIENTE: " + peticion.getNumero_cliente());
        log.info("CURP: " + peticion.getCurp());
        log.info("NOMBRE: " + peticion.getNombre());
        log.info("APELLIDO PATERNO: " + peticion.getApellido_paterno());
        log.info("APELLIDO MATERNO: " + peticion.getApellido_materno());
        log.info("LATITUD: " + peticion.getLatitud());
        log.info("LONGITUD: " + peticion.getLongitud());
        log.info("ESTATUS: " + peticion.getEstatus());
        log.info("FOLIO: " + peticion.getFolio());
        log.info("TOKEN: " + peticion.getTokenJwt());
        log.info("");
        log.info("");

        if (Objects.isNull(peticion.getTokenJwt()) || peticion.getTokenJwt().trim().isEmpty()) {

            resultado.put("codigo", -31);
            resultado.put("descripcion", "Ingresar el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        String resultado_token = servicios.valida_token(peticion.getTokenJwt().trim());

        if (Objects.isNull(resultado_token)) {

            resultado.put("codigo", -32);
            resultado.put("descripcion", "No fue posible ejecutar el servicio de validación para el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getNumero_cliente()) || peticion.getNumero_cliente().trim().isEmpty()) {

            resultado.put("codigo", 102);
            resultado.put("descripcion", "Ingresar el número del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getCurp()) || peticion.getCurp().trim().isEmpty()) {

            peticion.setCurp("SI");

        }

        if (Objects.isNull(peticion.getNombre()) || peticion.getNombre().trim().isEmpty()) {

            peticion.setNombre("SI");
        }

        if (Objects.isNull(peticion.getApellido_paterno()) || peticion.getApellido_paterno().trim().isEmpty()) {

            peticion.setApellido_paterno("SI");
        }

        if (Objects.isNull(peticion.getApellido_materno()) || peticion.getApellido_materno().trim().isEmpty()) {

            peticion.setApellido_materno("SI");
        }

        if (Objects.isNull(peticion.getLatitud()) || peticion.getLatitud().trim().isEmpty()) {

            resultado.put("codigo", 103);
            resultado.put("descripcion", "Ingresar la latitud.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getLongitud()) || peticion.getLongitud().trim().isEmpty()) {

            resultado.put("codigo", 104);
            resultado.put("descripcion", "Ingresar la longitud.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getEstatus()) || peticion.getEstatus().trim().isEmpty()) {

            resultado.put("codigo", 105);
            resultado.put("descripcion", "Ingresar el estatus.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_latitud(peticion.getLatitud().trim())) {

            resultado.put("codigo", 106);
            resultado.put("descripcion", "Latitud no válida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_longitud(peticion.getLongitud().trim())) {

            resultado.put("codigo", 107);
            resultado.put("descripcion", "Longitud no válida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_entero_positivo(peticion.getEstatus().trim())) {

            resultado.put("codigo", 108);
            resultado.put("descripcion", "El campo estatus debe ser de tipo numérico mayor a 0.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getFolio()) || peticion.getFolio().trim().isEmpty()) {

            peticion.setFolio(ValidacionesUtilities.generar_folio_validacion());

        }

        String latitud_recortada = peticion.getLatitud().trim().length() > 25
                ? peticion.getLatitud().trim().substring(0, 25) : peticion.getLatitud().trim();

        String longitud_recortada = peticion.getLongitud().trim().length() > 25
                ? peticion.getLongitud().trim().substring(0, 25) : peticion.getLongitud().trim();

        Integer registrar = procedimientos.registrar_bitacora(peticion.getNumero_cliente().trim(),
                peticion.getCurp().trim(), peticion.getNombre().trim(), peticion.getApellido_paterno().trim(),
                peticion.getApellido_materno().trim(), latitud_recortada, longitud_recortada,
                Integer.parseInt(peticion.getEstatus().trim()), peticion.getFolio().trim());

        if (registrar != 0) {

            resultado.put("codigo", 109);
            resultado.put("descripcion", "No fue posible registrar la información en la bitácora.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        resultado.put("codigo", 0);
        resultado.put("descripcion", "Petición realizada con éxito.");
        respuesta.put("resultado", resultado);

        log.info("RESULTADO");
        log.info("");
        log.info(new JSONObject(respuesta).toString());
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");

        return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

    }

    public ResponseEntity<Object> validar_datos_curp(String solicitud) {

        HashMap<String, Object> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        String folio = ValidacionesUtilities.generar_folio();

        log.info("FOLIO");
        log.info(folio);
        log.info("");
        log.info("");

        if (Objects.isNull(solicitud)) {

            resultado.put("codigo", 101);
            resultado.put("descripcion", "Petición vacía.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        ValidarDatosCurpModel peticion = solicitud_validar_datos_curp(solicitud);

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", -30);
            resultado.put("descripcion", "Cadena encriptada inválida.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        log.info("DATOS DE LA PETICION");
        log.info("");
        log.info("");
        log.info("NUMERO DEL CLIENTE: " + peticion.getNumero_cliente());
        log.info("CURP INGRESADA: " + peticion.getCurp_ingresada());
        log.info("CURP REGISTRADA: " + peticion.getCurp_registrada());
        log.info("NOMBRE: " + peticion.getNombre());
        log.info("APELLIDO PATERNO: " + peticion.getApellido_paterno());
        log.info("APELLIDO MATERNO: " + peticion.getApellido_materno());
        log.info("FECHA DE NACIMIENTO: " + peticion.getFecha_nacimiento());
        log.info("GENERO: " + peticion.getGenero());
        log.info("ENTIDAD DE NACIMIENTO: " + peticion.getEntidad_nacimiento());
        log.info("TELEFONO: " + peticion.getTelefono_celular());
        log.info("TOKEN: " + peticion.getTokenJwt());
        log.info("");
        log.info("");

        if (Objects.isNull(peticion.getTokenJwt()) || peticion.getTokenJwt().trim().isEmpty()) {

            resultado.put("codigo", -31);
            resultado.put("descripcion", "Ingresar el token de acceso.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        String resultado_token = servicios.valida_token(peticion.getTokenJwt().trim());

        if (Objects.isNull(resultado_token)) {

            resultado.put("codigo", -32);
            resultado.put("descripcion", "No fue posible ejecutar el servicio de validación para el token de acceso.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        try {

            ObjectMapper mapeador = new ObjectMapper();
            TokenModel token = mapeador.readValue(resultado_token, TokenModel.class);

            if (!token.getCodigo().equals(0)) {

                resultado.put("codigo", token.getCodigo());
                resultado.put("descripcion", token.getMensaje());
                respuesta.put("resultado", resultado);
                respuesta.put("folio", folio);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

        } catch (Exception e) {

            resultado.put("codigo", -33);
            resultado.put("descripcion", "La respuesta del servicio de validación del token de acceso no es correcta.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getNumero_cliente()) || peticion.getNumero_cliente().trim().isEmpty()) {

            resultado.put("codigo", 102);
            resultado.put("descripcion", "Ingresar el número del cliente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getCurp_ingresada()) || peticion.getCurp_ingresada().trim().isEmpty()) {

            resultado.put("codigo", 103);
            resultado.put("descripcion", "Ingresar la CURP del cliente (ingresada).");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getCurp_registrada()) || peticion.getCurp_registrada().trim().isEmpty()) {

            resultado.put("codigo", 104);
            resultado.put("descripcion", "Ingresar la CURP del cliente (registrada).");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getNombre()) || peticion.getNombre().trim().isEmpty()) {

            resultado.put("codigo", 105);
            resultado.put("descripcion", "Ingresar el nombre del cliente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getApellido_paterno()) || peticion.getApellido_paterno().trim().isEmpty()) {

            resultado.put("codigo", 106);
            resultado.put("descripcion", "Ingresar el apellido paterno del cliente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getApellido_materno()) || peticion.getApellido_materno().trim().isEmpty()) {

            resultado.put("codigo", 107);
            resultado.put("descripcion", "Ingresar el apellido materno del cliente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getFecha_nacimiento()) || peticion.getFecha_nacimiento().trim().isEmpty()) {

            resultado.put("codigo", 108);
            resultado.put("descripcion", "Ingresar la fecha de nacimiento del cliente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getTelefono_celular()) || peticion.getTelefono_celular().trim().isEmpty()) {

            resultado.put("codigo", 109);
            resultado.put("descripcion", "Ingresar el teléfono celular del cliente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getGenero()) || peticion.getGenero().trim().isEmpty()) {

            resultado.put("codigo", 110);
            resultado.put("descripcion", "Ingresar el género del cliente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getEntidad_nacimiento()) || peticion.getEntidad_nacimiento().trim().isEmpty()) {

            resultado.put("codigo", 111);
            resultado.put("descripcion", "Ingresar la entidad de nacimiento del cliente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_curp(peticion.getCurp_ingresada().trim())) {

            resultado.put("codigo", 112);
            resultado.put("descripcion", "CURP no válida (ingresada).");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_curp(peticion.getCurp_registrada().trim())) {

            resultado.put("codigo", 113);
            resultado.put("descripcion", "CURP no válida (registrada).");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_fecha_nacimiento(peticion.getFecha_nacimiento().trim())) {

            resultado.put("codigo", 114);
            resultado.put("descripcion", "Fecha de nacimiento no válida.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_telefono_celular(peticion.getTelefono_celular().trim())) {

            resultado.put("codigo", 115);
            resultado.put("descripcion", "Teléfono celular no válido.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        switch (peticion.getGenero().trim()) {
            case "M":
                peticion.setGenero("H");
                break;
            case "F":
                peticion.setGenero("M");
                break;
            default:
                resultado.put("codigo", 125);
                resultado.put("descripcion", "El género ingresado no es correcto.");
                respuesta.put("resultado", resultado);
                respuesta.put("folio", folio);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);
        }

        RespuestaValidarCurpModel validar_curp = servicios.servicio_curp(peticion.getCurp_ingresada().trim());

        if (Objects.isNull(validar_curp)) {

            resultado.put("codigo", 116);
            resultado.put("descripcion", "No fue posible ejecutar de forma correcta el servicio de validación CURP.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        String curp = ValidacionesUtilities.limpiar_campo(peticion.getCurp_registrada().trim()) + "";
        String nombre = ValidacionesUtilities.limpiar_campo(peticion.getNombre().trim()) + "";
        String apellido_paterno = ValidacionesUtilities.limpiar_campo(peticion.getApellido_paterno().trim()) + "";
        String apellido_materno = ValidacionesUtilities.limpiar_campo(peticion.getApellido_materno().trim()) + "";
        String fecha_nacimiento = ValidacionesUtilities.limpiar_campo(peticion.getFecha_nacimiento().trim()) + "";
        String genero = ValidacionesUtilities.limpiar_campo(peticion.getGenero().trim()) + "";
        String entidad_nacimiento = ValidacionesUtilities.limpiar_campo(peticion.getEntidad_nacimiento().trim()) + "";

        log.info("CAMPOS LIMPIOS");
        log.info("");
        log.info("CURP: " + curp);
        log.info("NOMBRE: " + nombre);
        log.info("APELLIDO PATERNO: " + apellido_paterno);
        log.info("APELLIDO MATERNO: " + apellido_materno);
        log.info("FECHA NACIMIENTO: " + fecha_nacimiento);
        log.info("GENERO: " + genero);
        log.info("ENTIDAD NACIMIENTO: " + entidad_nacimiento);
        log.info("");
        log.info("");

        if (!curp.equals(validar_curp.getCurp().trim())) {

            Integer registrar = procedimientos.registrar_datos_curp(peticion.getNumero_cliente().trim(), peticion.getCurp_ingresada().trim(),
                    peticion.getCurp_registrada().trim(), peticion.getNombre().trim(), peticion.getApellido_paterno().trim(),
                    peticion.getApellido_materno().trim(), peticion.getFecha_nacimiento().trim(), 1, peticion.getGenero().trim(), peticion.getEntidad_nacimiento().trim(), folio);

            if (registrar != 0) {

                resultado.put("codigo", 124);
                resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de CURP.");
                respuesta.put("resultado", resultado);
                respuesta.put("folio", folio);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            servicios.servicio_enviar_mensaje(PropiedadesUtilities.Variables.VALOR_VALIDACION_CAMPO_CURP, peticion.getTelefono_celular().trim());

            resultado.put("codigo", 117);
            resultado.put("descripcion", "CURP del cliente inconsistente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!nombre.equals(validar_curp.getNombre().trim())) {

            Integer registrar = procedimientos.registrar_datos_curp(peticion.getNumero_cliente().trim(), peticion.getCurp_ingresada().trim(),
                    peticion.getCurp_registrada().trim(), peticion.getNombre().trim(), peticion.getApellido_paterno().trim(),
                    peticion.getApellido_materno().trim(), peticion.getFecha_nacimiento().trim(), 2, peticion.getGenero().trim(), peticion.getEntidad_nacimiento().trim(), folio);

            if (registrar != 0) {

                resultado.put("codigo", 124);
                resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de CURP.");
                respuesta.put("resultado", resultado);
                respuesta.put("folio", folio);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            servicios.servicio_enviar_mensaje(PropiedadesUtilities.Variables.VALOR_VALIDACION_CAMPO_NOMBRE, peticion.getTelefono_celular().trim());

            resultado.put("codigo", 118);
            resultado.put("descripcion", "Nombre del cliente inconsistente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!apellido_paterno.equals(validar_curp.getApellido_paterno().trim())) {

            Integer registrar = procedimientos.registrar_datos_curp(peticion.getNumero_cliente().trim(), peticion.getCurp_ingresada().trim(),
                    peticion.getCurp_registrada().trim(), peticion.getNombre().trim(), peticion.getApellido_paterno().trim(),
                    peticion.getApellido_materno().trim(), peticion.getFecha_nacimiento().trim(), 3, peticion.getGenero().trim(), peticion.getEntidad_nacimiento().trim(), folio);

            if (registrar != 0) {

                resultado.put("codigo", 124);
                resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de CURP.");
                respuesta.put("resultado", resultado);
                respuesta.put("folio", folio);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            servicios.servicio_enviar_mensaje(PropiedadesUtilities.Variables.VALOR_VALIDACION_CAMPO_APELLIDO_PATERNO, peticion.getTelefono_celular().trim());

            resultado.put("codigo", 119);
            resultado.put("descripcion", "Apellido paterno del cliente inconsistente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!apellido_materno.equals(validar_curp.getApellido_materno().trim())) {

            Integer registrar = procedimientos.registrar_datos_curp(peticion.getNumero_cliente().trim(), peticion.getCurp_ingresada().trim(),
                    peticion.getCurp_registrada().trim(), peticion.getNombre().trim(), peticion.getApellido_paterno().trim(),
                    peticion.getApellido_materno().trim(), peticion.getFecha_nacimiento().trim(), 4, peticion.getGenero().trim(), peticion.getEntidad_nacimiento().trim(), folio);

            if (registrar != 0) {

                resultado.put("codigo", 124);
                resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de CURP.");
                respuesta.put("resultado", resultado);
                respuesta.put("folio", folio);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            servicios.servicio_enviar_mensaje(PropiedadesUtilities.Variables.VALOR_VALIDACION_CAMPO_APELLIDO_MATERNO, peticion.getTelefono_celular().trim());

            resultado.put("codigo", 120);
            resultado.put("descripcion", "Apellido materno del cliente inconsistente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!fecha_nacimiento.equals(validar_curp.getFecha_nacimiento().trim())) {

            Integer registrar = procedimientos.registrar_datos_curp(peticion.getNumero_cliente().trim(), peticion.getCurp_ingresada().trim(),
                    peticion.getCurp_registrada().trim(), peticion.getNombre().trim(), peticion.getApellido_paterno().trim(),
                    peticion.getApellido_materno().trim(), peticion.getFecha_nacimiento().trim(), 5, peticion.getGenero().trim(), peticion.getEntidad_nacimiento().trim(), folio);

            if (registrar != 0) {

                resultado.put("codigo", 124);
                resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de CURP.");
                respuesta.put("resultado", resultado);
                respuesta.put("folio", folio);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            servicios.servicio_enviar_mensaje(PropiedadesUtilities.Variables.VALOR_VALIDACION_CAMPO_FECHA_NACIMIENTO, peticion.getTelefono_celular().trim());

            resultado.put("codigo", 121);
            resultado.put("descripcion", "Fecha de nacimiento del cliente inconsistente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!genero.equals(validar_curp.getGenero().trim())) {

            Integer registrar = procedimientos.registrar_datos_curp(peticion.getNumero_cliente().trim(), peticion.getCurp_ingresada().trim(),
                    peticion.getCurp_registrada().trim(), peticion.getNombre().trim(), peticion.getApellido_paterno().trim(),
                    peticion.getApellido_materno().trim(), peticion.getFecha_nacimiento().trim(), 6, peticion.getGenero().trim(), peticion.getEntidad_nacimiento().trim(), folio);

            if (registrar != 0) {

                resultado.put("codigo", 124);
                resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de CURP.");
                respuesta.put("resultado", resultado);
                respuesta.put("folio", folio);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            servicios.servicio_enviar_mensaje(PropiedadesUtilities.Variables.VALOR_VALIDACION_CAMPO_GENERO, peticion.getTelefono_celular().trim());

            resultado.put("codigo", 122);
            resultado.put("descripcion", "Género del cliente inconsistente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!entidad_nacimiento.equals(validar_curp.getEntidad_nacimiento().trim())) {

            Integer registrar = procedimientos.registrar_datos_curp(peticion.getNumero_cliente().trim(), peticion.getCurp_ingresada().trim(),
                    peticion.getCurp_registrada().trim(), peticion.getNombre().trim(), peticion.getApellido_paterno().trim(),
                    peticion.getApellido_materno().trim(), peticion.getFecha_nacimiento().trim(), 7, peticion.getGenero().trim(), peticion.getEntidad_nacimiento().trim(), folio);

            if (registrar != 0) {

                resultado.put("codigo", 124);
                resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de CURP.");
                respuesta.put("resultado", resultado);
                respuesta.put("folio", folio);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            servicios.servicio_enviar_mensaje(PropiedadesUtilities.Variables.VALOR_VALIDACION_CAMPO_ENTIDAD_NACIMIENTO, peticion.getTelefono_celular().trim());

            resultado.put("codigo", 123);
            resultado.put("descripcion", "Entidad de nacimiento del cliente inconsistente.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        Integer registrar = procedimientos.registrar_datos_curp(peticion.getNumero_cliente().trim(), peticion.getCurp_ingresada().trim(),
                peticion.getCurp_registrada().trim(), peticion.getNombre().trim(), peticion.getApellido_paterno().trim(),
                peticion.getApellido_materno().trim(), peticion.getFecha_nacimiento().trim(), 8, peticion.getGenero().trim(), peticion.getEntidad_nacimiento().trim(), folio);

        if (registrar != 0) {

            resultado.put("codigo", 124);
            resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de CURP.");
            respuesta.put("resultado", resultado);
            respuesta.put("folio", folio);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        resultado.put("codigo", 0);
        resultado.put("descripcion", "Información válida del cliente.");
        respuesta.put("resultado", resultado);
        respuesta.put("folio", folio);

        log.info("RESULTADO");
        log.info("");
        log.info(new JSONObject(respuesta).toString());
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");

        return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

    }

    public ResponseEntity<Object> bitacora_incode(String solicitud) {

        HashMap<String, Object> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(solicitud)) {

            resultado.put("codigo", 101);
            resultado.put("descripcion", "Petición vacía.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        BitacoraIncodeModel peticion = solicitud_bitacora_incode(solicitud);

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", -30);
            resultado.put("descripcion", "Cadena encriptada inválida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        log.info("DATOS DE LA PETICION");
        log.info("");
        log.info("");
        log.info("NUMERO DEL CLIENTE: " + peticion.getNumero_cliente());
        log.info("CURP: " + peticion.getCurp());
        log.info("NOMBRE: " + peticion.getNombre());
        log.info("APELLIDO PATERNO: " + peticion.getApellido_paterno());
        log.info("APELLIDO MATERNO: " + peticion.getApellido_materno());
        log.info("ESTATUS: " + peticion.getEstatus());
        log.info("IDENTIFICADOR INCODE: " + peticion.getIdentificador_incode());
        log.info("SELFIE: " + peticion.getSelfie());
        log.info("VIDEO SELFIE: " + peticion.getVideo_selfie());
        log.info("PRUEBA DE VIDA: " + peticion.getPrueba_vida());
        log.info("FOLIO: " + peticion.getFolio());
        log.info("TOKEN: " + peticion.getTokenJwt());
        log.info("");
        log.info("");

        if (Objects.isNull(peticion.getTokenJwt()) || peticion.getTokenJwt().trim().isEmpty()) {

            resultado.put("codigo", -31);
            resultado.put("descripcion", "Ingresar el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        String resultado_token = servicios.valida_token(peticion.getTokenJwt().trim());

        if (Objects.isNull(resultado_token)) {

            resultado.put("codigo", -32);
            resultado.put("descripcion", "No fue posible ejecutar el servicio de validación para el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        try {

            ObjectMapper mapeador = new ObjectMapper();
            TokenModel token = mapeador.readValue(resultado_token, TokenModel.class);

            if (!token.getCodigo().equals(0)) {

                resultado.put("codigo", token.getCodigo());
                resultado.put("descripcion", token.getMensaje());
                respuesta.put("resultado", resultado);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

        } catch (Exception e) {

            resultado.put("codigo", -33);
            resultado.put("descripcion", "La respuesta del servicio de validación del token de acceso no es correcta.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getNumero_cliente()) || peticion.getNumero_cliente().trim().isEmpty()) {

            resultado.put("codigo", 102);
            resultado.put("descripcion", "Ingresar el número del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getCurp()) || peticion.getCurp().trim().isEmpty()) {

            resultado.put("codigo", 103);
            resultado.put("descripcion", "Ingresar la CURP del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getNombre()) || peticion.getNombre().trim().isEmpty()) {

            resultado.put("codigo", 104);
            resultado.put("descripcion", "Ingresar el nombre del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getApellido_paterno()) || peticion.getApellido_paterno().trim().isEmpty()) {

            resultado.put("codigo", 105);
            resultado.put("descripcion", "Ingresar el apellido paterno del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getApellido_materno()) || peticion.getApellido_materno().trim().isEmpty()) {

            resultado.put("codigo", 106);
            resultado.put("descripcion", "Ingresar el apellido materno del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getEstatus()) || peticion.getEstatus().trim().isEmpty()) {

            resultado.put("codigo", 107);
            resultado.put("descripcion", "Ingresar el estatus.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getIdentificador_incode()) || peticion.getIdentificador_incode().trim().isEmpty()) {

            resultado.put("codigo", 108);
            resultado.put("descripcion", "Ingresar el identificador de INCODE.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getSelfie()) || peticion.getSelfie().trim().isEmpty()) {

            resultado.put("codigo", 109);
            resultado.put("descripcion", "Ingresar el selfie de INCODE.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getVideo_selfie()) || peticion.getVideo_selfie().trim().isEmpty()) {

            resultado.put("codigo", 110);
            resultado.put("descripcion", "Ingresar el video selfie de INCODE.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getPrueba_vida()) || peticion.getPrueba_vida().trim().isEmpty()) {

            resultado.put("codigo", 111);
            resultado.put("descripcion", "Ingresar la prueba de vida de INCODE.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_curp(peticion.getCurp().trim())) {

            resultado.put("codigo", 112);
            resultado.put("descripcion", "CURP no válida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_entero_positivo(peticion.getEstatus().trim())) {

            resultado.put("codigo", 113);
            resultado.put("descripcion", "El campo estatus debe ser de tipo numérico mayor a 0.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getFolio()) || peticion.getFolio().trim().isEmpty()) {

            resultado.put("codigo", 114);
            resultado.put("descripcion", "Ingresar el folio de registro.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        Integer registrar = procedimientos.registrar_bitacora_incode(peticion.getNumero_cliente().trim(), peticion.getCurp().trim(),
                peticion.getNombre().trim(), peticion.getApellido_paterno().trim(), peticion.getApellido_materno().trim(),
                Integer.parseInt(peticion.getEstatus().trim()), peticion.getIdentificador_incode().trim(), peticion.getSelfie().trim(),
                peticion.getVideo_selfie().trim(), peticion.getPrueba_vida().trim(), peticion.getFolio().trim());

        if (registrar != 0) {

            resultado.put("codigo", 115);
            resultado.put("descripcion", "No fue posible registrar los datos del cliente en la bitácora de INCODE.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        resultado.put("codigo", 0);
        resultado.put("descripcion", "Petición realizada con éxito.");
        respuesta.put("resultado", resultado);

        log.info("RESULTADO");
        log.info("");
        log.info(new JSONObject(respuesta).toString());
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");

        return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

    }

    public ResponseEntity<Object> validar_datos_ine(String solicitud) {

        HashMap<String, Object> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(solicitud)) {

            resultado.put("codigo", 101);
            resultado.put("descripcion", "Petición vacía.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        ValidarDatosIneModel peticion = solicitud_validar_datos_ine(solicitud);

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", -30);
            resultado.put("descripcion", "Cadena encriptada inválida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        log.info("DATOS DE LA PETICION");
        log.info("");
        log.info("");
        log.info("NUMERO DEL CLIENTE: " + peticion.getN_cliente());
        log.info("AÑO REGISTRO: " + peticion.getA_registro());
        log.info("AÑO EMISION: " + peticion.getA_emision());
        log.info("NOMBRE: " + peticion.getNombre());
        log.info("CURP: " + peticion.getCurp());
        log.info("NUMERO EMISION: " + peticion.getN_emision());
        log.info("CODIGO DE IDENTIFICACION: " + peticion.getC_identificacion());
        log.info("CLAVE DE ELECTOR: " + peticion.getC_elector());
        log.info("APELLIDO PATERNO: " + peticion.getC_identificacion());
        log.info("APELLIDO MATERNO: " + peticion.getC_identificacion());
        log.info("RECONOCIMIENTO OPTICO: " + peticion.getOcr());
        log.info("FOLIO: " + peticion.getFolio());
        log.info("TOKEN: " + peticion.getTokenJwt());
        log.info("");
        log.info("");

        if (Objects.isNull(peticion.getTokenJwt()) || peticion.getTokenJwt().trim().isEmpty()) {

            resultado.put("codigo", -31);
            resultado.put("descripcion", "Ingresar el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        String resultado_token = servicios.valida_token(peticion.getTokenJwt().trim());

        if (Objects.isNull(resultado_token)) {

            resultado.put("codigo", -32);
            resultado.put("descripcion", "No fue posible ejecutar el servicio de validación para el token de acceso.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        try {

            ObjectMapper mapeador = new ObjectMapper();
            TokenModel token = mapeador.readValue(resultado_token, TokenModel.class);

            if (!token.getCodigo().equals(0)) {

                resultado.put("codigo", token.getCodigo());
                resultado.put("descripcion", token.getMensaje());
                respuesta.put("resultado", resultado);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

        } catch (Exception e) {

            resultado.put("codigo", -33);
            resultado.put("descripcion", "La respuesta del servicio de validación del token de acceso no es correcta.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getN_cliente()) || peticion.getN_cliente().trim().isEmpty()) {

            resultado.put("codigo", 102);
            resultado.put("descripcion", "Ingresar el número del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getA_registro()) || peticion.getA_registro().trim().isEmpty()) {

            resultado.put("codigo", 103);
            resultado.put("descripcion", "Ingresar el año de registro.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getA_emision()) || peticion.getA_emision().trim().isEmpty()) {

            resultado.put("codigo", 104);
            resultado.put("descripcion", "Ingresar el año de emisión.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getNombre()) || peticion.getNombre().trim().isEmpty()) {

            resultado.put("codigo", 105);
            resultado.put("descripcion", "Ingresar el nombre del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getCurp()) || peticion.getCurp().trim().isEmpty()) {

            resultado.put("codigo", 106);
            resultado.put("descripcion", "Ingresar la CURP del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getN_emision()) || peticion.getN_emision().trim().isEmpty()) {

            resultado.put("codigo", 107);
            resultado.put("descripcion", "Ingresar el número de emisión.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getC_identificacion()) || peticion.getC_identificacion().trim().isEmpty()) {

            resultado.put("codigo", 108);
            resultado.put("descripcion", "Ingresar el código de identificación.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getC_elector()) || peticion.getC_elector().trim().isEmpty()) {

            resultado.put("codigo", 109);
            resultado.put("descripcion", "Ingresar la clave de elector.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getA_paterno()) || peticion.getA_paterno().trim().isEmpty()) {

            resultado.put("codigo", 110);
            resultado.put("descripcion", "Ingresar apellido paterno del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getA_materno()) || peticion.getA_materno().trim().isEmpty()) {

            resultado.put("codigo", 111);
            resultado.put("descripcion", "Ingresar apellido materno del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getOcr()) || peticion.getOcr().trim().isEmpty()) {

            resultado.put("codigo", 112);
            resultado.put("descripcion", "Ingresar OCR.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!ValidacionesUtilities.validar_curp(peticion.getCurp().trim())) {

            resultado.put("codigo", 113);
            resultado.put("descripcion", "CURP no válida.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (Objects.isNull(peticion.getFolio()) || peticion.getFolio().trim().isEmpty()) {

            resultado.put("codigo", 114);
            resultado.put("descripcion", "Ingresar el folio de registro.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        ResultadoIneModel resultado_ine = servicios.servicio_ine(peticion);

        if (Objects.isNull(resultado_ine)) {

            resultado.put("codigo", 115);
            resultado.put("descripcion", "No fue posible ejecutar de forma correcta el servicio de validación INE.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        if (!resultado_ine.getCodigo_http().equals("200")) {

            Integer registrar = procedimientos.registrar_datos_ine(peticion.getN_cliente().trim(), peticion.getA_registro().trim(), peticion.getA_emision().trim(),
                    peticion.getNombre().trim(), peticion.getCurp().trim(), peticion.getN_emision().trim(), peticion.getC_identificacion().trim(), peticion.getC_elector().trim(),
                    peticion.getA_paterno().trim(), peticion.getA_materno().trim(), peticion.getOcr().trim(), 2, peticion.getFolio().trim());

            if (registrar != 0) {

                resultado.put("codigo", 116);
                resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de INE.");
                respuesta.put("resultado", resultado);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

            }

            resultado.put("codigo", Integer.parseInt(resultado_ine.getCodigo_servicio()));
            resultado.put("descripcion", resultado_ine.getDescripcion_servicio());
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        Integer registrar = procedimientos.registrar_datos_ine(peticion.getN_cliente().trim(), peticion.getA_registro().trim(), peticion.getA_emision().trim(),
                peticion.getNombre().trim(), peticion.getCurp().trim(), peticion.getN_emision().trim(), peticion.getC_identificacion().trim(), peticion.getC_elector().trim(),
                peticion.getA_paterno().trim(), peticion.getA_materno().trim(), peticion.getOcr().trim(), 1, peticion.getFolio().trim());

        if (registrar != 0) {

            resultado.put("codigo", 116);
            resultado.put("descripcion", "No fue posible registrar los datos de validación del servicio de INE.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

        }

        resultado.put("codigo", 0);
        resultado.put("descripcion", "Petición realizada con éxito.");
        respuesta.put("resultado", resultado);

        log.info("RESULTADO");
        log.info("");
        log.info(new JSONObject(respuesta).toString());
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");

        return new ResponseEntity(ValidacionesUtilities.encriptar_respuesta(new JSONObject(respuesta).toString()), HttpStatus.OK);

    }

    private static ValidarDatosIneModel solicitud_validar_datos_ine(String peticion) {

        try {

            Cipher cifrar = Cipher.getInstance(PropiedadesUtilities.Encriptacion.VALOR_TIPO_CIFRADO);
            SecretKeySpec especificaciones = new SecretKeySpec(PropiedadesUtilities.Encriptacion.VALOR_CLAVE_ESPECIFICA.getBytes(), PropiedadesUtilities.Encriptacion.VALOR_TIPO_ALGORITMO);
            IvParameterSpec parametro = new IvParameterSpec(PropiedadesUtilities.Encriptacion.VALOR_PARAMETRO_ESPECIFICO.getBytes());

            byte[] decodificar = Base64.decode(peticion.getBytes());
            cifrar.init(Cipher.DECRYPT_MODE, especificaciones, parametro);

            byte[] descifrar = cifrar.doFinal(decodificar);
            String resultado = new String(descifrar);

            ObjectMapper mapeador = new ObjectMapper();
            return mapeador.readValue(resultado, ValidarDatosIneModel.class);

        } catch (Exception e) {

            log.error("EXCEPCION DESENCRIPTAR (VALIDAR DATOS INE)");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    private static ValidarDatosCurpModel solicitud_validar_datos_curp(String peticion) {

        try {

            Cipher cifrar = Cipher.getInstance(PropiedadesUtilities.Encriptacion.VALOR_TIPO_CIFRADO);
            SecretKeySpec especificaciones = new SecretKeySpec(PropiedadesUtilities.Encriptacion.VALOR_CLAVE_ESPECIFICA.getBytes(), PropiedadesUtilities.Encriptacion.VALOR_TIPO_ALGORITMO);
            IvParameterSpec parametro = new IvParameterSpec(PropiedadesUtilities.Encriptacion.VALOR_PARAMETRO_ESPECIFICO.getBytes());

            byte[] decodificar = Base64.decode(peticion.getBytes());
            cifrar.init(Cipher.DECRYPT_MODE, especificaciones, parametro);

            byte[] descifrar = cifrar.doFinal(decodificar);
            String resultado = new String(descifrar);

            ObjectMapper mapeador = new ObjectMapper();
            return mapeador.readValue(resultado, ValidarDatosCurpModel.class);

        } catch (Exception e) {

            log.error("EXCEPCION DESENCRIPTAR (VALIDAR DATOS CURP)");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    private static RegistrarBitacoraModel solicitud_registrar_bitacora(String peticion) {

        try {

            Cipher cifrar = Cipher.getInstance(PropiedadesUtilities.Encriptacion.VALOR_TIPO_CIFRADO);
            SecretKeySpec especificaciones = new SecretKeySpec(PropiedadesUtilities.Encriptacion.VALOR_CLAVE_ESPECIFICA.getBytes(), PropiedadesUtilities.Encriptacion.VALOR_TIPO_ALGORITMO);
            IvParameterSpec parametro = new IvParameterSpec(PropiedadesUtilities.Encriptacion.VALOR_PARAMETRO_ESPECIFICO.getBytes());

            byte[] decodificar = Base64.decode(peticion.getBytes());
            cifrar.init(Cipher.DECRYPT_MODE, especificaciones, parametro);

            byte[] descifrar = cifrar.doFinal(decodificar);
            String resultado = new String(descifrar);

            ObjectMapper mapeador = new ObjectMapper();
            return mapeador.readValue(resultado, RegistrarBitacoraModel.class);

        } catch (Exception e) {

            log.error("EXCEPCION DESENCRIPTAR (REGISTRAR BITACORA)");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    private static BitacoraIncodeModel solicitud_bitacora_incode(String peticion) {

        try {

            Cipher cifrar = Cipher.getInstance(PropiedadesUtilities.Encriptacion.VALOR_TIPO_CIFRADO);
            SecretKeySpec especificaciones = new SecretKeySpec(PropiedadesUtilities.Encriptacion.VALOR_CLAVE_ESPECIFICA.getBytes(), PropiedadesUtilities.Encriptacion.VALOR_TIPO_ALGORITMO);
            IvParameterSpec parametro = new IvParameterSpec(PropiedadesUtilities.Encriptacion.VALOR_PARAMETRO_ESPECIFICO.getBytes());

            byte[] decodificar = Base64.decode(peticion.getBytes());
            cifrar.init(Cipher.DECRYPT_MODE, especificaciones, parametro);

            byte[] descifrar = cifrar.doFinal(decodificar);
            String resultado = new String(descifrar);

            ObjectMapper mapeador = new ObjectMapper();
            return mapeador.readValue(resultado, BitacoraIncodeModel.class);

        } catch (Exception e) {

            log.error("EXCEPCION DESENCRIPTAR (BITACORA INCODE)");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    private static AvisoContratoModel solicitud_aviso_contrato(String peticion) {

        try {

            Cipher cifrar = Cipher.getInstance(PropiedadesUtilities.Encriptacion.VALOR_TIPO_CIFRADO);
            SecretKeySpec especificaciones = new SecretKeySpec(PropiedadesUtilities.Encriptacion.VALOR_CLAVE_ESPECIFICA.getBytes(), PropiedadesUtilities.Encriptacion.VALOR_TIPO_ALGORITMO);
            IvParameterSpec parametro = new IvParameterSpec(PropiedadesUtilities.Encriptacion.VALOR_PARAMETRO_ESPECIFICO.getBytes());

            byte[] decodificar = Base64.decode(peticion.getBytes());
            cifrar.init(Cipher.DECRYPT_MODE, especificaciones, parametro);

            byte[] descifrar = cifrar.doFinal(decodificar);
            String resultado = new String(descifrar);

            ObjectMapper mapeador = new ObjectMapper();
            return mapeador.readValue(resultado, AvisoContratoModel.class);

        } catch (Exception e) {

            log.error("EXCEPCION DESENCRIPTAR (AVISO & CONTRATO)");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    private static ValidarInformacionModel solicitud_validar_informacion(String peticion) {

        try {

            Cipher cifrar = Cipher.getInstance(PropiedadesUtilities.Encriptacion.VALOR_TIPO_CIFRADO);
            SecretKeySpec especificaciones = new SecretKeySpec(PropiedadesUtilities.Encriptacion.VALOR_CLAVE_ESPECIFICA.getBytes(), PropiedadesUtilities.Encriptacion.VALOR_TIPO_ALGORITMO);
            IvParameterSpec parametro = new IvParameterSpec(PropiedadesUtilities.Encriptacion.VALOR_PARAMETRO_ESPECIFICO.getBytes());

            byte[] decodificar = Base64.decode(peticion.getBytes());
            cifrar.init(Cipher.DECRYPT_MODE, especificaciones, parametro);

            byte[] descifrar = cifrar.doFinal(decodificar);
            String resultado = new String(descifrar);

            ObjectMapper mapeador = new ObjectMapper();
            return mapeador.readValue(resultado, ValidarInformacionModel.class);

        } catch (Exception e) {

            log.error("EXCEPCION DESENCRIPTAR (VALIDAR INFORMACION)");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

}
