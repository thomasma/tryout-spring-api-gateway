package com.tryout.springgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class TryoutSpringGatewayApplication {
	@Autowired
	private UriConfiguration httpUri;

	public static void main(String[] args) {
		SpringApplication.run(TryoutSpringGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				// rewrite URL and route to my blog site
				.route(p -> p
						.path("/myblog")
						.filters(f -> f.setPath("/"))
						.uri(httpUri.getMyblog()))
				// rewrite URL and route to my spring guides site
				.route(p -> p
						.path("/spring")
						.filters(f -> f.setPath("/guides"))
						.uri(httpUri.getSpring()))
				// add request header before routing to httpbin
				.route(p -> p
						.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri(httpUri.getHttpbin()))
				// apply the circuit breaker pattern to the route
				.route(p -> p
						.host("*.circuitbreaker.com")
						.filters(f -> f
								.circuitBreaker(config -> config.setFallbackUri("forward:/fallback")))
						.uri(httpUri.getHttpbin()))
				.build();
	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}

}
