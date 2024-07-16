package com.harvey.demo;

import com.harvey.natto.NattoApplication;
import com.harvey.natto.NattoBootApplication;

/**
 * @author harvey
 */
@NattoBootApplication
public class Application {
    public static void main(String[] args) {
        NattoApplication.run(Application.class);
    }
}
