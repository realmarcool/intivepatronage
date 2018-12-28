package pl.marcool.intivepatronage.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Organization {
    @NotBlank(message = "Nazwa nie może składać się z samych białych znaków")
    @Size(min = 2, max = 20, message = "Nazwa musi mieć długość od 2 do 20")
    @NotNull(message = "Musisz podać nazwę sali")
    private String name;

    public Organization(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                '}';
    }
}
