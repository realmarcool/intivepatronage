package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.MyExceptions;
import pl.marcool.intivepatronage.services.OrganizationService;
import javax.validation.Valid;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private CheckingService checkingService;
    private String message = "The following error was detected:\n";


    @GetMapping("/organization")
    ResponseEntity getAll() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @GetMapping("/organization/name")
    ResponseEntity getById(@RequestParam String id) {
        if (!organizationService.findById(id).getName().equals("empty")) {
            return ResponseEntity.ok().body(organizationService.findById(id));
        }
        return ResponseEntity.badRequest().body(message + "ID: '" + id + "' not found");
    }

    @PostMapping("/organization")
    ResponseEntity save(@RequestBody @Valid Organization organization, BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            organizationService.save(organization);
            return ResponseEntity.ok("Added successfully:\n" + organization);
        } catch (MyExceptions myExceptions) {
            return ResponseEntity.badRequest().body(myExceptions.getMessage());
        }
    }

    @PutMapping("/organization/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid Organization organization,
                          BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            organizationService.update(id, organization);
            return ResponseEntity.ok("Successful update:\n" + organization);
        } catch (MyExceptions myExceptions) {
            return ResponseEntity.badRequest().body(myExceptions.getMessage());
        }
    }

    @DeleteMapping("/organization/delete/name")
    ResponseEntity delete(@RequestParam String id) {
        if (!organizationService.findById(id).getName().equals("empty")) {
            organizationService.deleteById(id);
            return ResponseEntity.ok().body("'" + id + "' entry has been successfully deleted");
        }
        return ResponseEntity.badRequest().body(message + "ID: '" + id + "' not found");
    }

    @DeleteMapping("/organization/delete/all")
    ResponseEntity deleteAll() {
        organizationService.deleteAll();
        return ResponseEntity.ok().body("The entire Organization database has been successfully deleted");
    }
}