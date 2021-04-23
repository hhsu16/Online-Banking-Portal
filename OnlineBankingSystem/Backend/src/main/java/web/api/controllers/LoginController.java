package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.api.models.User;
import web.api.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/user")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/login")
    public User getUserRole(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password){
        return userService.getUserLogin(email, password);
    }
}
