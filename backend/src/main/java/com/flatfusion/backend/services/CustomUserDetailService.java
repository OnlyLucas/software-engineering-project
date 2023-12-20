package com.flatfusion.backend.services;

import com.flatfusion.backend.config.CustomUserPrincipal;
import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    private UserEntityRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);

    public CustomUserDetailService() {
        log.info("CustomUserDetailService bean is initialized.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity user = userRepository.findByEmail(username);

        if (user == null) {
            logger.debug("User details failed to load.");
            throw new UsernameNotFoundException(username);
        }

        UserDetails details = new CustomUserPrincipal(user);
        logger.debug("User details loaded for user " + details.getUsername());
        return details;
    }
}
