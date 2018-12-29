package pl.marcool.intivepatronage.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

public class Reservation {

    @NotBlank(message = "ID nie może składać się z samych białych znaków")
    @Size(min = 2, max = 20, message = "ID musi mieć długość od 2 do 20")
    @NotNull(message = "Musisz podać ID")
    private String id;
    private LocalDate start;
    private LocalDate end;
    private String organizationId;
    private String conferenceRoomId;

    public Reservation(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
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
                ", start=" + start +
                ", end=" + end +
                ", organizationId='" + organizationId + '\'' +
                ", conferenceRoomId='" + conferenceRoomId + '\'' +
                '}';
    }
}
