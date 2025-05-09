package com.jclient.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


// Esquema de la respuesta obtenida en POST /authentication/sign-in
@JsonIgnoreProperties(ignoreUnknown = true)
public record SignInResponse(
        @JsonProperty("user_account_id") String userAccountID,
        @JsonProperty("date") String date,
        @JsonProperty("access_token") String token
) {}

