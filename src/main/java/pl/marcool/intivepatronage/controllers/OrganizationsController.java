package pl.marcool.intivepatronage.controllers;

import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.dto.OrganizationDTO;
import pl.marcool.intivepatronage.services.OrganizationsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/organizations")
class OrganizationsController {

    private final OrganizationsService organizationsService;

    private OrganizationsController(OrganizationsService organizationsService){
        this.organizationsService = organizationsService;
    }

    @GetMapping
    Iterable<OrganizationDTO> getAll() {
        return organizationsService.getAll();
    }

    @GetMapping("{id}")
    OrganizationDTO getById(@PathVariable String id) {
        return organizationsService.findById(id);
    }

    @PostMapping
    OrganizationDTO save(@RequestBody @Valid OrganizationDTO organization) {
        return organizationsService.save(organization);
    }

    @PutMapping("{id}")
    OrganizationDTO update(@PathVariable String id, @RequestBody @Valid OrganizationDTO organizationDTO) {
        return organizationsService.update(id, organizationDTO);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable String id) {
        organizationsService.deleteById(id);
    }

    @DeleteMapping
    void deleteAll() {
        organizationsService.deleteAll();
    }
}