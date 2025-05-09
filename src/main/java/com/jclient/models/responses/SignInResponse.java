package com.jclient.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Esquema de la respuesta obtenida en POST /authentication/sign-in
public record SignInResponse(
        @JsonProperty("user_account_id") String userAccountID,
        @JsonProperty("access_token") String token
) {}

/*
package com.jclient.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SignInResponse(
    @JsonProperty("access_token") String token
    // si luego te interesa userAccountId:
    //, @JsonProperty("user_account_id") String userAccountId
) {}

 */