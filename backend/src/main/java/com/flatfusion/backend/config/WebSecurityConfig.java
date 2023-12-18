package com.flatfusion.backend.config;

import com.flatfusion.backend.repositories.UserEntityRepository;
import com.flatfusion.backend.services.CustomUserDetailService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;


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

    @Autowired
    private RsaKeyConfigProperties rsaKeyConfigProperties;


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
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();

        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
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
