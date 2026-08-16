package com.coworkia.backend.service;

import com.coworkia.backend.configurations.JwtService;
import com.coworkia.backend.controllers.LoginRequest;
import com.coworkia.backend.entities.InternalUser;
import com.coworkia.backend.repository.InternalUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final InternalUserRepository internalUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(InternalUserRepository internalUserRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.internalUserRepository = internalUserRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public String login (LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(),
                        loginRequest.password())
        );
        return jwtService.generateToken(authentication);
    }

    public List<InternalUser> getUser(){
        return internalUserRepository.findAll();
    }

    public InternalUser CreateUser (InternalUser internalUser){
        Optional<InternalUser> userExistant = internalUserRepository.findByEmail(internalUser.getEmail());

        if(userExistant.isPresent()){
            throw new IllegalArgumentException("Un utilisateur existe déjà avec cet email" + internalUser.getEmail());
        }

        internalUser.setPassword(passwordEncoder.encode(internalUser.getPassword()));
        return internalUserRepository.save(internalUser);
    }

    public void deledtUser (Long id){
        internalUserRepository.deleteById(id);
    }
}
