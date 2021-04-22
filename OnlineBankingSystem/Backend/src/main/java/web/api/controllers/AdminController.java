package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.api.models.Prospect;
import web.api.models.User;
import web.api.services.ProspectService;
import web.api.services.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProspectService prospectService;

    @Autowired
    public AdminController(ProspectService prospectService)
    {
        this.prospectService = prospectService;
    }

    @GetMapping("/newUsers")
    public ResponseEntity<List<Prospect>> getUsers() {
        System.out.println("In getMapping of Users");
        List<Prospect> prospects = prospectService.getProspects();
        return ResponseEntity.ok().body(prospects);
    }
}
