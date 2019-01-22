package pl.marcool.intivepatronage.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReservationDTO {
    @NotBlank(message = "Reservation ID must not be null and can't consist of only white characters")
    @Size(min = 2, max = 20, message = "Reservation ID must be between 2 and 20 characters long")
    private String id;
    @NotNull(message = "Enter beginDate")
    @Pattern(regexp = "[2]+[0]+[1-2]+[0-9]+\\-+[0-1]+[0-9]+\\-+[0-3]+[0-9]+\\s+[0-2]+[0-9]+\\:+[0-5]+[0-9]", message = "Begin date must be in the format YYYY-MM-DD HH:mm")
    private String beginDate;
    @NotNull(message = "Enter endDate")
    @Pattern(regexp = "[2]+[0]+[1-2]+[0-9]+\\-+[0-1]+[0-9]+\\-+[0-3]+[0-9]+\\s+[0-2]+[0-9]+\\:+[0-5]+[0-9]", message = "Begin date must be in the format YYYY-MM-DD HH:mm")
    private String endDate;
    @NotNull(message = "Enter Organization ID")
    private String organizationId;
    @NotNull(message = "Enter Room ID")
    private String conferenceRoomId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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
        return "ReservationDTO{" +
                "id='" + id + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", organizationId='" + organizationId + '\'' +
                ", conferenceRoomId='" + conferenceRoomId + '\'' +
                '}';
    }
}

