package com.harvey.natto.web;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author harvey
 */
public class TomcatWebServer implements WebServer {
    @Override
    public void start(WebApplicationContext webApplicationContext) {
        System.out.println("starting tomcat http server");
        
        Tomcat tomcat = new Tomcat();
        
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");
        
        Connector connector = new Connector();
        connector.setPort(8080);
        
        Engine engine = new StandardEngine();
        engine.setDefaultHost("localhost");
        
        Host host = new StandardHost();
        host.setName("localhost");
        
        String contentPath = "";
        
        Context context = new StandardContext();
        context.setPath(contentPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());
        
        host.addChild(context);
        engine.addChild(host);
        
        service.setContainer(engine);
        service.addConnector(connector);
        
        tomcat.addServlet(context, "dispatcher", new DispatcherServlet(webApplicationContext));
        context.addServletMappingDecoded("/*", "dispatcher");
        
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
