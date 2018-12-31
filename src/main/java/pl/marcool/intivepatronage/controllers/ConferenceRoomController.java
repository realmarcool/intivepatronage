package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.ConferenceRoom;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.ConferenceRoomService;

import javax.validation.Valid;

@RestController
public class ConferenceRoomController {

    @Autowired
    private ConferenceRoomService conferenceRoomService;
    @Autowired
    private CheckingService checkingService;

    @GetMapping("/conferenceroom")
    ResponseEntity getAll() {
        return ResponseEntity.ok(conferenceRoomService.getAll());
    }

    @GetMapping("/conferenceroom/id")
    ResponseEntity getById(@RequestParam String id) {
        if (!conferenceRoomService.findById(id).getId().equals("pusty")) {
            return ResponseEntity.ok().body(conferenceRoomService.findById(id));
        }
        return ResponseEntity.badRequest().body("Nie znaleziono rekordu o ID '" + id + "'");
    }

    @PostMapping("/conferenceroom")
    ResponseEntity save(@RequestBody @Valid ConferenceRoom conferenceRoom, BindingResult bindingResult) {

        String checkBR = checkingService.checkBindingResult(bindingResult); //Sprawdzenie poprawności parametrów
        if (checkBR.equals("ok")) {
            String addConferenceRoom = conferenceRoomService.save(conferenceRoom);
            if (addConferenceRoom.equals("ok")) return ResponseEntity.ok("Pomyślnie dodano:\n" + conferenceRoom);
            else return ResponseEntity.badRequest().body(addConferenceRoom);
        } else return ResponseEntity.badRequest().body(checkBR);
    }

    @PutMapping("/conferenceroom/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid ConferenceRoom conferenceRoom,
                          BindingResult bindingResult) {

        String checkBR = checkingService.checkBindingResult(bindingResult); //Sprawdzenie poprawności parametrów
        if (checkBR.equals("ok")) {
            String reply = conferenceRoomService.update(id, conferenceRoom);
            if (reply.equals("ok")) return ResponseEntity.ok().body("Pomyślny update:\n" + conferenceRoom);
            else return ResponseEntity.badRequest().body(reply);
        } else return ResponseEntity.badRequest().body(checkBR);
    }

    @DeleteMapping("/conferenceroom/delete/id")
    ResponseEntity delete(@RequestParam String id) {
        if (!conferenceRoomService.findById(id).getId().equals("pusty")) {
            conferenceRoomService.deleteById(id);
            return ResponseEntity.ok().body("Pomyślnie skasowano rekord o ID '" + id + "'");
        }
        return ResponseEntity.badRequest().body("Nie znaleziono rekordu o ID '" + id + "'");
    }

    @DeleteMapping("/conferenceroom/delete/all")
    ResponseEntity deleteAll() {
        conferenceRoomService.deleteAll();
        return ResponseEntity.ok().body("Skasowano całą bazę Conference Room");
    }
}