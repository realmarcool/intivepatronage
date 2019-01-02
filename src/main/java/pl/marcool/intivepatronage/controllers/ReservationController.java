package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.MyExceptions;
import pl.marcool.intivepatronage.services.ReservationService;
import javax.validation.Valid;

@RestController
public class ReservationController {

    @Autowired
    private CheckingService checkingService;
    @Autowired
    private ReservationService reservationService;
    private String message = "The following error was detected:\n";

    @GetMapping("/reservation")
    ResponseEntity getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/reservation/id")
    ResponseEntity getById(@RequestParam String id) {
        if (!reservationService.findById(id).getId().equals("empty")) {
            return ResponseEntity.ok().body(reservationService.findById(id));
        }
        return ResponseEntity.badRequest().body(message + "ID: '" + id + "' not found");
    }

    @PostMapping("/reservation")
    ResponseEntity save(@RequestBody @Valid Reservation reservation, BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            reservationService.save(reservation);
            return ResponseEntity.ok("Added successfully:\n" + reservation);
        } catch (MyExceptions myExceptions) {
            return ResponseEntity.badRequest().body(myExceptions.getMessage());
        }
    }

    @PutMapping("/reservation/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid Reservation reservation,
                          BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            reservationService.update(id, reservation);
            return ResponseEntity.ok().body("Successful update:\n" + reservation);
        } catch (MyExceptions myExceptions) {
            return ResponseEntity.badRequest().body(myExceptions.getMessage());
        }
    }

    @DeleteMapping("/reservation/delete/id")
    ResponseEntity delete(@RequestParam String id) {
        if (!reservationService.findById(id).getId().equals("empty")) {
            reservationService.deleteById(id);
            return ResponseEntity.ok().body("'" + id + "' entry has been successfully deleted");
        }
        return ResponseEntity.badRequest().body(message + "ID: '" + id + "' not found");
    }

    @DeleteMapping("/reservation/delete/all")
    ResponseEntity deleteAll() {
        reservationService.deleteAll();
        return ResponseEntity.ok().body("The entire Reservation database has been successfully deleted");
    }
}