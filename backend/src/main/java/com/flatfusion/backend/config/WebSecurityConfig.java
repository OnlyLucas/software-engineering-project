package com.flatfusion.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.SecurityFilterChain;


/***
 * This configuration class is used for authenticating http requests.
 * For each request, the call needs to authenticate with the email and password of the app user.
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Always redirect to https
                .requiresChannel(channel ->
                        channel.anyRequest().requiresSecure())
                .authorizeHttpRequests((authorize) -> authorize
                        // Specifies access rules for the endpoints

                        // Login url requires manual auth
                        .requestMatchers("/v1/login").permitAll()
                        // User registration allow all, as users do not have an account with credentials yet
                        .requestMatchers("/v1/users/create-with-password").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/**").authenticated()
                )
                .authenticationProvider(authProvider())
                .httpBasic(Customizer.withDefaults());

        http
                .csrf(csrf -> {
                    csrf.disable();
                })
                .cors(cors -> {
                    cors.disable();
                })
                ;

        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        logger.debug("SecurityFilterChain created.");

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }


//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .authenticationProvider(authProvider())
//                .build();
//    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /***
     * This method returns a {@link org.springframework.security.crypto.password.DelegatingPasswordEncoder} so that multiple encryption variants are support.
     * This has the advantage of enabling easier and rolling changes to the encryption strategy.
     * It is more future-proof than using only a single encryptor.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Type check not needed as type is always DelegationPasswordEncoder
        DelegatingPasswordEncoder encoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // If no encoding is specified in db value, pw has no encoding
        encoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());

        return encoder;
    }
}
