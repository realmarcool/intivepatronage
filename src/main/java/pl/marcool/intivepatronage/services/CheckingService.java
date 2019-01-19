package pl.marcool.intivepatronage.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service
public class CheckingService {

    public void checkBindingResult(BindingResult bindingResult) throws MyExceptions {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            list.stream().forEach(t -> sb.append(t.getDefaultMessage()).append("."));
            throw new MyExceptions(400, "{\"Error\":\"" + sb.toString() + "\"}");
        }
    }
}
