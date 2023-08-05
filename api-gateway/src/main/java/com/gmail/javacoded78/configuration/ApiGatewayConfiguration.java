package com.gmail.javacoded78.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.socket.client.TomcatWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.springframework.web.reactive.socket.server.upgrade.TomcatRequestUpgradeStrategy;

@Configuration
public class ApiGatewayConfiguration {

//    @Bean
//    @Primary
//    WebSocketClient tomcatWebSocketClient() {
//        return new TomcatWebSocketClient();
//    }
//    @Bean
//    @Primary
//    public RequestUpgradeStrategy requestUpgradeStrategy() {
//        return new TomcatRequestUpgradeStrategy();
//    }
}
