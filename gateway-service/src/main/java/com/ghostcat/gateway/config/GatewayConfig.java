package com.ghostcat.gateway.config;

import com.ghostcat.gateway.filter.NacosGatewayFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class GatewayConfig {

    @Bean
    @Order(0)
    public GlobalFilter nacos()
    {
        return new NacosGatewayFilter();
    }

}
