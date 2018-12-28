package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.OrganizationService;

import javax.validation.Valid;
import java.util.List;

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
        if(!findById(id).getName().equals("pusty")){
            return ResponseEntity.ok().body(organizationService.findById(id));
        }
        return ResponseEntity.badRequest().body("Nie znaleziono organizacji o nazwie '" + id + "'");
    }

    @PostMapping("/organization")
    ResponseEntity save(@RequestBody @Valid Organization organization, BindingResult bindingResult) {

        String checkOrg = checkingService.checkBindingResult(bindingResult);

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
        String checkBR = checkingService.checkBindingResult(bindingResult);
        String checkOldId = findById(id).getName();
        String checkNewId = findById(organization.getName()).getName();
        System.out.println("Stare id, wynik: " + checkOldId );
        System.out.println("Nowe id, wynik: " + checkNewId);

        if (!findById(id).getName().equals("pusty")) { //Sprawdzenie czy podane ID istnieje
            if(findById(organization.getName()).getName().equals("pusty")) { //Sprawdzenie czy nowe id z updatowenego confrenceRoom już istnieje w bazie
                if (checkBR.equals("ok")) {  //Sprawdzenie BindingResults
                    //Jeżeli 1.poprawne parametry, 2.stare id znalezione, 3.nowe id nie znalezione
                    organizationService.update(id, organization);
                    return ResponseEntity.ok().body("Pomyślny update:\n" + organization);
                }
                // Jeżeli błędne parametry
                else return ResponseEntity.badRequest().body(checkBR);
            }
            // Jeżeli nowe id już istnieje w bazie
            else return ResponseEntity.badRequest().body("Nowe id '" + organization.getName() + "' już istnieje ww bazie");
        }
        // Jeżeli podane id nie istnieje w bazie
        return ResponseEntity.badRequest().body("Nie znaleziono id '" + id + "' w bazie");
    }

    @DeleteMapping("/organization/delete/name")
    ResponseEntity delete(@RequestParam String id){
        if(!findById(id).getName().equals("pusty")){
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

    Organization findById(String id){
        return organizationService.findById(id);
    }
}

