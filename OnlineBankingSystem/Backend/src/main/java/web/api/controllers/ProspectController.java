package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import web.api.models.Prospect;
import web.api.services.ProspectService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class ProspectController {
    private final ProspectService prospectService;

    @Autowired
    public ProspectController(ProspectService prospectService)
    {
        this.prospectService = prospectService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Prospect> getUsers(@RequestBody Prospect newProspect) {
        Prospect object = prospectService.addProspect(newProspect);
            return new ResponseEntity<>(object, HttpStatus.CREATED);
    }
}
