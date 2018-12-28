package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        if(!findById(id).getId().equals("pusty")){
            return ResponseEntity.ok().body(reservationService.findById(id));
        }
        return ResponseEntity.badRequest().body("Nie znaleziono reserwacji o id '" + id + "'");
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
                          @RequestBody @Valid Reservation reservation,
                          BindingResult bindingResult) {
        String checkBR = checkingService.checkBindingResult(bindingResult);
        if (!findById(id).getId().equals("pusty")) { //Sprawdzenie czy podane ID istnieje
            if(findById(reservation.getId()).getId().equals("pusty")) { //Sprawdzenie czy nowe id z updatowenego confrenceRoom już istnieje w bazie
                if (checkBR.equals("ok")) {  //Sprawdzenie BindingResults
                    //Jeżeli 1.poprawne parametry, 2.stare id znalezione, 3.nowe id nie znalezione
                    reservationService.update(id, reservation);
                    return ResponseEntity.ok().body("Pomyślny update:\n" + reservation);
                }
                // Jeżeli błędne parametry
                else return ResponseEntity.badRequest().body(checkBR);
            }
            // Jeżeli nowe id już istnieje w bazie
            else return ResponseEntity.badRequest().body("Nowe id '" + reservation.getId() + "' już istnieje ww bazie");
        }
        // Jeżeli podane id nie istnieje w bazie
        return ResponseEntity.badRequest().body("Nie znaleziono id '" + id + "' w bazie");
    }

    @DeleteMapping("/reservation/delete/id")
    ResponseEntity delete(@RequestParam String id){
        if(!findById(id).getId().equals("pusty")){
            reservationService.deleteById(id);
            return ResponseEntity.ok().body("Pomyślnie skasowano rezerwację o id '" + id + "'");
        }
        return ResponseEntity.badRequest().body("Nie znaleziono rezerwacji o id '" + id + "'");
    }

    @DeleteMapping("/reservation/delete/all")
    ResponseEntity deleteAll(){
        reservationService.deleteAll();
        return ResponseEntity.ok().body("Skasowano całą bazę rezerwacji");
    }

    Reservation findById(String id){
        return reservationService.findById(id);
    }
}
