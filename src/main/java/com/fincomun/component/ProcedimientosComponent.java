package com.fincomun.component;

import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import oracle.jdbc.OracleTypes;
import lombok.extern.slf4j.Slf4j;
import java.sql.CallableStatement;
import com.fincomun.model.IneModel;
import com.fincomun.model.CurpModel;
import com.fincomun.model.IncodeModel;
import com.fincomun.model.ClienteModel;
import com.fincomun.model.DatosBitacoraModel;
import com.fincomun.model.DetalleBitacoraModel;
import com.fincomun.utilities.ConexionUtilities;
import org.springframework.stereotype.Component;
import com.fincomun.model.RegistrosBitacoraModel;
import com.fincomun.model.ResultadosBitacoraModel;
import com.fincomun.utilities.PropiedadesUtilities;

@Slf4j
@Component
public class ProcedimientosComponent extends ConexionUtilities {

    private Connection abrir_conexion() {

        try {

            return this.conexion();

        } catch (Exception e) {

            log.error("ERROR AL ABRIR LA CONEXION DE BASE DE DATOS");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    private void cerrar_conexion(Integer tipo, CallableStatement declaracion, ResultSet resultado, Connection conexion) {

        try {

            if (tipo == 1) {

                conexion.close();
                declaracion.close();
                resultado.close();

            } else {

                conexion.close();
                declaracion.close();

            }

        } catch (Exception e) {

            log.error("ERROR AL CERRAR LA CONEXION DE BASE DE DATOS");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

        }

    }

    public List<ClienteModel> validar_cliente(String numero_celular) {

        ResultSet registros = null;
        CallableStatement declaracion = null;
        List<ClienteModel> clientes = new ArrayList<>();

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_VALIDAR_CLIENTE + "(?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, numero_celular);
                declaracion.registerOutParameter(2, OracleTypes.CURSOR);
                declaracion.execute();

                registros = (ResultSet) declaracion.getObject(2);

                while (registros.next()) {

                    ClienteModel registro = new ClienteModel();

                    registro.setMov_id(registros.getLong(1));
                    registro.setMov_usuario(registros.getString(2));

                    clientes.add(registro);

                }

                cerrar_conexion(1, declaracion, registros, conexion);

                return clientes;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, registros, conexion);

                log.error("ERROR AL VALIDAR LA INFORMACION DEL CLIENTE");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return null;

            }

        } else {

            return null;

        }

    }

    public Integer registrar_enrolamiento(String numero_cliente, String telefono_celular, String correo_electronico, String curp,
            String firma, Integer estatus) {

        CallableStatement declaracion = null;

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_REGISTRAR_ENROLAMIENTO + "(?, ?, ?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, numero_cliente);
                declaracion.setString(2, telefono_celular);
                declaracion.setString(3, correo_electronico);
                declaracion.setString(4, curp);
                declaracion.setString(5, firma);
                declaracion.setInt(6, estatus);
                declaracion.registerOutParameter(7, OracleTypes.NUMBER);
                declaracion.execute();

                Integer resultado = declaracion.getInt(7);
                cerrar_conexion(0, declaracion, null, conexion);

                return resultado;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, null, conexion);

                log.error("ERROR AL REGISTRAR EL ENROLAMIENTO");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return 2;

            }

        }

        return 2;

    }

    public Integer registrar_aviso_contrato(String numero_cliente, String aviso_privacidad, String contrato_servicios) {

        CallableStatement declaracion = null;

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_REGISTRAR_AVISO_CONTRATO + "(?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, numero_cliente);
                declaracion.setString(2, aviso_privacidad);
                declaracion.setString(3, contrato_servicios);
                declaracion.registerOutParameter(4, OracleTypes.NUMBER);
                declaracion.execute();

                Integer resultado = declaracion.getInt(4);
                cerrar_conexion(0, declaracion, null, conexion);

                return resultado;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, null, conexion);

                log.error("ERROR AL REGISTRAR EL AVISO CON EL CONTRATO");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return 2;

            }

        }

        return 2;

    }

    public Integer registrar_bitacora(String numero_cliente, String curp, String nombre, String apellido_paterno,
            String apellido_materno, String latitud, String longitud, Integer estatus, String folio) {

        CallableStatement declaracion = null;

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_REGISTRAR_BITACORA + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, numero_cliente);
                declaracion.setString(2, curp);
                declaracion.setString(3, nombre);
                declaracion.setString(4, apellido_paterno);
                declaracion.setString(5, apellido_materno);
                declaracion.setString(6, latitud);
                declaracion.setString(7, longitud);
                declaracion.setInt(8, estatus);
                declaracion.setString(9, folio);
                declaracion.registerOutParameter(10, OracleTypes.NUMBER);
                declaracion.execute();

                Integer resultado = declaracion.getInt(10);
                cerrar_conexion(0, declaracion, null, conexion);

                return resultado;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, null, conexion);

                log.error("ERROR AL REGISTRAR LA INFORMACION EN BITACORA");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return 2;

            }

        }

        return 2;

    }

    public Integer registrar_datos_curp(String numero_cliente, String curp_ingresada, String curp_registrada, String nombre,
            String apellido_paterno, String apellido_materno, String fecha_nacimiento, Integer estatus, String genero, String entidad_nacimiento, String folio) {

        CallableStatement declaracion = null;

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_REGISTRAR_VALIDACION_DATOS_CURP + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, numero_cliente);
                declaracion.setString(2, curp_ingresada);
                declaracion.setString(3, curp_registrada);
                declaracion.setString(4, nombre);
                declaracion.setString(5, apellido_paterno);
                declaracion.setString(6, apellido_materno);
                declaracion.setString(7, fecha_nacimiento);
                declaracion.setInt(8, estatus);
                declaracion.setString(9, genero);
                declaracion.setString(10, entidad_nacimiento);
                declaracion.setString(11, folio);
                declaracion.registerOutParameter(12, OracleTypes.NUMBER);
                declaracion.execute();

                Integer resultado = declaracion.getInt(12);
                cerrar_conexion(0, declaracion, null, conexion);

                return resultado;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, null, conexion);

                log.error("ERROR AL REGISTRAR LOS DATOS DE VALIDACION DEL SERVICIO DE CURP");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return 2;

            }

        }

        return 2;

    }

    public Integer registrar_bitacora_incode(String numero_cliente, String curp, String nombre, String apellido_paterno,
            String apellido_materno, Integer estatus, String identificador_incode, String selfie, String video_selfie,
            String prueba_vida, String folio) {

        CallableStatement declaracion = null;

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_REGISTRAR_BITACORA_INCODE + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, numero_cliente);
                declaracion.setString(2, curp);
                declaracion.setString(3, nombre);
                declaracion.setString(4, apellido_paterno);
                declaracion.setString(5, apellido_materno);
                declaracion.setInt(6, estatus);
                declaracion.setString(7, identificador_incode);
                declaracion.setString(8, selfie);
                declaracion.setString(9, video_selfie);
                declaracion.setString(10, prueba_vida);
                declaracion.setString(11, folio);
                declaracion.registerOutParameter(12, OracleTypes.NUMBER);
                declaracion.execute();

                Integer resultado = declaracion.getInt(12);
                cerrar_conexion(0, declaracion, null, conexion);

                return resultado;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, null, conexion);

                log.error("ERROR AL REGISTRAR LA INFORMACION EN BITACORA (INCODE)");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return 2;

            }

        }

        return 2;

    }

    public Integer registrar_datos_ine(String n_cliente, String a_registro, String a_emision, String nombre, String curp,
            String n_emision, String c_identificacion, String c_elector, String a_paterno, String a_materno, String ocr,
            Integer estatus, String folio) {

        CallableStatement declaracion = null;

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_REGISTRAR_VALIDACION_DATOS_INE + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, n_cliente);
                declaracion.setString(2, a_registro);
                declaracion.setString(3, a_emision);
                declaracion.setString(4, nombre);
                declaracion.setString(5, curp);
                declaracion.setString(6, n_emision);
                declaracion.setString(7, c_identificacion);
                declaracion.setString(8, c_elector);
                declaracion.setString(9, a_paterno);
                declaracion.setString(10, a_materno);
                declaracion.setString(11, ocr);
                declaracion.setInt(12, estatus);
                declaracion.setString(13, folio);
                declaracion.registerOutParameter(14, OracleTypes.NUMBER);
                declaracion.execute();

                Integer resultado = declaracion.getInt(14);
                cerrar_conexion(0, declaracion, null, conexion);

                return resultado;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, null, conexion);

                log.error("ERROR AL REGISTRAR LOS DATOS DE VALIDACION DEL SERVICIO DE INE");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return 2;

            }

        }

        return 2;

    }

    public ResultadosBitacoraModel consultar_bitacora_nombre(String nombre, Integer n_pagina, Integer n_registros) {

        ResultSet registros = null;
        CallableStatement declaracion = null;
        List<DatosBitacoraModel> datos = new ArrayList<>();

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_CONSULTAR_BITACORA_NOMBRE + "(?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, nombre);
                declaracion.setInt(2, n_pagina);
                declaracion.setInt(3, n_registros);
                declaracion.registerOutParameter(4, OracleTypes.INTEGER);
                declaracion.registerOutParameter(5, OracleTypes.CURSOR);
                declaracion.execute();

                registros = (ResultSet) declaracion.getObject(5);

                while (registros.next()) {

                    IneModel ine = null;
                    IncodeModel incode = null;
                    CurpModel curp = new CurpModel();

                    curp.setCurp_ingresada(registros.getString("CURP_INGRESADA_VAR"));
                    curp.setCurp_registrada(registros.getString("CURP_REGISTRADA_VAR"));
                    curp.setFecha_nacimiento(registros.getString("FECHA_NACIMIENTO_VAR"));
                    curp.setFecha_registro(registros.getString("FECHA_REGISTRO_VAR"));
                    curp.setHora_registro(registros.getString("HORA_REGISTRO_VAR"));
                    curp.setGenero(registros.getString("GENERO_VAR"));
                    curp.setEntidad_nacimiento(registros.getString("ENTIDAD_NACIMIENTO_VAR"));
                    curp.setValor_estatus(registros.getString("ESTATUS_VALOR_CURP"));
                    curp.setDescripcion_estatus(registros.getString("ESTATUS_DESCRIPCION_CURP"));

                    if (registros.getString("A_REGISTRO_VAR") != null && !registros.getString("A_REGISTRO_VAR").trim().isEmpty()) {

                        ine = new IneModel();

                        ine.setA_registro(registros.getString("A_REGISTRO_VAR"));
                        ine.setA_emision(registros.getString("A_EMISION_VAR"));
                        ine.setN_emision(registros.getString("N_EMISION_VAR"));
                        ine.setC_identificacion(registros.getString("CODIGO_IDENTIFICACION_VAR"));
                        ine.setC_elector(registros.getString("CLAVE_ELECTOR_VAR"));
                        ine.setOcr(registros.getString("OCR_VAR"));
                        ine.setFecha_registro(registros.getString("FECHA_REGISTRO_INE"));
                        ine.setHora_registro(registros.getString("HORA_REGISTRO_INE"));
                        ine.setValor_estatus(registros.getString("ESTATUS_VALOR_INE"));
                        ine.setDescripcion_estatus(registros.getString("ESTATUS_DESCRIPCION_INE"));

                    }

                    if (registros.getString("SELFIE_VAR") != null && !registros.getString("SELFIE_VAR").trim().isEmpty()) {

                        incode = new IncodeModel();

                        incode.setIdentificador_incode(registros.getString("IDENTIFICADOR_INCODE_VAR"));
                        incode.setSelfie(registros.getString("SELFIE_VAR"));
                        incode.setVideo_selfie(registros.getString("VIDEO_SELFIE_VAR"));
                        incode.setPrueba_vida(registros.getString("PRUEBA_VIDA_VAR"));
                        incode.setFecha_registro(registros.getString("FECHA_REGISTRO_INCODE"));
                        incode.setHora_registro(registros.getString("HORA_REGISTRO_INCODE"));
                        incode.setValor_estatus(registros.getString("ESTATUS_VALOR_INCODE"));
                        incode.setDescripcion_estatus(registros.getString("ESTATUS_DESCRIPCION_INCODE"));

                    }

                    RegistrosBitacoraModel registros_bitacora = new RegistrosBitacoraModel();

                    registros_bitacora.setCurp(curp);
                    registros_bitacora.setIne(ine);
                    registros_bitacora.setIncode(incode);

                    DatosBitacoraModel bitacora = new DatosBitacoraModel();

                    bitacora.setNombre(registros.getString("NOMBRE_VAR"));
                    bitacora.setA_paterno(registros.getString("APELLIDO_PATERNO_VAR"));
                    bitacora.setA_materno(registros.getString("APELLIDO_MATERNO_VAR"));
                    bitacora.setN_cliente(registros.getString("NUMERO_CLIENTE_VAR"));
                    bitacora.setFolio_r(registros.getString("FOLIO_VAR"));
                    bitacora.setRegistros(registros_bitacora);

                    datos.add(bitacora);

                }

                ResultadosBitacoraModel resultados = new ResultadosBitacoraModel();
                resultados.setTotal_registros(declaracion.getInt(4) + "");
                resultados.setRegistros(datos);

                cerrar_conexion(1, declaracion, registros, conexion);

                return resultados;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, null, conexion);

                log.error("ERROR AL CONSULTAR LA INFORMACION DE LAS BITACORAS POR NOMBRE");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return null;

            }

        } else {

            return null;

        }

    }

    public ResultadosBitacoraModel consultar_bitacora_fecha(String f_inicio, String f_fin, Integer n_pagina, Integer n_registros) {

        ResultSet registros = null;
        CallableStatement declaracion = null;
        List<DatosBitacoraModel> datos = new ArrayList<>();

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_CONSULTAR_BITACORA_FECHA + "(?, ?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, f_inicio);
                declaracion.setString(2, f_fin);
                declaracion.setInt(3, n_pagina);
                declaracion.setInt(4, n_registros);
                declaracion.registerOutParameter(5, OracleTypes.INTEGER);
                declaracion.registerOutParameter(6, OracleTypes.CURSOR);
                declaracion.execute();

                registros = (ResultSet) declaracion.getObject(6);

                while (registros.next()) {

                    IneModel ine = null;
                    IncodeModel incode = null;
                    CurpModel curp = new CurpModel();

                    curp.setCurp_ingresada(registros.getString("CURP_INGRESADA_VAR"));
                    curp.setCurp_registrada(registros.getString("CURP_REGISTRADA_VAR"));
                    curp.setFecha_nacimiento(registros.getString("FECHA_NACIMIENTO_VAR"));
                    curp.setFecha_registro(registros.getString("FECHA_REGISTRO_VAR"));
                    curp.setHora_registro(registros.getString("HORA_REGISTRO_VAR"));
                    curp.setGenero(registros.getString("GENERO_VAR"));
                    curp.setEntidad_nacimiento(registros.getString("ENTIDAD_NACIMIENTO_VAR"));
                    curp.setValor_estatus(registros.getString("ESTATUS_VALOR_CURP"));
                    curp.setDescripcion_estatus(registros.getString("ESTATUS_DESCRIPCION_CURP"));

                    if (registros.getString("A_REGISTRO_VAR") != null && !registros.getString("A_REGISTRO_VAR").trim().isEmpty()) {

                        ine = new IneModel();

                        ine.setA_registro(registros.getString("A_REGISTRO_VAR"));
                        ine.setA_emision(registros.getString("A_EMISION_VAR"));
                        ine.setN_emision(registros.getString("N_EMISION_VAR"));
                        ine.setC_identificacion(registros.getString("CODIGO_IDENTIFICACION_VAR"));
                        ine.setC_elector(registros.getString("CLAVE_ELECTOR_VAR"));
                        ine.setOcr(registros.getString("OCR_VAR"));
                        ine.setFecha_registro(registros.getString("FECHA_REGISTRO_INE"));
                        ine.setHora_registro(registros.getString("HORA_REGISTRO_INE"));
                        ine.setValor_estatus(registros.getString("ESTATUS_VALOR_INE"));
                        ine.setDescripcion_estatus(registros.getString("ESTATUS_DESCRIPCION_INE"));

                    }

                    if (registros.getString("SELFIE_VAR") != null && !registros.getString("SELFIE_VAR").trim().isEmpty()) {

                        incode = new IncodeModel();

                        incode.setIdentificador_incode(registros.getString("IDENTIFICADOR_INCODE_VAR"));
                        incode.setSelfie(registros.getString("SELFIE_VAR"));
                        incode.setVideo_selfie(registros.getString("VIDEO_SELFIE_VAR"));
                        incode.setPrueba_vida(registros.getString("PRUEBA_VIDA_VAR"));
                        incode.setFecha_registro(registros.getString("FECHA_REGISTRO_INCODE"));
                        incode.setHora_registro(registros.getString("HORA_REGISTRO_INCODE"));
                        incode.setValor_estatus(registros.getString("ESTATUS_VALOR_INCODE"));
                        incode.setDescripcion_estatus(registros.getString("ESTATUS_DESCRIPCION_INCODE"));

                    }

                    RegistrosBitacoraModel registros_bitacora = new RegistrosBitacoraModel();

                    registros_bitacora.setCurp(curp);
                    registros_bitacora.setIne(ine);
                    registros_bitacora.setIncode(incode);

                    DatosBitacoraModel bitacora = new DatosBitacoraModel();

                    bitacora.setNombre(registros.getString("NOMBRE_VAR"));
                    bitacora.setA_paterno(registros.getString("APELLIDO_PATERNO_VAR"));
                    bitacora.setA_materno(registros.getString("APELLIDO_MATERNO_VAR"));
                    bitacora.setN_cliente(registros.getString("NUMERO_CLIENTE_VAR"));
                    bitacora.setFolio_r(registros.getString("FOLIO_VAR"));
                    bitacora.setRegistros(registros_bitacora);

                    datos.add(bitacora);

                }

                ResultadosBitacoraModel resultados = new ResultadosBitacoraModel();
                resultados.setTotal_registros(declaracion.getInt(5) + "");
                resultados.setRegistros(datos);

                cerrar_conexion(1, declaracion, registros, conexion);

                return resultados;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, null, conexion);

                log.error("ERROR AL CONSULTAR LA INFORMACION DE LAS BITACORAS POR FECHAS");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return null;

            }

        } else {

            return null;

        }

    }

    public List<DetalleBitacoraModel> detalle_cliente(String n_cliente, String folio_r) {

        ResultSet registros = null;
        CallableStatement declaracion = null;
        List<DetalleBitacoraModel> datos = new ArrayList<>();

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseOracle.ESQUEMA_BASE_DATOS_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE + "."
                    + PropiedadesUtilities.BaseOracle.PROCEDIMIENTO_CONSULTAR_DETALLE_CLIENTE + "(?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, n_cliente);
                declaracion.setString(2, folio_r);
                declaracion.registerOutParameter(3, OracleTypes.CURSOR);
                declaracion.execute();

                registros = (ResultSet) declaracion.getObject(3);

                while (registros.next()) {

                    IneModel ine = null;
                    IncodeModel incode = null;
                    CurpModel curp = new CurpModel();

                    curp.setCurp_ingresada(registros.getString("CURP_INGRESADA_VAR"));
                    curp.setCurp_registrada(registros.getString("CURP_REGISTRADA_VAR"));
                    curp.setFecha_nacimiento(registros.getString("FECHA_NACIMIENTO_VAR"));
                    curp.setFecha_registro(registros.getString("FECHA_REGISTRO_VAR"));
                    curp.setHora_registro(registros.getString("HORA_REGISTRO_VAR"));
                    curp.setGenero(registros.getString("GENERO_VAR"));
                    curp.setEntidad_nacimiento(registros.getString("ENTIDAD_NACIMIENTO_VAR"));
                    curp.setValor_estatus(registros.getString("ESTATUS_VALOR_CURP"));
                    curp.setDescripcion_estatus(registros.getString("ESTATUS_DESCRIPCION_CURP"));

                    if (registros.getString("A_REGISTRO_VAR") != null && !registros.getString("A_REGISTRO_VAR").trim().isEmpty()) {

                        ine = new IneModel();

                        ine.setA_registro(registros.getString("A_REGISTRO_VAR"));
                        ine.setA_emision(registros.getString("A_EMISION_VAR"));
                        ine.setN_emision(registros.getString("N_EMISION_VAR"));
                        ine.setC_identificacion(registros.getString("CODIGO_IDENTIFICACION_VAR"));
                        ine.setC_elector(registros.getString("CLAVE_ELECTOR_VAR"));
                        ine.setOcr(registros.getString("OCR_VAR"));
                        ine.setFecha_registro(registros.getString("FECHA_REGISTRO_INE"));
                        ine.setHora_registro(registros.getString("HORA_REGISTRO_INE"));
                        ine.setValor_estatus(registros.getString("ESTATUS_VALOR_INE"));
                        ine.setDescripcion_estatus(registros.getString("ESTATUS_DESCRIPCION_INE"));

                    }

                    if (registros.getString("SELFIE_VAR") != null && !registros.getString("SELFIE_VAR").trim().isEmpty()) {

                        incode = new IncodeModel();

                        incode.setIdentificador_incode(registros.getString("IDENTIFICADOR_INCODE_VAR"));
                        incode.setSelfie(registros.getString("SELFIE_VAR"));
                        incode.setVideo_selfie(registros.getString("VIDEO_SELFIE_VAR"));
                        incode.setPrueba_vida(registros.getString("PRUEBA_VIDA_VAR"));
                        incode.setFecha_registro(registros.getString("FECHA_REGISTRO_INCODE"));
                        incode.setHora_registro(registros.getString("HORA_REGISTRO_INCODE"));
                        incode.setValor_estatus(registros.getString("ESTATUS_VALOR_INCODE"));
                        incode.setDescripcion_estatus(registros.getString("ESTATUS_DESCRIPCION_INCODE"));

                    }

                    RegistrosBitacoraModel registros_bitacora = new RegistrosBitacoraModel();

                    registros_bitacora.setCurp(curp);
                    registros_bitacora.setIne(ine);
                    registros_bitacora.setIncode(incode);

                    DetalleBitacoraModel bitacora = new DetalleBitacoraModel();

                    bitacora.setNombre(registros.getString("NOMBRE_VAR"));
                    bitacora.setA_paterno(registros.getString("APELLIDO_PATERNO_VAR"));
                    bitacora.setA_materno(registros.getString("APELLIDO_MATERNO_VAR"));
                    bitacora.setN_cliente(registros.getString("NUMERO_CLIENTE_VAR"));
                    bitacora.setRegistros(registros_bitacora);

                    datos.add(bitacora);

                }

                cerrar_conexion(1, declaracion, registros, conexion);

                return datos;

            } catch (Exception e) {

                cerrar_conexion(0, declaracion, null, conexion);

                log.error("ERROR AL CONSULTAR LA INFORMACION DETALLADA DEL CLIENTE");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return null;

            }

        } else {

            return null;

        }

    }

}
