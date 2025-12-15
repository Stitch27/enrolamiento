package com.fincomun.utilities;

public class PropiedadesUtilities {

    public static class Weblogic {

        public Weblogic() {

        }

        public static final String WEBLOGIC_USUARIO = ConfiguracionPropiedadesUtilities.propiedad("weblogic.usuario");
        public static final String WEBLOGIC_CLAVE = ConfiguracionPropiedadesUtilities.propiedad("weblogic.clave");

    }

    public static class BaseOracle {

        public BaseOracle() {

        }

        public static final String ESQUEMA_BASE_DATOS_ORACLE = ConfiguracionPropiedadesUtilities.propiedad("esquema.base.datos.oracle");
        public static final String PAQUETE_MIDDLEWARE_ENROLAMIENTO_ORACLE = ConfiguracionPropiedadesUtilities.propiedad("paquete.middleware.enrolamiento.oracle");
        public static final String PROCEDIMIENTO_VALIDAR_CLIENTE = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.validar.cliente");
        public static final String PROCEDIMIENTO_REGISTRAR_ENROLAMIENTO = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.registrar.enrolamiento");
        public static final String PROCEDIMIENTO_REGISTRAR_AVISO_CONTRATO = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.registrar.aviso.contrato");
        public static final String PROCEDIMIENTO_REGISTRAR_BITACORA = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.registrar.bitacora");
        public static final String PROCEDIMIENTO_REGISTRAR_VALIDACION_DATOS_CURP = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.registrar.validacion.datos.curp");
        public static final String PROCEDIMIENTO_REGISTRAR_BITACORA_INCODE = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.registrar.bitacora.incode");
        public static final String PROCEDIMIENTO_REGISTRAR_VALIDACION_DATOS_INE = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.registrar.validacion.datos.ine");
        public static final String PROCEDIMIENTO_CONSULTAR_BITACORA_NOMBRE = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.consultar.bitacora.nombre");
        public static final String PROCEDIMIENTO_CONSULTAR_BITACORA_FECHA = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.consultar.bitacora.fecha");
        public static final String PROCEDIMIENTO_CONSULTAR_DETALLE_CLIENTE = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.consultar.detalle.cliente");

    }

    public static class Variables {

        public Variables() {

        }

        public static final String VALOR_AVISO_CONTRATO = ConfiguracionPropiedadesUtilities.propiedad("valor.aviso.contrato");
        public static final String VALOR_TIMEPO_ESPERA_SERVICIOS = ConfiguracionPropiedadesUtilities.propiedad("valor.tiempo.espera.servicios");
        public static final String VALOR_CABECERA_SERVICIOS = ConfiguracionPropiedadesUtilities.propiedad("valor.cabecera.servicios");
        public static final String VALOR_DIRECCION_SERVICIO_VALIDAR_CURP = ConfiguracionPropiedadesUtilities.propiedad("valor.direccion.servicio.validar.curp");
        public static final String VALOR_DIRECCION_SERVICIO_VALIDAR_INE = ConfiguracionPropiedadesUtilities.propiedad("valor.direccion.servicio.validar.ine");
        public static final String VALOR_CANAL_SERVICIO_VALIDAR_CURP_INE_ENROLAMIENTO = ConfiguracionPropiedadesUtilities.propiedad("valor.canal.servicio.validar.curp.ine.enrolamiento");
        public static final String VALOR_IDENTIFICADOR_SERVICIO_VALIDAR_CURP_INE_ENROLAMIENTO = ConfiguracionPropiedadesUtilities.propiedad("valor.identificador.servicio.validar.curp.ine.enrolamiento");
        public static final String VALOR_TIPO_PROCESO_SERVICIO_VALIDAR_CURP = ConfiguracionPropiedadesUtilities.propiedad("valor.tipo.proceso.servicio.validar.curp");
        public static final String VALOR_DIRECCION_SERVICIO_ENVIAR_MENSAJE = ConfiguracionPropiedadesUtilities.propiedad("valor.direccion.servicio.enviar.mensaje");
        public static final String VALOR_VALIDACION_CAMPO_CURP = ConfiguracionPropiedadesUtilities.propiedad("valor.validacion.campo.curp");
        public static final String VALOR_VALIDACION_CAMPO_NOMBRE = ConfiguracionPropiedadesUtilities.propiedad("valor.validacion.campo.nombre");
        public static final String VALOR_VALIDACION_CAMPO_APELLIDO_PATERNO = ConfiguracionPropiedadesUtilities.propiedad("valor.validacion.campo.apellido.paterno");
        public static final String VALOR_VALIDACION_CAMPO_APELLIDO_MATERNO = ConfiguracionPropiedadesUtilities.propiedad("valor.validacion.campo.apellido.materno");
        public static final String VALOR_VALIDACION_CAMPO_FECHA_NACIMIENTO = ConfiguracionPropiedadesUtilities.propiedad("valor.validacion.campo.fecha.nacimiento");
        public static final String VALOR_VALIDACION_CAMPO_GENERO = ConfiguracionPropiedadesUtilities.propiedad("valor.validacion.campo.genero");
        public static final String VALOR_VALIDACION_CAMPO_ENTIDAD_NACIMIENTO = ConfiguracionPropiedadesUtilities.propiedad("valor.validacion.campo.entidad.nacimiento");
        public static final String VALOR_DIRECCION_SERVICIO_VALIDAR_TOKEN = ConfiguracionPropiedadesUtilities.propiedad("valor.direccion.servicio.validar.token");

    }

    public static class Encriptacion {

        public Encriptacion() {

        }

        public static final String VALOR_TIPO_CIFRADO = ConfiguracionPropiedadesUtilities.propiedad("valor.tipo.cifrado");
        public static final String VALOR_CLAVE_ESPECIFICA = ConfiguracionPropiedadesUtilities.propiedad("valor.clave.especifica");
        public static final String VALOR_TIPO_ALGORITMO = ConfiguracionPropiedadesUtilities.propiedad("valor.tipo.algoritmo");
        public static final String VALOR_PARAMETRO_ESPECIFICO = ConfiguracionPropiedadesUtilities.propiedad("valor.parametro.especifico");

    }

}
