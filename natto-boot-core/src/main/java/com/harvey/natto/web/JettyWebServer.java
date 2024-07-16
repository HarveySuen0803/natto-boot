package com.harvey.natto.web;

import org.springframework.web.context.WebApplicationContext;

/**
 * @author harvey
 */
public class JettyWebServer implements WebServer {
    @Override
    public void start(WebApplicationContext webApplicationContext) {
        System.out.println("starting jetty http server");
    }
}
