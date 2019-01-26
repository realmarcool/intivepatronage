package pl.marcool.intivepatronage.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.models.dto.OrganizationDTO;
import pl.marcool.intivepatronage.models.dto.ReservationDTO;
import pl.marcool.intivepatronage.models.dto.RoomDTO;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;
import pl.marcool.intivepatronage.repositores.ReservationRepository;
import pl.marcool.intivepatronage.repositores.RoomRepository;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationsServiceTest {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private ReservationsService reservationsService;

    private final OrganizationDTO organization = new OrganizationDTO();
    private final RoomDTO room1 = new RoomDTO();
    private final ReservationDTO existReservation1 = new ReservationDTO();
    private final ReservationDTO existReservation2 = new ReservationDTO();
    private Reservation testDTOtoReservation = new Reservation();

    private final ReservationDTO incorrectReservation1 = new ReservationDTO();
    private final ReservationDTO correctReservation1 = new ReservationDTO();


    @Before
    public void setUp(){
        OrganizationsService organizationsService = new OrganizationsService(organizationRepository, objectMapper);
        RoomsService roomsService = new RoomsService(roomRepository, objectMapper);
        reservationsService = new ReservationsService(roomsService, reservationRepository, organizationsService);
        organization.setName("Org1");
        room1.setName("conference room 1");
        room1.setId("CR1");
        room1.setFloor(10);
        room1.setAvailability(true);
        room1.setSeating(20);
        room1.setStandingPlace(20);
        room1.setLyingPlace(20);
        room1.setHammock(20);
        room1.setPhone(true);
        room1.setPhoneInNumber(91);
        room1.setPhoneOutNumber("+12 123456789");
        room1.setCommunicationInterface("bluetooth");

        existReservation1.setId("Reservation1");
        existReservation1.setOrganizationId("Org1");
        existReservation1.setConferenceRoomId("CR1");
        existReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 0));
        existReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        existReservation2.setId("Reservation2");
        existReservation2.setOrganizationId("Org1");
        existReservation2.setConferenceRoomId("CR1");
        existReservation2.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        existReservation2.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 0));

        incorrectReservation1.setId("Reservation3");
        incorrectReservation1.setOrganizationId("Org1");
        incorrectReservation1.setConferenceRoomId("CR1");

        correctReservation1.setId("Reservation3");
        correctReservation1.setOrganizationId("Org1");
        correctReservation1.setConferenceRoomId("CR1");

        roomsService.save(room1);
        organizationsService.save(organization);
        reservationsService.save(existReservation1);
        reservationsService.save(existReservation2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void save13to14() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13to13_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save12_30to13_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 12, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save12_30to14_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 12, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 30));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13_30to13_50() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 50));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13_30to14_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 30));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13_30to14_00() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save12_30to13_01() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 12, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 1));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13_59to14_05() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 59));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 5));
        reservationsService.save(incorrectReservation1);
    }
    @Test
    public void save12to13() {
        correctReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 12, 0));
        correctReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 0));
        reservationsService.save(correctReservation1);
        assertSame(correctReservation1.getBeginDate(), reservationsService.findById(correctReservation1.getId()).getBeginDate());
    }
    @Test
    public void save14to15() {
        correctReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        correctReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(correctReservation1);
        assertSame(correctReservation1.getBeginDate(), reservationsService.findById(correctReservation1.getId()).getBeginDate());
    }

    @Test
    public void getAll() {
        assertEquals(2, reservationsService.getAll().size());
    }

    @Test
    public void findById() {
        assertSame(reservationsService.findById(existReservation1.getId()).getId(), existReservation1.getId());
    }

    @Test
    public void update() {
        correctReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        correctReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        correctReservation1.setId(existReservation1.getId());
        reservationsService.update(existReservation1.getId(), correctReservation1);
        assertSame(correctReservation1.getBeginDate(), reservationsService.findById(existReservation1.getId()).getBeginDate());
    }
    @Test(expected = IllegalArgumentException.class)
    public void update13to14() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(incorrectReservation1);
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        reservationsService.update(incorrectReservation1.getId(), incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void update13to13_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(incorrectReservation1);
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        reservationsService.update(incorrectReservation1.getId(), incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void update12_30to13_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(incorrectReservation1);
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 12, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        reservationsService.update(incorrectReservation1.getId(), incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void update12_30to14_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(incorrectReservation1);
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 12, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 30));
        reservationsService.update(incorrectReservation1.getId(), incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void update13_30to13_50() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(incorrectReservation1);
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 50));
        reservationsService.update(incorrectReservation1.getId(), incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void update13_30to14_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(incorrectReservation1);
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 30));
        reservationsService.update(incorrectReservation1.getId(), incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void update13_30to14_00() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(incorrectReservation1);
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        reservationsService.update(incorrectReservation1.getId(), incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void update12_30to13_01() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(incorrectReservation1);
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 12, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 13, 1));
        reservationsService.update(incorrectReservation1.getId(), incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void update13_59to14_05() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 14, 0));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 15, 0));
        reservationsService.save(incorrectReservation1);
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 59));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 5));
        reservationsService.update(incorrectReservation1.getId(), incorrectReservation1);
    }

    @Test
    public void deleteById() {
        reservationsService.deleteById(existReservation1.getId());
        assertEquals(1, reservationsService.getAll().size());
    }

    @Test
    public void deleteAll() {
        reservationsService.deleteAll();
        assertEquals(0, reservationsService.getAll().size());
    }

    @Test
    public void dtoToReservation() {
        testDTOtoReservation = reservationsService.dtoToReservation(existReservation1);
        assertNotNull(testDTOtoReservation);
        assertSame(testDTOtoReservation.getId(), existReservation1.getId());
        assertSame(testDTOtoReservation.getConferenceRoomId(), existReservation1.getConferenceRoomId());
        assertSame(testDTOtoReservation.getBeginDate(), existReservation1.getBeginDate());
        assertSame(testDTOtoReservation.getEndDate(), existReservation1.getEndDate());
    }

    @Test
    public void reservationToDTO() {
        testDTOtoReservation.setId("Reservation1");
        testDTOtoReservation.setOrganizationId("Org1");
        testDTOtoReservation.setConferenceRoomId("CR1");
        testDTOtoReservation.setBeginDate(LocalDateTime.of(2019, 1, 20, 13, 30));
        testDTOtoReservation.setEndDate(LocalDateTime.of(2019, 1, 20, 14, 30));
        ReservationDTO testReservationToDTO = reservationsService.reservationToDTO(testDTOtoReservation);
        assertNotNull(testReservationToDTO);
        assertSame(testReservationToDTO.getId(), testDTOtoReservation.getId());
        assertSame(testReservationToDTO.getConferenceRoomId(), testDTOtoReservation.getConferenceRoomId());
        assertSame(testReservationToDTO.getBeginDate(), testDTOtoReservation.getBeginDate());
        assertSame(testReservationToDTO.getEndDate(), testDTOtoReservation.getEndDate());
    }
}
