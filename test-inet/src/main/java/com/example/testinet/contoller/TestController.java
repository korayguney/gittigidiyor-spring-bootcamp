package com.example.testinet.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class TestController {

    @GetMapping("/h1")
    public String getHostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    @GetMapping("/h2")
    public String getHostAddress1() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    @GetMapping("/h3")
    public String getHostAddress2() {
        return InetAddress.getLoopbackAddress().getHostAddress();
    }

    @GetMapping("/h4")
    public String getHostAddress3() {
        return InetAddress.getLoopbackAddress().getHostName();
    }
}
