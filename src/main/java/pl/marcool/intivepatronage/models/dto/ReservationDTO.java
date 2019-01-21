package pl.marcool.intivepatronage.models.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationDTO {
    @NotBlank(message = "Reservation ID must not be null and can't consist of only white characters")
    @Size(min = 2, max = 20, message = "Reservation ID must be between 2 and 20 characters long")
    private String id;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @NotNull(message = "Enter beginDate")
    private LocalDateTime beginDate;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @NotNull(message = "Enter endDate")
    private LocalDateTime endDate;
    @NotNull(message = "Enter Organization ID")
    private String organizationId;
    @NotNull(message = "Enter Room ID")
    private String conferenceRoomId;

    public ReservationDTO() {
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

