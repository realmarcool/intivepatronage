package pl.marcool.intivepatronage.models;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class ConferenceRoom {

    @NotBlank(message = "Name must not be null and can't consist of only white characters")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters long")
    private String name;

    @Id
    @NotBlank(message = "ID must not be null and can't consist of only white characters")
    @Size(min = 2, max = 20, message = "ID must be between 2 and 20 characters long")
    private String id;

    @Range(min = 0, max = 10, message = "Floor must be between 0 and 10")
    @NotNull(message = "You must enter the floor")
    private int floor;
    private boolean availability;
    private int seating;
    private int standingPlace;
    private int lyingPlace;
    private int hammock;
    private String projector;
    private boolean phone;

    @Range(min = 0, max = 99, message = "Internal number must be between 0 and 99")
    private int phoneInNumber;

    @Pattern(regexp = "\\++[0-9]{2}+\\s+[0-9]{9}", message = "The external number must have 13 characters in the format +12 123456789")
    private String phoneOutNumber;

    @Pattern(regexp = "USB|bluetooth", message = "The phone interface can only have one of two values: 'USB' or 'bluetooth")
    private String communicationInterface;

    public ConferenceRoom() {
    }

    @Override
    public String toString() {
        return "ConferenceRoom{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", floor=" + floor +
                ", availability=" + availability +
                ", seating=" + seating +
                ", standingPlace=" + standingPlace +
                ", lyingPlace=" + lyingPlace +
                ", hammock=" + hammock +
                ", projector='" + projector + '\'' +
                ", phone=" + phone +
                ", phoneInNumber=" + phoneInNumber +
                ", phoneOutNumber='" + phoneOutNumber + '\'' +
                ", communicationInterface='" + communicationInterface + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getSeating() {
        return seating;
    }

    public void setSeating(int seating) {
        this.seating = seating;
    }

    public int getStandingPlace() {
        return standingPlace;
    }

    public void setStandingPlace(int standingPlace) {
        this.standingPlace = standingPlace;
    }

    public int getLyingPlace() {
        return lyingPlace;
    }

    public void setLyingPlace(int lyingPlace) {
        this.lyingPlace = lyingPlace;
    }

    public int getHammock() {
        return hammock;
    }

    public void setHammock(int hammock) {
        this.hammock = hammock;
    }

    public String getProjector() {
        return projector;
    }

    public void setProjector(String projector) {
        this.projector = projector;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

    public int getPhoneInNumber() {
        return phoneInNumber;
    }

    public void setPhoneInNumber(int phoneInNumber) {
        this.phoneInNumber = phoneInNumber;
    }

    public String getPhoneOutNumber() {
        return phoneOutNumber;
    }

    public void setPhoneOutNumber(String phoneOutNumber) {
        this.phoneOutNumber = phoneOutNumber;
    }

    public String getCommunicationInterface() {
        return communicationInterface;
    }

    public void setCommunicationInterface(String communicationInterface) {
        this.communicationInterface = communicationInterface;
    }
}