package com.tryout.springgateway;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@lombok.Data
class UriConfiguration {

    private String httpbin = "http://httpbin.org:80";
    private String spring = "https://spring.io:443";
    private String myblog = "https://www.justenougharchitecture.com:443";
}