package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.dto.ReservationDTO;
import pl.marcool.intivepatronage.services.ReservationsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsService reservationsService;

    @GetMapping
    List<ReservationDTO> getAll() {
        return reservationsService.getAll();
    }

    @GetMapping("{id}")
    ReservationDTO getById(@PathVariable String id) {
        return reservationsService.findById(id);
    }

    @PostMapping
    ReservationDTO save(@RequestBody @Valid ReservationDTO reservationDTO) {
        return reservationsService.save(reservationDTO);
    }

    @PutMapping("{id}")
    ReservationDTO update(@PathVariable String id, @RequestBody @Valid ReservationDTO reservationDTO) {
        return reservationsService.update(id, reservationDTO);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable String id) {
        reservationsService.deleteById(id);
    }

    @DeleteMapping
    void deleteAll() {
        reservationsService.deleteAll();
    }
}