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
    ResponseEntity getById(@RequestParam String id){
        if(!findById(id).getName().equals("pusty")){
            return ResponseEntity.ok().body(conferenceRoomService.findById(id));
        }
        return ResponseEntity.badRequest().body("Nie znaleziono rekordu o ID '" + id + "'");
    }

    @PostMapping("/conferenceroom")
    ResponseEntity save(@RequestBody @Valid ConferenceRoom conferenceRoom, BindingResult bindingResult) {

        String checkBR = checkingService.checkBindingResult(bindingResult);

        if(checkBR.equals("ok")){
            String addConferenceRoom = conferenceRoomService.save(conferenceRoom);
            System.out.println(addConferenceRoom);
            if (addConferenceRoom.equals("ok")) return ResponseEntity.ok("Pomyślnie dodano:\n" + conferenceRoom);
            else return ResponseEntity.badRequest().body(addConferenceRoom);
        }
        else return ResponseEntity.badRequest().body(checkBR);
    }

    @PutMapping("/conferenceroom/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid ConferenceRoom conferenceRoom,
                          BindingResult bindingResult) {
        String checkBR = checkingService.checkBindingResult(bindingResult);
        if (!findById(id).getName().equals("pusty")) { //Sprawdzenie czy podane ID istnieje
            if(findById(conferenceRoom.getId()).getName().equals("pusty")) { //Sprawdzenie czy nowe id z updatowenego confrenceRoom już istnieje w bazie
                if (checkBR.equals("ok")) {  //Sprawdzenie BindingResults
                    //Jeżeli 1.poprawne parametry, 2.stare id znalezione, 3.nowe id nie znalezione
                    conferenceRoomService.update(id, conferenceRoom);
                    return ResponseEntity.ok().body("Pomyślny update:\n" + conferenceRoom);
                }
                // Jeżeli błędne parametry
                else return ResponseEntity.badRequest().body(checkBR);
            }
            // Jeżeli nowe id już istnieje w bazie
            else return ResponseEntity.badRequest().body("Nowe id '" + conferenceRoom.getId() + "' już istnieje ww bazie");
        }
        // Jeżeli podane id nie istnieje w bazie
        return ResponseEntity.badRequest().body("Nie znaleziono id '" + id + "' w bazie");
    }

    @DeleteMapping("/conferenceroom/delete/id")
    ResponseEntity delete(@RequestParam String id){
        if(!findById(id).getName().equals("pusty")){
            conferenceRoomService.deleteById(id);
            return ResponseEntity.ok().body("Pomyślnie skasowano rekord o ID '" + id + "'");
        }
        return ResponseEntity.badRequest().body("Nie znaleziono rekordu o ID '" + id + "'");
    }

    @DeleteMapping("/conferenceroom/delete/all")
    ResponseEntity deleteAll(){
        conferenceRoomService.deleteAll();
        return ResponseEntity.ok().body("Skasowano całą bazę Conference Room");
    }

    ConferenceRoom findById(String id){
        return conferenceRoomService.findById(id);
    }
}