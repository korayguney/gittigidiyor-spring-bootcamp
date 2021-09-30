package com.example.testinet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class TestInetApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestInetApplication.class, args);
    }

    @Bean
    public String testInet() throws UnknownHostException {
        // Local address
        System.out.println("*********** 1 ************ " + InetAddress.getLocalHost().getHostAddress());
        System.out.println("*********** 2 ************ " + InetAddress.getLocalHost().getHostName());

        // Remote address
        System.out.println("*********** 3 ************ " +InetAddress.getLoopbackAddress().getHostAddress());
        System.out.println("*********** 4 ************ " +InetAddress.getLoopbackAddress().getHostName());

        return "test";
    }

}
