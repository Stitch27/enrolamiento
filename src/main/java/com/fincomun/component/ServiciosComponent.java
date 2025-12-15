package com.fincomun.component;

import org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import java.nio.charset.StandardCharsets;
import org.apache.http.entity.StringEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import com.fincomun.model.ResultadoIneModel;
import org.apache.http.client.methods.HttpPost;
import com.fincomun.model.ValidarDatosIneModel;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringEscapeUtils;
import com.fincomun.utilities.PropiedadesUtilities;
import org.apache.http.client.config.RequestConfig;
import org.springframework.web.client.RestTemplate;
import com.fincomun.model.RespuestaValidarCurpModel;
import org.apache.http.impl.client.HttpClientBuilder;

@Slf4j
@Component
public class ServiciosComponent {

    public RespuestaValidarCurpModel servicio_curp(String curp) {

        RequestConfig configuracion = RequestConfig.custom()
                .setConnectTimeout(Integer.parseInt(PropiedadesUtilities.Variables.VALOR_TIMEPO_ESPERA_SERVICIOS))
                .setConnectionRequestTimeout(Integer.parseInt(PropiedadesUtilities.Variables.VALOR_TIMEPO_ESPERA_SERVICIOS))
                .setSocketTimeout(Integer.parseInt(PropiedadesUtilities.Variables.VALOR_TIMEPO_ESPERA_SERVICIOS)).build();

        HttpClient cliente = HttpClientBuilder.create().setDefaultRequestConfig(configuracion).build();

        HttpPost operacion = new HttpPost(PropiedadesUtilities.Variables.VALOR_DIRECCION_SERVICIO_VALIDAR_CURP);
        operacion.addHeader("Content-Type", PropiedadesUtilities.Variables.VALOR_CABECERA_SERVICIOS);

        JSONObject datos_portal = new JSONObject();
        datos_portal.put("identificador", PropiedadesUtilities.Variables.VALOR_IDENTIFICADOR_SERVICIO_VALIDAR_CURP_INE_ENROLAMIENTO);
        datos_portal.put("nombre", PropiedadesUtilities.Variables.VALOR_CANAL_SERVICIO_VALIDAR_CURP_INE_ENROLAMIENTO);

        JSONObject datos_cliente = new JSONObject();
        datos_cliente.put("clave_unica", curp);

        JSONObject peticion = new JSONObject();
        peticion.put("datos_portal", datos_portal);
        peticion.put("datos_cliente", datos_cliente);
        peticion.put("proceso", PropiedadesUtilities.Variables.VALOR_TIPO_PROCESO_SERVICIO_VALIDAR_CURP);

        log.info("PARAMETROS DEL SERVICIO DE VALIDACION CURP");
        log.info(peticion.toString());
        log.info("");
        log.info("");

        try {

            StringEntity entidad = new StringEntity(peticion.toString());

            operacion.setEntity(entidad);

            HttpResponse respuesta = cliente.execute(operacion);

            String salida = EntityUtils.toString(respuesta.getEntity());

            JSONObject resultado = new JSONObject(salida);

            log.info("RESPUESTA DEL SERVICIO VALIDAR CURP");
            log.info(resultado.toString());
            log.info("");
            log.info("");

            if (respuesta.getStatusLine().getStatusCode() == 200) {

                JSONObject informacion = new JSONObject(resultado.get("informacion").toString());

                RespuestaValidarCurpModel validar = new RespuestaValidarCurpModel();

                validar.setNombre(informacion.get("nombres").toString());
                validar.setApellido_paterno(informacion.get("a_paterno").toString());
                validar.setApellido_materno(informacion.get("a_materno").toString());
                validar.setFecha_nacimiento(informacion.get("fecha_nacimiento").toString());
                validar.setCurp(informacion.get("clave_unica").toString());
                validar.setGenero(informacion.get("sexo").toString());
                validar.setEntidad_nacimiento(informacion.get("c_entidad_nacimiento").toString());

                return validar;

            } else {

                log.info("CODIGO DE RESPUESTA");
                log.info(respuesta.getStatusLine().getStatusCode() + "");
                log.info("");
                log.info("");

                return null;

            }

        } catch (Exception e) {

            log.error("EXCEPCION");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    public Integer servicio_enviar_mensaje(String mensaje, String telefono_celular) {

        RequestConfig configuracion = RequestConfig.custom()
                .setConnectTimeout(Integer.parseInt(PropiedadesUtilities.Variables.VALOR_TIMEPO_ESPERA_SERVICIOS))
                .setConnectionRequestTimeout(Integer.parseInt(PropiedadesUtilities.Variables.VALOR_TIMEPO_ESPERA_SERVICIOS))
                .setSocketTimeout(Integer.parseInt(PropiedadesUtilities.Variables.VALOR_TIMEPO_ESPERA_SERVICIOS)).build();

        HttpClient cliente = HttpClientBuilder.create().setDefaultRequestConfig(configuracion).build();

        HttpPost operacion = new HttpPost(PropiedadesUtilities.Variables.VALOR_DIRECCION_SERVICIO_ENVIAR_MENSAJE);
        operacion.addHeader("Content-Type", "application/json; charset=UTF-8");
        operacion.setHeader("Accept", "application/json");

        JSONObject peticion = new JSONObject();
        peticion.put("mensaje", mensaje);
        peticion.put("celular", telefono_celular);

        log.info("PARAMETROS DEL SERVICIO ENVIAR MENSAJE");
        log.info(peticion.toString());
        log.info("");
        log.info("");

        try {

            StringEntity entidad = new StringEntity(peticion.toString(), StandardCharsets.UTF_8);

            entidad.setContentType("application/json");
            operacion.setEntity(entidad);

            HttpResponse respuesta = cliente.execute(operacion);

            String salida = EntityUtils.toString(respuesta.getEntity());

            log.info("CADENA DE SALIDA");
            log.info(salida);
            log.info("");
            log.info("");

            JSONObject resultado = new JSONObject(salida);

            log.info("SALIDA JSON DEL SERVICIO ENVIAR MENSAJE");
            log.info(resultado.toString());
            log.info("");
            log.info("");

            if (respuesta.getStatusLine().getStatusCode() == 200) {

                if (resultado.get("codigo").toString().equals("0")) {

                    return 0;

                } else {

                    return 3;

                }

            } else {

                log.info("CODIGO DE RESPUESTA");
                log.info(respuesta.getStatusLine().getStatusCode() + "");
                log.info("");
                log.info("");

                return 2;

            }

        } catch (Exception e) {

            log.error("EXCEPCION");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return 1;

        }

    }

    public ResultadoIneModel servicio_ine(ValidarDatosIneModel solicitud) {

        RequestConfig configuracion = RequestConfig.custom()
                .setConnectTimeout(Integer.parseInt(PropiedadesUtilities.Variables.VALOR_TIMEPO_ESPERA_SERVICIOS))
                .setConnectionRequestTimeout(Integer.parseInt(PropiedadesUtilities.Variables.VALOR_TIMEPO_ESPERA_SERVICIOS))
                .setSocketTimeout(Integer.parseInt(PropiedadesUtilities.Variables.VALOR_TIMEPO_ESPERA_SERVICIOS)).build();

        HttpClient cliente = HttpClientBuilder.create().setDefaultRequestConfig(configuracion).build();

        HttpPost operacion = new HttpPost(PropiedadesUtilities.Variables.VALOR_DIRECCION_SERVICIO_VALIDAR_INE);
        operacion.addHeader("Content-Type", PropiedadesUtilities.Variables.VALOR_CABECERA_SERVICIOS);

        JSONObject datos_portal = new JSONObject();
        datos_portal.put("identificador", PropiedadesUtilities.Variables.VALOR_IDENTIFICADOR_SERVICIO_VALIDAR_CURP_INE_ENROLAMIENTO);
        datos_portal.put("nombre", PropiedadesUtilities.Variables.VALOR_CANAL_SERVICIO_VALIDAR_CURP_INE_ENROLAMIENTO);

        JSONObject datos_cliente = new JSONObject();
        datos_cliente.put("a_registro", solicitud.getA_registro().trim());
        datos_cliente.put("a_emision", solicitud.getA_emision().trim());
        datos_cliente.put("nombre", solicitud.getNombre().trim());
        datos_cliente.put("clave_unica", solicitud.getCurp().trim());
        datos_cliente.put("n_emision", solicitud.getN_emision().trim());
        datos_cliente.put("codigo_identificacion", solicitud.getC_identificacion().trim());
        datos_cliente.put("clave_elector", solicitud.getC_elector().trim());
        datos_cliente.put("apellido_paterno", solicitud.getA_paterno().trim());
        datos_cliente.put("apellido_materno", solicitud.getA_materno().trim());
        datos_cliente.put("reconocimiento_optico", solicitud.getOcr().trim());

        JSONObject peticion = new JSONObject();
        peticion.put("datos_portal", datos_portal);
        peticion.put("datos_cliente", datos_cliente);

        log.info("PARAMETROS DEL SERVICIO DE VALIDACION INE");
        log.info(peticion.toString());
        log.info("");
        log.info("");

        try {

            StringEntity entidad = new StringEntity(peticion.toString());

            operacion.setEntity(entidad);

            HttpResponse respuesta = cliente.execute(operacion);

            String salida = EntityUtils.toString(respuesta.getEntity());

            JSONObject resultado = new JSONObject(salida);

            log.info("RESPUESTA DEL SERVICIO VALIDAR INE");
            log.info(resultado.toString());
            log.info("");
            log.info("");

            ResultadoIneModel resultado_ine = new ResultadoIneModel();

            if (respuesta.getStatusLine().getStatusCode() == 200) {

                JSONObject parametros = new JSONObject(resultado.get("resultado").toString());

                resultado_ine.setCodigo_http(respuesta.getStatusLine().getStatusCode() + "");
                resultado_ine.setCodigo_servicio(parametros.get("codigo") + "");
                resultado_ine.setDescripcion_servicio(parametros.get("descripcion") + "");

                return resultado_ine;

            } else {

                log.info("CODIGO DE RESPUESTA");
                log.info(respuesta.getStatusLine().getStatusCode() + "");
                log.info("");
                log.info("");

                JSONObject parametros = new JSONObject(resultado.get("resultado").toString());

                resultado_ine.setCodigo_http(respuesta.getStatusLine().getStatusCode() + "");
                resultado_ine.setCodigo_servicio(parametros.get("codigo") + "");
                resultado_ine.setDescripcion_servicio(parametros.get("descripcion") + "");

                return resultado_ine;

            }

        } catch (Exception e) {

            log.error("EXCEPCION");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    public String valida_token(String token) {

        log.info("METODO VALIDAR TOKEN");
        log.info("");

        HttpHeaders encabezados = new HttpHeaders();
        RestTemplate plantilla = new RestTemplate();
        encabezados.add("Authorization", token);
        HttpEntity<String> solicitud = new HttpEntity<>(encabezados);

        try {

            String validacion = plantilla.postForObject(PropiedadesUtilities.Variables.VALOR_DIRECCION_SERVICIO_VALIDAR_TOKEN, solicitud, String.class);
            return StringEscapeUtils.unescapeHtml4(validacion);

        } catch (Exception e) {

            log.error("EXCEPCION");
            log.error(e.getMessage());
            log.error("");

            return null;

        }

    }

}
