package com.bonbonn.springboot_starter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .headers(AbstractHttpConfigurer::disable)
  //  http.csrf().disable();
 //   http.headers().frameOptions().disable();
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/tuya/**").authenticated()
            .requestMatchers(HttpMethod.GET, "/tuya/**").authenticated()
            .anyRequest().permitAll()
        )
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  @Profile("local")
  public InMemoryUserDetailsManager inMemoryUserDetailsService() {
    UserDetails user = User.withUsername("user")
        .password("{noop}password")
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  @Profile("prod")
  public UserDetailsService yamlUserDetailsService(SecurityProperties securityProperties,
      PasswordEncoder encoder) {
    UserDetails user = User.withUsername(securityProperties.getUser().getName())
        .password(encoder.encode(securityProperties.getUser().getPassword()))
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}
