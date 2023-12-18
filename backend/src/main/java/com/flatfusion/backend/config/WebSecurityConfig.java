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

//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        // authenticationManagerBuilder.userDetailsService(userDetailsService);
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

//        http.
//                authenticationManager(authenticationManager);


        http
                .authorizeHttpRequests((authorize) -> authorize
                        // Specifies urls accessible by authenticated user
                        .requestMatchers("/v1/auth/login").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("v1/groups/*").authenticated()
                        .requestMatchers("/v1/users").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/v1/**").hasAnyAuthority("ADMIN", "USER", "READ", "READ_PRIVILEGE")
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
//                .exceptionHandling((exceptions) -> exceptions
//                        // If exception occurs, reroute to login
//                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/v1/login"))
//                )
                //.oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> jwt.decoder(jwtDecoder())));
                //.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));

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
