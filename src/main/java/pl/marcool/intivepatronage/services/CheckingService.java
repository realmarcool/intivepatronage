package pl.marcool.intivepatronage.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service
public class CheckingService {

    public String checkBindingResult(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            list.stream().forEach(
                    t -> sb.append(t.getDefaultMessage()).append(", \n"));
            return "Uwaga, pojawiły się następujące błędy:\n" + sb;
        }
        return "ok";
    }
}
