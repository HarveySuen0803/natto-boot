package com.harvey.natto;

import com.harvey.natto.web.WebServer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Map;

/**
 * @author harvey
 */
public class NattoApplication {
    public static void run(Class<?> clazz) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(clazz);
        applicationContext.refresh();
        
        startHttpServer(applicationContext);
    }
    
    public static void startHttpServer(WebApplicationContext webApplicationContext) {
        Map<String, WebServer> webServers = webApplicationContext.getBeansOfType(WebServer.class);
        
        if (webServers.isEmpty()) {
            throw new RuntimeException();
        }
        
        if (webServers.size() > 1) {
            throw new RuntimeException();
        }
        
        WebServer webServer = webServers.values().stream().findFirst().get();
        
        webServer.start(webApplicationContext);
    }
}
