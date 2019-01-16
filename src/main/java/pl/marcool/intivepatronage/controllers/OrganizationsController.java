package pl.marcool.intivepatronage.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcool.intivepatronage.models.JsonMessage;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.services.CheckingService;
import pl.marcool.intivepatronage.services.MyExceptions;
import pl.marcool.intivepatronage.services.OrganizationService;

import javax.validation.Valid;

@RestController
public class OrganizationsController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private CheckingService checkingService;

    Gson gson = new Gson();
    JsonMessage jsonMessage = new JsonMessage();

    @GetMapping("/organizations")
    ResponseEntity getAll() {
        jsonMessage.setOperation("getAll");
        jsonMessage.setStatus("success");
        return ResponseEntity.ok().body(gson.toJson(jsonMessage) + organizationService.getAll());
    }

    @GetMapping("/organizations/id")
    ResponseEntity getById(@RequestParam String id) {
        try {
            Organization organization = organizationService.findById(id);
            jsonMessage.setOperation("getById");
            jsonMessage.setStatus("success");
            return ResponseEntity.ok().body(gson.toJson(jsonMessage) + organization);
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("getById");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @PostMapping("/organizations")
    ResponseEntity save(@RequestBody @Valid Organization organization, BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            organizationService.save(organization);
            jsonMessage.setOperation("save");
            jsonMessage.setStatus("success");
            return ResponseEntity.ok().body(gson.toJson(jsonMessage) + organization);
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("save");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @PutMapping("/organizations/update")
    ResponseEntity update(@RequestParam String id,
                          @RequestBody @Valid Organization organization,
                          BindingResult bindingResult) {
        try {
            checkingService.checkBindingResult(bindingResult);
            organizationService.update(id, organization);
            jsonMessage.setOperation("update");
            jsonMessage.setStatus("success");
            return ResponseEntity.ok().body(gson.toJson(jsonMessage) + organization);
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("update");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @DeleteMapping("/organizations/delete/id")
    ResponseEntity delete(@RequestParam String id) {
        try {
            organizationService.deleteById(id);
            jsonMessage.setOperation("deleteById");
            jsonMessage.setStatus("success");
            jsonMessage.setMessage("Organization ID:" + id + " - has been successfully deleted.");
            return ResponseEntity.ok().body(gson.toJson(jsonMessage));
        } catch (MyExceptions myExceptions) {
            jsonMessage.setOperation("deleteById");
            jsonMessage.setStatus("error");
            jsonMessage.setMessage(myExceptions.getMessage());
            return ResponseEntity.badRequest().body(gson.toJson(jsonMessage));
        }
    }

    @DeleteMapping("/organizations/delete/all")
    ResponseEntity deleteAll() {
        organizationService.deleteAll();
        return ResponseEntity.ok().body("The entire Organization database has been successfully deleted.");
    }
}