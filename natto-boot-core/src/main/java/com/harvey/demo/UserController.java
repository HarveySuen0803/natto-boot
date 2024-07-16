package com.harvey.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author harvey
 */
@RestController
public class UserController {
    @GetMapping("/user/hello")
    public String hello() {
        return "hello world";
    }
}
