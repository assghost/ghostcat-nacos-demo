package com.ghostcat.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
public class NacosGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest orgRequest = exchange.getRequest();
        String orgUrl = orgRequest.getPath().pathWithinApplication().value();
        String methodValue = orgRequest.getMethodValue();

        log.info("request url : {}", orgUrl);
        log.info("request method : {}", methodValue);

        //ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        return chain.filter(exchange).then(Mono.fromRunnable(
                () -> {
                    log.info("response finish");
                }
        ));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
