package com.jclient.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record SessionResponse(
        @JsonProperty("userAccountID") String userAccountID,
        @JsonProperty("date") String date,
        @JsonProperty("accessToken") String token
) {}

