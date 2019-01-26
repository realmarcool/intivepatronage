package pl.marcool.intivepatronage.services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.models.dto.ReservationDTO;
import pl.marcool.intivepatronage.repositores.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationsService {

    private final RoomsService roomsService;
    private final ReservationRepository reservationRepository;
    private final OrganizationsService organizationsService;

    public ReservationsService(RoomsService roomsService, ReservationRepository reservationRepository,
                               OrganizationsService organizationsService) {
        this.roomsService = roomsService;
        this.reservationRepository = reservationRepository;
        this.organizationsService = organizationsService;
    }

    public ReservationDTO save(ReservationDTO reservationDTO) throws IllegalArgumentException {
        var reservation = dtoToReservation(reservationDTO);
        if (reservationRepository.findById(reservation.getId()).isPresent()) {
            throw new IllegalArgumentException(reservation.getId() + " - already exist");
        }
        isCommonConditionsCorrect("none", reservation);
        reservationRepository.save(reservation);
        return reservationDTO;
    }

    public List<ReservationDTO> getAll() {
        return reservationRepository.findAll().stream()
                .map(this::reservationToDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO findById(String id) throws IllegalArgumentException {
        return reservationToDTO(reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " - not found")));
    }

    public ReservationDTO update(String id, ReservationDTO reservationDTO) throws IllegalArgumentException {
        var reservation = dtoToReservation(reservationDTO);
        if (reservationRepository.findById(reservation.getId()).isPresent() & !id.equals(reservation.getId())) {
            throw new IllegalArgumentException(reservation.getId() + " - already exist");
        }
        isCommonConditionsCorrect(findById(id).getId(), reservation);
        reservationRepository.deleteById(id);
        return reservationToDTO(reservationRepository.save(reservation));
    }

    public void deleteById(String id) {
        reservationRepository.deleteById(findById(id).getId());
    }

    public void deleteAll() {
        reservationRepository.deleteAll();
    }

    private void isCommonConditionsCorrect(String id, Reservation reservation) throws IllegalArgumentException {
        roomsService.findById(reservation.getConferenceRoomId());
        organizationsService.findById(reservation.getOrganizationId());
        var reservationBegin = reservation.getBeginDate();
        var reservationEnd = reservation.getEndDate();
        if (reservationBegin.isAfter(reservationEnd)) {
            throw new IllegalArgumentException("The end date is earlier than the start date");
        }
        if (reservationBegin.plusMinutes(5).isAfter(reservationEnd)) {
            throw new IllegalArgumentException("Minimum rental time is 5 minutes");
        }
        if (reservationBegin.plusHours(2).isBefore(reservationEnd)) {
            throw new IllegalArgumentException("Maximum rental time is 2 hours");
        }
        var isNewReservationStartsDuringExistingOne = reservationRepository
                .findByConferenceRoomIdAndBeginDateBeforeAndEndDateAfterAndIdIsNotContaining
                        (reservation.getConferenceRoomId(), reservationBegin.plusSeconds(60), reservationBegin, id);
        var isNewReservationEndsDuringExistingOneOrExistingOneContainedInNewReservation = reservationRepository
                .findByConferenceRoomIdAndBeginDateAfterAndBeginDateBeforeAndIdIsNotContaining
                        (reservation.getConferenceRoomId(), reservationBegin, reservationEnd, id);
        if ((!isNewReservationStartsDuringExistingOne.isEmpty())
                || (!isNewReservationEndsDuringExistingOneOrExistingOneContainedInNewReservation.isEmpty())) {
            throw new IllegalArgumentException("Room is already reserved at this time");
        }
    }

    Reservation dtoToReservation(ReservationDTO reservationDTO) {
        var reservation = new Reservation();
        BeanUtils.copyProperties(reservationDTO, reservation);
        return reservation;
    }

    ReservationDTO reservationToDTO(Reservation reservation) {
        var reservationDTO = new ReservationDTO();
        BeanUtils.copyProperties(reservation, reservationDTO);
        return reservationDTO;
    }
}