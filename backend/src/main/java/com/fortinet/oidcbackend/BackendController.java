package com.fortinet.oidcbackend;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.stream.*;

@RestController
@RequestMapping("/backend")
public class BackendController {

    @GetMapping
    public String home(HttpServletRequest request) {
        StringBuilder response = new StringBuilder("Welcome to the backend application! Token verified successfully.<br/><br/>");

        // Add URL and Headers
        response.append("Request URL: ").append(request.getRequestURL()).append("<br/>");
        response.append("Headers: <br/>");
        var headers = Collections.list(request.getHeaderNames()).stream()
                .map(headerName -> headerName + ": " + request.getHeader(headerName))
                .collect(Collectors.joining("<br/>"));
        response.append(headers);

        return response.toString();
    }
}