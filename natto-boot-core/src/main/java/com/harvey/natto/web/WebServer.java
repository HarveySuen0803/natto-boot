package com.harvey.natto.web;

import org.springframework.web.context.WebApplicationContext;

/**
 * @author harvey
 */
public interface WebServer {
    void start(WebApplicationContext webApplicationContext);
}
