package com.flatfusion.backend.config;

import com.flatfusion.backend.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Custom implementation of the Spring Security UserDetails interface for user authentication.
 * This class represents the principal (currently authenticated user) and provides
 * information required by the security framework.
 */
public class CustomUserPrincipal implements UserDetails {
    private UserEntity user;

    private final Logger log = LoggerFactory.getLogger(CustomUserPrincipal.class);

    /**
     * Constructs a CustomUserPrincipal with the specified UserEntity.
     *
     * @param user The UserEntity representing the currently authenticated user.
     */
    public CustomUserPrincipal(UserEntity user) {
        this.user = user;
    }

    /***
     * This method is for getting the authorities of a user.
     * A user can only access endpoints if they have the needed authorities.
     * @return The user's authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("USER"));
        authorities.add(new SimpleGrantedAuthority("READ_PRIVILEGE"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
