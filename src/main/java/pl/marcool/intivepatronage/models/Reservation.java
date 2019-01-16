package pl.marcool.intivepatronage.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Reservation {

    @NotBlank(message = "ID must not be null and can't consist of only white characters")
    @Size(min = 2, max = 20, message = "ID must be between 2 and 20 characters long")
    @Id
    private String id;
    @NotNull(message = "Enter beginDate")
    private LocalDateTime beginDate;
    @NotNull(message = "Enter endDate")
    private LocalDateTime endDate;
    @NotNull(message = "Enter Organization ID")
    private String organizationId;
    @NotNull(message = "Enter Conference Room ID")
    private String conferenceRoomId;

    public Reservation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
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

    private DateTimeFormatter dataFormat() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    @Override
    public String toString() {

        return "Reservation{" +
                "id= '" + id + '\'' +
                ", beginDate= " + beginDate.format(dataFormat()) +
                ", endDate= " + endDate.format(dataFormat()) +
                ", organizationId= '" + organizationId + '\'' +
                ", conferenceRoomId= '" + conferenceRoomId + '\'' +
                '}';
    }
}
