package com.jclient.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


// Esquema de la respuesta obtenida en POST /authentication/sign-in
@JsonIgnoreProperties(ignoreUnknown = true)
public record SignInResponse(
        @JsonProperty("userAccountID") String userAccountID,
        @JsonProperty("date") String date,
        @JsonProperty("accessToken") String token
) {}

