package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.ConferenceRoom;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.ConferenceRoomService;
import pl.marcool.intivepatronage.services.MyExceptions;
import javax.validation.Valid;

@RestController
public class ConferenceRoomController {

    @Autowired
    private ConferenceRoomService conferenceRoomService;
    @Autowired
    private CheckingService checkingService;
    private String message = "The following error was detected:\n";


    @GetMapping("/conferenceroom")
    ResponseEntity getAll() {
        return ResponseEntity.ok(conferenceRoomService.getAll());
    }

    @GetMapping("/conferenceroom/id")
    ResponseEntity getById(@RequestParam String id) {
        if (!conferenceRoomService.findById(id).getId().equals("empty")) {
            return ResponseEntity.ok().body(conferenceRoomService.findById(id));
        }
        return ResponseEntity.badRequest().body(message + "ID: '" + id + "' not found");
    }

    @PostMapping("/conferenceroom")
    ResponseEntity save(@RequestBody @Valid ConferenceRoom conferenceRoom, BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            conferenceRoomService.save(conferenceRoom);
            return ResponseEntity.ok("Added successfully:\n" + conferenceRoom);
        } catch (MyExceptions myExceptions) {
            return ResponseEntity.badRequest().body(myExceptions.getMessage());
        }
    }

    @PutMapping("/conferenceroom/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid ConferenceRoom conferenceRoom,
                          BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            conferenceRoomService.update(id, conferenceRoom);
            return ResponseEntity.ok().body("Successful update:\n" + conferenceRoom);
        } catch (MyExceptions myExceptions) {
            return ResponseEntity.badRequest().body(myExceptions.getMessage());
        }
    }

    @DeleteMapping("/conferenceroom/delete/id")
    ResponseEntity delete(@RequestParam String id) {
        if (!conferenceRoomService.findById(id).getId().equals("empty")) {
            conferenceRoomService.deleteById(id);
            return ResponseEntity.ok().body("'" + id + "' entry has been successfully deleted");
        }
        return ResponseEntity.badRequest().body(message + "ID: '" + id + "' not found");
    }

    @DeleteMapping("/conferenceroom/delete/all")
    ResponseEntity deleteAll() {
        conferenceRoomService.deleteAll();
        return ResponseEntity.ok().body("The entire Conference Room database has been successfully deleted");
    }
}