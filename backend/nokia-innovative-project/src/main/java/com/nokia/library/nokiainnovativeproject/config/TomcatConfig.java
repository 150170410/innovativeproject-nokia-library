package com.nokia.library.nokiainnovativeproject.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
    @Bean
    public TomcatServletWebServerFactory containerFactory() {
        return new TomcatServletWebServerFactory() {
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);
                connector.setMaxPostSize(-1);
                connector.setMaxSavePostSize(-1);
                if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {

                    ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
                }
            }
        };
    }
}
