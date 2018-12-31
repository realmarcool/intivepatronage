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
        if(!reservationService.findById(id).getId().equals("pusty")){
            return ResponseEntity.ok().body(reservationService.findById(id));
        }
        return ResponseEntity.badRequest().body("Nie znaleziono reserwacji o id '" + id + "'");
    }

    @PostMapping("/reservation")
    ResponseEntity save(@RequestBody @Valid Reservation reservation, BindingResult bindingResult) {

        String checkOrg = checkingService.checkBindingResult(bindingResult); //Sprawdzenie poprawności parametrów

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

        String checkBR = checkingService.checkBindingResult(bindingResult); //Sprawdzenie poprawności parametrów
        if (checkBR.equals("ok")) {
            String reply = reservationService.update(id, reservation);
            if (reply.equals("ok")) return ResponseEntity.ok().body("Pomyślny update:\n" + reservation);
            else return ResponseEntity.badRequest().body(reply);
        }else return ResponseEntity.badRequest().body(checkBR);
    }

    @DeleteMapping("/reservation/delete/id")
    ResponseEntity delete(@RequestParam String id){
        if(!reservationService.findById(id).getId().equals("pusty")){
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
}
