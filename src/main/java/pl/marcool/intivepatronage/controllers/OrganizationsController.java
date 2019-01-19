package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.services.OrganizationService;

import javax.validation.Valid;

@RestController
public class OrganizationsController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/organizations")
    ResponseEntity getAll() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @GetMapping("/organizations/id")
    ResponseEntity getById(@RequestParam String id) {
        return ResponseEntity.ok(organizationService.findById(id));
    }

    @PostMapping("/organizations")
    ResponseEntity save(@RequestBody @Valid Organization organization, BindingResult bindingResult) {
        return ResponseEntity.ok(organizationService.save(organization, bindingResult));
    }

    @PutMapping("/organizations/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid Organization organization,
                          BindingResult bindingResult) {
        return ResponseEntity.ok(organizationService.update(id, organization, bindingResult));
    }

    @DeleteMapping("/organizations/delete/id")
    ResponseEntity delete(@RequestParam String id) {
        return ResponseEntity.ok(organizationService.deleteById(id));
    }

    @DeleteMapping("/organizations/delete/all")
    ResponseEntity deleteAll() {
        return ResponseEntity.ok(organizationService.deleteAll());
    }
}