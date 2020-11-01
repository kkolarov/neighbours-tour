package com.kamen.NeighboursTour.oauth;

public interface AccessTokenValidator {
    AccessTokenValidationResult validate(String accessToken);
}