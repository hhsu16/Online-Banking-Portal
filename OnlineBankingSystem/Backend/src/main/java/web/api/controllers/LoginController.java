package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.api.models.Role;
import web.api.services.UserService;

import java.net.URI;

@RestController
@RequestMapping("api/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public Role getUserRole(String email, String password){
        return userService.getUserRole(email, password);
    }
}
