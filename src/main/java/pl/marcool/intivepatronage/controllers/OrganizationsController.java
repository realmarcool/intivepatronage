package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.services.OrganizationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/organizations")
public class OrganizationsController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    Iterable<Organization> getAll() {
        return organizationService.getAll();
    }

    @GetMapping("/id")
    Organization getById(@RequestParam String id) {
        return organizationService.findById(id);
    }

    @PostMapping
    Organization save(@RequestBody @Valid Organization organization) {
        return organizationService.save(organization);
    }

    @PutMapping("/update")
    Organization update(@RequestParam String id, @RequestBody @Valid Organization organization) {
        return organizationService.update(id, organization);
    }

    @DeleteMapping("/organizations/delete/id")
    String delete(@RequestParam String id) {
        return organizationService.deleteById(id);
    }

    @DeleteMapping("/organizations/delete/all")
    String deleteAll() {
        return organizationService.deleteAll();
    }
}