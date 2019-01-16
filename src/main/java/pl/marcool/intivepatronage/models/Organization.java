package pl.marcool.intivepatronage.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Organization {
    @NotBlank(message = "Name must not be null and can't consist of only white characters")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters long")
    @Id
    private String name;

    public Organization() {
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
