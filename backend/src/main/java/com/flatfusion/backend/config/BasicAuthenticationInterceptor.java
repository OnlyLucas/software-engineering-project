//package com.flatfusion.backend.config;
//
//import com.flatfusion.backend.entities.UserEntity;
//import com.flatfusion.backend.repositories.UserEntityRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.crypto.codec.Utf8;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.servlet.HandlerInterceptor;
//import java.util.Base64;
//
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class BasicAuthenticationInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    UserEntityRepository userRepository;
//
//    Logger logger = LoggerFactory.getLogger(BasicAuthenticationInterceptor.class);
//
//    @Override
//    public boolean preHandle(@RequestBody HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        logger.debug("------ BasicAuthenticationIntercepter called.");
//
//        String authHeader = request.getHeader("Authorization");
//
//        String[] credentials;
//        String username;
//        String password;
//
//        if (authHeader != null) {
//            try{
//                // Header format: 'Basic username:password'
//                credentials = new String(Base64.getDecoder().decode(authHeader)).split(":", 2);
//            } catch (IllegalArgumentException e){
//                logger.debug("Header auth credentials not in Base64 encoding. ", e);
//
//                credentials = new String(Utf8.decode(authHeader.getBytes())).split(":", 2);
//            }
//
//            try{
//                username = credentials[0];
//                password = credentials[1];
//            } catch (ArrayIndexOutOfBoundsException | NullPointerException e){
//                logger.debug("---------Authentication failed");
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//
//                return false;
//            }
//
//
//
//            UserEntity user = userRepository.findByEmail(username);//fetch user from database using email
//
//            if (user != null) {
//                if (passwordEncoder.matches(password, user.getPassword())) {
//                    logger.debug(username + " -------- authentication successful");
//                    return true;
//                }
//            }
//        }
//
//        logger.debug("---------Authentication failed");
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//        return false;
//    }
//}