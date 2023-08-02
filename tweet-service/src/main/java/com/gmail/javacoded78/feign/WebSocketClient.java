package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.gmail.javacoded78.constants.FeignConstants.WEBSOCKET_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_WEBSOCKET;

@FeignClient(name = WEBSOCKET_SERVICE, configuration = FeignConfiguration.class)
public interface WebSocketClient {

    @PostMapping(API_V1_WEBSOCKET)
    void send(@RequestParam("destination") String destination, @RequestBody Object request);
}
