package pl.marcool.intivepatronage.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class Reservation {

    @NotBlank(message = "Nazwa nie może składać się z samych białych znaków")
    @Size(min = 2, max = 20, message = "Nazwa musi mieć długość od 2 do 20")
    @NotNull(message = "Musisz podać nazwę sali")
    private String id;
    private Date start;
    private Date end;
    private String organizationId;
    private String conferenceRoomId;

    public Reservation(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
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
