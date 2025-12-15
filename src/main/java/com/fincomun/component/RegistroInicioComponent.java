package com.fincomun.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RegistroInicioComponent {

    private static final Logger log = LoggerFactory.getLogger("com.fincomun");

    @PostConstruct
    public void init() {

        log.info("");
        log.info("");
        log.info("---------- APLICACION DE ENROLAMIENTO INICIADA ----------");
        log.info("");
        log.info("");

    }

}
