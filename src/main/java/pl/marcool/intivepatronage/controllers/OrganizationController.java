package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.OrganizationService;

import javax.validation.Valid;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private CheckingService checkingService;

    @GetMapping("/organization")
    ResponseEntity getAll() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @GetMapping("/organization/name")
    ResponseEntity getById(@RequestParam String id){
        if(!organizationService.findById(id).getName().equals("pusty")){
            return ResponseEntity.ok().body(organizationService.findById(id));
        }
        return ResponseEntity.badRequest().body("Nie znaleziono organizacji o nazwie '" + id + "'");
    }

    @PostMapping("/organization")
    ResponseEntity save(@RequestBody @Valid Organization organization, BindingResult bindingResult) {

        String checkOrg = checkingService.checkBindingResult(bindingResult); //Sprawdzenie poprawności parametrów

        if(checkOrg.equals("ok")){
            String addOrganization = organizationService.save(organization);
            if (addOrganization.equals("ok")) return ResponseEntity.ok("Pomyślnie dodano:\n" + organization);
            else return ResponseEntity.badRequest().body(addOrganization);
        }
        else return ResponseEntity.badRequest().body(checkOrg);
    }

    @PutMapping("/organization/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid Organization organization,
                          BindingResult bindingResult) {

        String checkBR = checkingService.checkBindingResult(bindingResult); //Sprawdzenie poprawności parametrów
        if (checkBR.equals("ok")){
            String reply = organizationService.update(id, organization);
            if(reply.equals("ok")) return ResponseEntity.ok("Pomyślny update:\n" + organization);
            else return ResponseEntity.badRequest().body(reply);
        }else return ResponseEntity.badRequest().body(checkBR);
    }

    @DeleteMapping("/organization/delete/name")
    ResponseEntity delete(@RequestParam String id){
        if(!organizationService.findById(id).getName().equals("pusty")){
            organizationService.deleteById(id);
            return ResponseEntity.ok().body("Pomyślnie skasowano organizację o nazwie '" + id + "'");
        }
        return ResponseEntity.badRequest().body("Nie znaleziono organizacji o nazwie '" + id + "'");
    }

    @DeleteMapping("/organization/delete/all")
    ResponseEntity deleteAll(){
        organizationService.deleteAll();
        return ResponseEntity.ok().body("Skasowano całą bazę Organizacje");
    }
}