package com.coworkia.backend.controllers;

import com.coworkia.backend.entities.InternalUser;
import com.coworkia.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/private")
public class PrivateController {
    private final UserService userService;

    public PrivateController (UserService userService){
        this.userService = userService;
    }
    @PostMapping("/login")
    public String login (@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }

    @GetMapping("/getUsers")
    public List<InternalUser> getUser(){
        return userService.getUser();
    }

    @PostMapping("/addUser")
    public InternalUser CreateUser (@RequestBody InternalUser internalUser){
        return userService.CreateUser(internalUser);
    }

    @DeleteMapping("/{id}")
    public void deledtUser (@PathVariable Long id){
        userService.deledtUser(id);
    }
}
