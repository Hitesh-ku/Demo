package com.My_securityexample.demo.config;

import ch.qos.logback.classic.joran.action.ConfigurationAction;
import com.My_securityexample.demo.service.CustomerUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityCon {
    String[] publicEndpoints={

            "/api/v1/user/register",
            "/api/v1/user/login"

    };

    @Bean
    public SecurityFilterChain securityConf(HttpSecurity http) throws Exception{
        http
                .csrf(
                        csrf->csrf.disable()
                )

                .authorizeHttpRequests(req->{
            req.requestMatchers(publicEndpoints).permitAll();
            req.requestMatchers("/api/v1/user/welcome").hasRole("ADMIN");

            req.anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public PasswordEncoder getCoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return    config.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authProvider(
            CustomerUserDetails customerUserDetails
    ){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customerUserDetails);
        provider.setPasswordEncoder(getCoder());
        return provider;

    }


}
