package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.ReservationService;

import javax.validation.Valid;

@RestController
public class ReservationController {

    @Autowired
    private CheckingService checkingService;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation")
    ResponseEntity getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/reservation/id")
    ResponseEntity getById(@RequestParam String id){
        if(!findById(id).getName().equals("pusty")){
            return ResponseEntity.ok().body(reservationService.findById(id));
        }
        return ResponseEntity.badRequest().body("Nie znaleziono organizacji o nazwie '" + id + "'");
    }

    @PostMapping("/reservation")
    ResponseEntity save(@RequestBody @Valid Reservation reservation, BindingResult bindingResult) {

        String checkOrg = checkingService.checkBindingResult(bindingResult);

        if(checkOrg.equals("ok")){
            String addReservation = reservationService.save(reservation);
            if (addReservation.equals("ok")) return ResponseEntity.ok("Pomyślnie dodano:\n" + reservation);
            else return ResponseEntity.badRequest().body(addReservation);
        }
        else return ResponseEntity.badRequest().body(checkOrg);
    }

    @PutMapping("/reservation/update")
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

    @DeleteMapping("/reservation/delete/name")
    ResponseEntity delete(@RequestParam String id){
        if(!findById(id).getName().equals("pusty")){
            organizationService.deleteById(id);
            return ResponseEntity.ok().body("Pomyślnie skasowano organizację o nazwie '" + id + "'");
        }
        return ResponseEntity.badRequest().body("Nie znaleziono organizacji o nazwie '" + id + "'");
    }

    @DeleteMapping("/reservation/delete/all")
    ResponseEntity deleteAll(){
        organizationService.deleteAll();
        return ResponseEntity.ok().body("Skasowano całą bazę Organizacje");
    }

    Organization findById(String id){
        return organizationService.findById(id);
    }
}



}
