package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.services.ReservationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    Iterable<Reservation> getAll() {
        return reservationService.getAll();
    }

    @GetMapping("/id")
    Reservation getById(@RequestParam String id) {
        return reservationService.findById(id);
    }

    @PostMapping
    Reservation save(@RequestBody @Valid Reservation reservation) {
        return reservationService.save(reservation);
    }

    @PutMapping("/update")
    Reservation update(@RequestParam String id, @RequestBody @Valid Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @DeleteMapping("/delete/id")
    String delete(@RequestParam String id) {
        return reservationService.deleteById(id);
    }

    @DeleteMapping("/delete/all")
    String deleteAll() {
        return reservationService.deleteAll();
    }
}