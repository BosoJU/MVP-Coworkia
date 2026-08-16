package com.coworkia.backend.controllers;

import com.coworkia.backend.configurations.JwtService;
import com.coworkia.backend.entities.InternalUser;
import com.coworkia.backend.repository.InternalUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/private")
public class PrivateController {
    private final InternalUserRepository internalUserRepository;
    private JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public PrivateController(InternalUserRepository internalUserRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.internalUserRepository = internalUserRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public String login (@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(),
                        loginRequest.password())
        );
        return jwtService.generateToken(authentication);
    }
}
