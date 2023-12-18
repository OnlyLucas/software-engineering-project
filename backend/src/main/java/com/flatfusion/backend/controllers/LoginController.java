package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;
import java.util.Map;



@RestController
@RequestMapping("/v1/login")
public class LoginController {

    @Autowired
    UserEntityRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * This is the endpoint
     * @param headers Empty request with an 'Authorization' header contain username and password credentials, encoded in Base64.
     * @return If credentials valid: ResponseEntity with User matching credentials. If invalid: null.
     */
    @GetMapping
    public ResponseEntity<UserEntity> getByEmail(@RequestHeader("Authorization") String authHeader){

        String[] credentials;
        String username;
        String password;

        if (authHeader != null) {
            try{
                // Remove non-credential chars
                String auth = authHeader.replaceFirst("[B|b]asic ", "");
                logger.debug("AuthHeader string: " + auth);

                // Header format: 'Basic username:password'
                credentials = new String(Base64.getDecoder().decode(auth)).split(":", 2);
            } catch (IllegalArgumentException e){
                logger.debug("Header auth credentials not in Base64 encoding.", e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            try{
                username = credentials[0];
                password = credentials[1];
                logger.debug("Username: {} , Password: {}", username, password);
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e){
                logger.debug("--Authentication failed--", e);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            UserEntity user = userRepository.findByEmail(username);//fetch user from database using email

            logger.debug("Raw PW: {} , DB PW encrypted: {}", user.getPassword(), password);

            if (user != null) {
                if (passwordEncoder.matches(password, user.getPassword())) {
                    logger.debug(username + " -------- authentication successful");
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }
            }
        }

        logger.debug("---------Authentication failed");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
