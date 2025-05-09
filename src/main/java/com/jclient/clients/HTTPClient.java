package com.jclient.clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Hashtable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jclient.models.Route;
import com.jclient.models.responses.SignInResponse;
import com.jclient.models.requests.SignInRequest;



public class HTTPClient {



    // HttpClient con la configuración por defecto
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    // ObjectMapper retutilizable
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public SignInResponse signIn(SignInRequest payload) throws IOException, InterruptedException {
        String jsonRequest = objectMapper.writeValueAsString(payload);

        System.out.println(jsonRequest);
        System.out.println(Route.signIn().toString());

        var request = HttpRequest.newBuilder()
                .uri(URI.create(Route.signIn().toString()))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        System.out.println(request);


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
}
