package com.flatfusion.backend.config;

import com.flatfusion.backend.services.CustomUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


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


//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//    }

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
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/v1/users").hasAnyRole("USER")
                        .requestMatchers("/v1/*").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authProvider())
                .httpBasic();

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



    // We need to define a PasswordEncoder bean in newer Spring Security versions
    // Set a safe password encoder in production applications
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService service) throws Exception {
//        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // default rounds = 10
        return new BCryptPasswordEncoder();
    }
}
