package com.api.parkingcontrol.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    //filters
    @Bean
    SecurityFilterChain  securityFilterChain (HttpSecurity http) throws Exception{
    	return http
    			.authorizeHttpRequests(
    					authorizeConfig->{
    						authorizeConfig.requestMatchers(HttpMethod.POST,"/cadastro").permitAll();
    						authorizeConfig.requestMatchers(HttpMethod.GET,"/cadastro").permitAll();
    						authorizeConfig.requestMatchers(HttpMethod.GET,"/cadastro/**").permitAll();
    						authorizeConfig.requestMatchers(HttpMethod.PUT,"/cadastro/**").permitAll();
    						authorizeConfig.requestMatchers(HttpMethod.DELETE,"/cadastro/**").permitAll();
    						authorizeConfig.requestMatchers(HttpMethod.GET,"/parking-spot").permitAll();
    						authorizeConfig.requestMatchers("/logout").permitAll();
    						authorizeConfig.requestMatchers(HttpMethod.POST, "/parking-spot").hasAnyRole("ADMIN", "USER")
    						.requestMatchers(HttpMethod.DELETE, "/parking-spot/**").hasRole("ADMIN");
    						authorizeConfig.anyRequest().authenticated();
    					}
    					)
                    .httpBasic(Customizer.withDefaults())
   		        //.formLogin(Customizer.withDefaults())
                    .csrf(csrf -> csrf.disable())
    			.build();
    }

   @Bean
   public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

}
