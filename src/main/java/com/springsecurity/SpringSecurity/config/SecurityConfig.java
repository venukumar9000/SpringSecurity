package com.springsecurity.SpringSecurity.config;

import com.springsecurity.SpringSecurity.service.UserInfoUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//        UserDetails admin = User.withUsername("venu")
//                .password(passwordEncoder.encode("1234"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("john")
//                .password(passwordEncoder.encode("1234"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);
        return new UserInfoUserDetailService();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(i->i.disable())

                .authorizeHttpRequests(i->i
                                .requestMatchers("/api/welcome","/api/new").permitAll()
                                .requestMatchers("/api/product","/api/products")
                                .authenticated().anyRequest().denyAll()
                        ).formLogin(Customizer.withDefaults());
                return http.build();


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
