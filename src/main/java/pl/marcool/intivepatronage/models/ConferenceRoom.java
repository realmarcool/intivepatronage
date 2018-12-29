package pl.marcool.intivepatronage.models;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ConferenceRoom {

    @NotBlank(message = "Nazwa nie może składać się z samych białych znaków")
    @Size(min = 2, max = 20, message = "Nazwa musi mieć długość od 2 do 20")
    @NotNull(message = "Musisz podać nazwę sali")
    private String name;

    @Id
    @NotBlank(message = "Identyfikator nie może składać się z samych białych znaków")
    @Size(min = 2, max = 20, message = "Identyfikator musi mieć długość od 2 do 20")
    @NotNull(message = "Musisz podać ID sali")
    private String id;

    @Range(min = 0, max = 10, message = "Piętro może być z przedziału 0-10")
    @NotNull(message = "Musisz podać piętro")
    private int floor;
    private boolean availability;
    private int seating;
    private int standingPlace;
    private int lyingPlace;
    private int hammock;
    private String projector;
    private boolean phone;

    @Range(min = 0, max = 99, message = "Numer wewnętrzny dozwolony w zakresie od 0 do 99")
    private int phoneInNumber;

    @Pattern(regexp = "\\++[0-9]{2}+\\s+[0-9]{9}", message = "Numer zewnętrzny musi mieć 13 znaków w formacie +12 123456789")
    private String phoneOutNumber;

    @Pattern(regexp = "USB|bluetooth", message = "Interfejs telefonu może mieć tylko jedną z dwóch wartości: 'USB' lub 'bluetooth")
    private String communicationInterface;

    public ConferenceRoom() {}

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
        return id;
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