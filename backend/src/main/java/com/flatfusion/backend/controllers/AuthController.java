//package com.flatfusion.backend.controllers;
//
//import com.flatfusion.backend.config.AuthService;
//import com.flatfusion.backend.config.CustomUserPrincipal;
//import com.flatfusion.backend.requests.LoginRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/v1/auth")
//@Validated
//public class AuthController {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//
//    @Autowired
//    private AuthService authService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest userLogin) throws IllegalAccessException {
//        Authentication authentication =
//                authenticationManager
//                        .authenticate(new UsernamePasswordAuthenticationToken(
//                                userLogin.getUsername(),
//                                userLogin.getPassword()));
//        logger.debug("Login call, Username: {} , Password: {}", userLogin.getUsername(), userLogin.getPassword());
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        CustomUserPrincipal userDetails = (CustomUserPrincipal) authentication.getPrincipal();
//
//        logger.info("Token requested for user: " + authentication.getAuthorities());
//        String token = authService.generateToken(authentication);
//
//        return ResponseEntity.ok(token);
//    }
//}
