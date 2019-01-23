package pl.marcool.intivepatronage.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    private String id;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private String organizationId;
    private String conferenceRoomId;

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
}
