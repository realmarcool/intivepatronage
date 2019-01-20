package pl.marcool.intivepatronage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.dto.RoomDTO;
import pl.marcool.intivepatronage.services.RoomsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    @Autowired
    private RoomsService roomsService;

    @GetMapping
    List<RoomDTO> getAll() {
        return roomsService.getAll();
    }

    @GetMapping("{id}")
    RoomDTO getById(@PathVariable String id) {
        return roomsService.findById(id);
    }

    @PostMapping
    RoomDTO save(@RequestBody @Valid RoomDTO roomDTO) {
        return roomsService.save(roomDTO);
    }

    @PutMapping("{id}")
    RoomDTO update(@PathVariable String id, @RequestBody @Valid RoomDTO roomDTO) {
        return roomsService.update(id, roomDTO);
    }

    @DeleteMapping("{id}")
    String delete(@PathVariable String id) {
        return roomsService.deleteById(id);
    }

    @DeleteMapping
    String deleteAll() {
        return roomsService.deleteAll();
    }
}