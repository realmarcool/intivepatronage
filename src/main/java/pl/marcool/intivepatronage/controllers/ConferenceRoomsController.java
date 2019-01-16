package pl.marcool.intivepatronage.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.ConferenceRoom;
import pl.marcool.intivepatronage.models.JsonMessage;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.ConferenceRoomService;
import pl.marcool.intivepatronage.services.MyExceptions;

import javax.validation.Valid;

@RestController
public class ConferenceRoomsController {

    @Autowired
    private ConferenceRoomService conferenceRoomService;
    @Autowired
    private CheckingService checkingService;

    Gson gson = new Gson();
    JsonMessage jsonMessage = new JsonMessage();

    @GetMapping("/conferencerooms")
    ResponseEntity getAll() {
        jsonMessage.setOperation("getAll");
        jsonMessage.setStatus("success");
        jsonMessage.setMessage(null);
        return ResponseEntity.ok().body(gson.toJson(jsonMessage) + conferenceRoomService.getAll());
    }

    @GetMapping("/conferencerooms/id")
    ResponseEntity getById(@RequestParam String id) {
        try {
            ConferenceRoom conferenceRoom = conferenceRoomService.findById(id);
            jsonMessage.setOperation("getById");
            jsonMessage.setStatus("success");
            jsonMessage.setMessage(null);
            return ResponseEntity.ok().body(gson.toJson(jsonMessage) + conferenceRoom);
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("getById");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @PostMapping("/conferencerooms")
    ResponseEntity save(@RequestBody @Valid ConferenceRoom conferenceRoom, BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            conferenceRoomService.save(conferenceRoom);
            jsonMessage.setOperation("save");
            jsonMessage.setStatus("success");
            jsonMessage.setMessage(null);
            return ResponseEntity.ok().body(gson.toJson(jsonMessage) + conferenceRoom);
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("save");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @PutMapping("/conferencerooms/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid ConferenceRoom conferenceRoom,
                          BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            conferenceRoomService.update(id, conferenceRoom);
            jsonMessage.setOperation("update");
            jsonMessage.setStatus("success");
            jsonMessage.setMessage(null);
            return ResponseEntity.ok().body(gson.toJson(jsonMessage) + conferenceRoom);
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("update");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @DeleteMapping("/conferencerooms/delete/id")
    ResponseEntity delete(@RequestParam String id) {
        try {
            conferenceRoomService.deleteById(id);
            jsonMessage.setOperation("deleteById");
            jsonMessage.setStatus("success");
            jsonMessage.setMessage("Conference Room ID:" + id + " - has been successfully deleted.");
            return ResponseEntity.ok().body(gson.toJson(jsonMessage));
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("deleteById");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @DeleteMapping("/conferencerooms/delete/all")
    ResponseEntity deleteAll() {
        conferenceRoomService.deleteAll();
        jsonMessage.setOperation("deleteAll");
        jsonMessage.setStatus("success");
        jsonMessage.setMessage("The entire Conference Room database has been successfully deleted.");
        return ResponseEntity.ok().body(gson.toJson(jsonMessage));
    }
}