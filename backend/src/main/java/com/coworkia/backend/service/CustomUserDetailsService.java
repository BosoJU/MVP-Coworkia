package com.coworkia.backend.service;

import com.coworkia.backend.entities.InternalRole;
import com.coworkia.backend.entities.InternalUser;
import com.coworkia.backend.repository.InternalUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final InternalUserRepository internalUserRepository;

    public CustomUserDetailsService(InternalUserRepository internalUserRepository) {
        this.internalUserRepository = internalUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        InternalUser internalUser = internalUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + email));

        return new User(internalUser.getEmail(), internalUser.getPassword(), buildGrandedAuthorities(internalUser.getRole()));
    }

    private List<GrantedAuthority> buildGrandedAuthorities(InternalRole role){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }
}