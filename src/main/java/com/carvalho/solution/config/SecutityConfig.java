package com.carvalho.solution.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SuppressWarnings("rawtypes")
@Configuration
@EnableMethodSecurity
public class SecutityConfig extends SecurityConfigurerAdapter  {
    
}
