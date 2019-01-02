package pl.marcool.intivepatronage.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Reservation {

    @NotBlank(message = "ID must not be null and can't consist of only white characters")
    @Size(min = 2, max = 20, message = "ID must be between 2 and 20 characters long")
    private String id;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String organizationId;
    private String conferenceRoomId;

    public Reservation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getConferenceRoomId() {
        return conferenceRoomId;
    }

    public void setConferenceRoomId(String conferenceRoomId) {
        this.conferenceRoomId = conferenceRoomId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", organizationId='" + organizationId + '\'' +
                ", conferenceRoomId='" + conferenceRoomId + '\'' +
                '}';
    }
}
