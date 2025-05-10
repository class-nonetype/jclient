package com.jclient.clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Hashtable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.jclient.models.Route;
import com.jclient.models.requests.SessionRequest;
import com.jclient.models.responses.SignInResponse;
import com.jclient.models.requests.SignInRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class HTTPClient {



    // HttpClient con la configuración por defecto
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    // ObjectMapper retutilizable
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);



    public SignInResponse signIn(SignInRequest payload) throws IOException, InterruptedException {
        String jsonPayload = objectMapper.writeValueAsString(payload);




        System.out.println("[request] " + jsonPayload);
        System.out.println("[route] " + Route.signIn().toString());

        var request = HttpRequest.newBuilder()
                .uri(URI.create(Route.signIn().toString()))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        System.out.println("[request] " + request);


        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        System.out.println(response.statusCode());
        System.out.println(response.uri());
        System.out.println(response.request());
        System.out.println(response.headers());
        System.out.println(response.version());
        System.out.println(response.sslSession());
        System.out.println(response.toString());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), SignInResponse.class);
        } else {
            throw new RuntimeException("Error al crear usuario: HTTP " + response.statusCode());
        }
    }

    public SignInResponse verifySession(String token) throws IOException, InterruptedException {
        // Construye la URL con el token como query param
        String encoded = URLEncoder.encode(token, StandardCharsets.UTF_8);
        URI uri = URI.create(Route.verifySession() + "?Authorization=" + encoded);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())  // POST sin body
                .build();

        HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() == 200) {
            return objectMapper.readValue(resp.body(), SignInResponse.class);
        } else {
            throw new RuntimeException("Error verificando sesión: HTTP " + resp.statusCode());
        }
    }



}
