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

/**
 * CustomUserDetailService is a custom implementation of the {@link UserDetailsService} interface
 * provided by Spring Security. It is responsible for loading user details from the database based on
 * the provided username (email) and creating a {@link UserDetails} object for authentication.
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    private UserEntityRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);

    /**
     * Default constructor for CustomUserDetailService.
     * Logs the initialization of the bean.
     */
    public CustomUserDetailService() {
        log.info("CustomUserDetailService bean is initialized.");
    }

    /**
     * Loads user details by the provided username (email) from the database.
     * Creates a {@link UserDetails} object for authentication.
     *
     * @param username The username (email) of the user.
     * @return UserDetails object containing user details.
     * @throws UsernameNotFoundException If the user with the given username is not found.
     */
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
