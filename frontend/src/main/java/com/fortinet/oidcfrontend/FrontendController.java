package com.fortinet.oidcfrontend;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.*;
import org.springframework.security.oauth2.client.registration.*;
import org.springframework.security.oauth2.core.oidc.user.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.util.Collections;
import java.util.stream.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/frontend")
public class FrontendController {

    private final RestTemplate restTemplate;

    public FrontendController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping
    public String home(HttpServletRequest request) {
        StringBuilder response = new StringBuilder("Welcome to the frontend application!<br/><br/>");

        // Add URL and Headers
        response.append("Request URL: ").append(request.getRequestURL()).append("<br/>");
        response.append("Headers: <br/>");
        var headers = Collections.list(request.getHeaderNames()).stream()
                .map(headerName -> headerName + ": " + request.getHeader(headerName))
                .collect(Collectors.joining("<br/>"));
        response.append(headers);

        return response.toString();
    }

    @GetMapping("/call-backend")
    public String callBackend(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client) {
        String backendUrl = "http://localhost:8081/backend";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(client.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                backendUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}