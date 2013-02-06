package com.awrank.web.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("localhost")
@PropertySource("classpath:com/awrank/web/backend/config/localhost.properties")
public class LocalhostConfiguration {

}
