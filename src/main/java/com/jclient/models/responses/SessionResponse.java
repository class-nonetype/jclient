package com.jclient.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record SessionResponse(
        @JsonProperty("user_account_id") String userAccountID,
        @JsonProperty("date") String date,
        @JsonProperty("access_token") String token
) {}

