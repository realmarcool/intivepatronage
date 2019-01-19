package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.Room;
import pl.marcool.intivepatronage.services.RoomService;

import javax.validation.Valid;

@RestController
@RequestMapping("/rooms")
public class ConferenceRoomsController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    Iterable<Room> getAll() {
        return roomService.getAll();
    }

    @GetMapping("/id")
    Room getById(@RequestParam String id) {
        return roomService.findById(id);
    }

    @PostMapping
    Room save(@RequestBody @Valid Room room) {
        return roomService.save(room);
    }

    @PutMapping("/update")
    Room update(@RequestParam String id, @RequestBody @Valid Room room) {
        return roomService.update(id, room);
    }

    @DeleteMapping("/delete/id")
    String delete(@RequestParam String id){
        return roomService.deleteById(id);
    }

    @DeleteMapping("/delete/all")
    String deleteAll() {
        return roomService.deleteAll();
    }
}