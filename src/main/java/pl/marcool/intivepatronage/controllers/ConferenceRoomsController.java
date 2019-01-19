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
public class ConferenceRoomsController {

    @Autowired
    private ConferenceRoomService conferenceRoomService;
    @Autowired
    private CheckingService checkingService;

    @GetMapping("/conferencerooms")
    ResponseEntity getAll() {
        return ResponseEntity.ok(conferenceRoomService.getAll());
    }

    @GetMapping("/conferencerooms/id")
    ResponseEntity getById(@RequestParam String id) {
        return ResponseEntity.ok(conferenceRoomService.findById(id));
    }

    @PostMapping("/conferencerooms")
    ResponseEntity save(@RequestBody @Valid ConferenceRoom conferenceRoom, BindingResult bindingResult) {
        return ResponseEntity.ok(conferenceRoomService.save(conferenceRoom, bindingResult));
    }

    @PutMapping("/conferencerooms/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid ConferenceRoom conferenceRoom,
                          BindingResult bindingResult) {
        return ResponseEntity.ok().body(conferenceRoomService.update(id, conferenceRoom));
    }


    @DeleteMapping("/conferencerooms/delete/id")
    ResponseEntity delete(@RequestParam String id){
        return ResponseEntity.ok().body(conferenceRoomService.deleteById(id));
    }

    @DeleteMapping("/conferencerooms/delete/all")
    ResponseEntity deleteAll() {
        return ResponseEntity.ok(conferenceRoomService.deleteAll());
    }
}