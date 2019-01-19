package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.services.ReservationService;

import javax.validation.Valid;

@RestController

public class ReservationsController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    ResponseEntity getAll() {
        return ResponseEntity.ok().body(reservationService.getAll());
    }

    @GetMapping("/reservations/id")
    ResponseEntity getById(@RequestParam String id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PostMapping("/reservations")
    ResponseEntity save(@RequestBody @Valid Reservation reservation, BindingResult bindingResult) {
        return ResponseEntity.ok(reservationService.save(reservation, bindingResult));
    }

    @PutMapping("/reservations/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid Reservation reservation,
                          BindingResult bindingResult) {
        return ResponseEntity.ok(reservationService.update(id, reservation, bindingResult));
    }

    @DeleteMapping("/reservations/delete/id")
    ResponseEntity delete(@RequestParam String id) {
        return ResponseEntity.ok(reservationService.deleteById(id));
    }

    @DeleteMapping("/reservations/delete/all")
    ResponseEntity deleteAll() {
        return ResponseEntity.ok(reservationService.deleteAll());
    }
}