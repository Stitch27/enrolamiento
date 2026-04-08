package com.fincomun.component;

import org.slf4j.MDC;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterceptorComponent implements HandlerInterceptor {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HHmmss");

    @Override
    public boolean preHandle(HttpServletRequest solicitud, HttpServletResponse respuesta, Object manejador) {

        String transaccion = solicitud.getHeader("transaccion");

        if (transaccion == null || transaccion.trim().isEmpty()) {

            LocalDateTime ahora = LocalDateTime.now();

            String fecha = ahora.format(FORMATO_FECHA);
            String hora = ahora.format(FORMATO_HORA);

            transaccion = "TRANS." + fecha + "." + hora;

        }

        MDC.put("transaccion", transaccion);
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest solicitud, HttpServletResponse respuesta, Object manejador, Exception excepcion) {

        MDC.clear();

    }

}
