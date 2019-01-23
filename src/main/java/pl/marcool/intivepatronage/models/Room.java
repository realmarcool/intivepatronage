package pl.marcool.intivepatronage.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Room {

    private String name;
    @Id
    private String id;
    private int floor;
    private boolean availability;
    private int seating;
    private int standingPlace;
    private int lyingPlace;
    private int hammock;
    private String projector;
    private boolean phone;
    private int phoneInNumber;
    private String phoneOutNumber;
    private String communicationInterface;

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