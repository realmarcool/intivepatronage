package pl.marcool.intivepatronage.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.JsonMessage;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.MyExceptions;
import pl.marcool.intivepatronage.services.ReservationService;

import javax.validation.Valid;

@RestController

public class ReservationsController {

    @Autowired
    private CheckingService checkingService;
    @Autowired
    private ReservationService reservationService;

    Gson gson = new Gson();
    JsonMessage jsonMessage = new JsonMessage();

    @GetMapping("/reservations")
    ResponseEntity getAll() {
        jsonMessage.setOperation("getAll");
        jsonMessage.setStatus("success");
        jsonMessage.setMessage(null);
        return ResponseEntity.ok().body(gson.toJson(jsonMessage) + reservationService.getAll());
    }

    @GetMapping("/reservations/id")
    ResponseEntity getById(@RequestParam String id) {
        try {
            Reservation reservation = reservationService.findById(id);
            jsonMessage.setOperation("getById");
            jsonMessage.setStatus("success");
            jsonMessage.setMessage(null);
            return ResponseEntity.ok().body(gson.toJson(jsonMessage) + reservation);
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("getById");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @PostMapping("/reservations")
    ResponseEntity save(@RequestBody @Valid Reservation reservation, BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            reservationService.save(reservation);
            jsonMessage.setOperation("save");
            jsonMessage.setStatus("success");
            jsonMessage.setMessage(null);
            return ResponseEntity.ok().body(gson.toJson(jsonMessage) + reservation);
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("save");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.status(409).body(gson.toJson(jsonMessage));
        }
    }

    @PutMapping("/reservations/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid Reservation reservation,
                          BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            reservationService.update(id, reservation);
            jsonMessage.setOperation("update");
            jsonMessage.setStatus("success");
            jsonMessage.setMessage(null);
            return ResponseEntity.ok().body(gson.toJson(jsonMessage) + reservation);
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("update");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.status(409).body(gson.toJson(jsonMessage));
        }
    }

    @DeleteMapping("/reservations/delete/id")
    ResponseEntity delete(@RequestParam String id) {
        try {
            reservationService.deleteById(id);
            jsonMessage.setOperation("deleteById");
            jsonMessage.setStatus("success");
            jsonMessage.setMessage("Reservation ID:" + id + " - has been successfully deleted.");
            return ResponseEntity.ok().body(gson.toJson(jsonMessage));
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("deleteById");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @DeleteMapping("/reservations/delete/all")
    ResponseEntity deleteAll() {
        reservationService.deleteAll();
        jsonMessage.setOperation("deleteAll");
        jsonMessage.setStatus("success");
        jsonMessage.setMessage("The entire Reservations database has been successfully deleted.");
        return ResponseEntity.ok().body(gson.toJson(jsonMessage));
    }
}