package com.gmail.javacoded78.filter;

import com.gmail.javacoded78.dto.UserPrincipalResponse;
import com.gmail.javacoded78.security.JwtAuthenticationException;
import com.gmail.javacoded78.security.JwtProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final JwtProvider jwtProvider;
    private final RestTemplate restTemplate;

    public AuthFilter(JwtProvider jwtProvider, RestTemplate restTemplate) {
        super(Config.class);
        this.jwtProvider = jwtProvider;
        this.restTemplate = restTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = jwtProvider.resolveToken(exchange.getRequest());
            boolean isTokenValid = jwtProvider.validateToken(token);

            if (token != null && isTokenValid) {
                String email = jwtProvider.parseToken(token);
                UserPrincipalResponse user = restTemplate.getForObject(
                        "http://user-service/api/v1/auth/user/{email}",
                        UserPrincipalResponse.class,
                        email
                );

                if (user.getActivationCode() != null) {
                    throw new JwtAuthenticationException("Email not activated");
                }
                exchange.getRequest()
                        .mutate()
                        .header("X-auth-user-id", String.valueOf(user.getId()))
                        .build();
                return chain.filter(exchange);
            } else {
                throw new JwtAuthenticationException("JWT token is expired or invalid");
            }
        };
    }

    public static class Config {
    }
}