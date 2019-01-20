package pl.marcool.intivepatronage.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OrganizationDTO {
    @NotBlank(message = "Name must not be null and can't consist of only white characters")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters long")
    private String name;

    public OrganizationDTO() {
    }

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

