package pl.marcool.intivepatronage.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.models.Room;
import pl.marcool.intivepatronage.models.dto.OrganizationDTO;
import pl.marcool.intivepatronage.models.dto.ReservationDTO;
import pl.marcool.intivepatronage.models.dto.RoomDTO;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;
import pl.marcool.intivepatronage.repositores.ReservationRepository;
import pl.marcool.intivepatronage.repositores.RoomRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationsServiceTest {

//    @TestConfiguration
//    static class TestContextConfiguration {
//        @MockBean
//        OrganizationRepository organizationRepository;
//        @MockBean
//        private RoomRepository roomRepository;
//        @MockBean
//        private ReservationRepository reservationRepository;
//        @MockBean
//        private ObjectMapper objectMapper;
////        @Bean
////        public OrganizationsService organizationsService() {
////            return new OrganizationsService(organizationRepository, objectMapper);
////        }
////        @Bean
////        public RoomsService roomsService(){
////            return new RoomsService(roomRepository, objectMapper);

//        @Bean
//        public ReservationsService reservationsService() {
//            return new ReservationsService(roomsService, reservationRepository, organizationsService);
//        }

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ObjectMapper objectMapper;

    OrganizationsService organizationsService;
    RoomsService roomsService;
    ReservationsService reservationsService;

//    @Autowired
//    private OrganizationsService organizationsService;
//    @Autowired
//    private RoomsService roomsService;
//    @Autowired
//    private ReservationsService reservationsService;
    OrganizationDTO organization = new OrganizationDTO();
    RoomDTO room1 = new RoomDTO();
    ReservationDTO existReservation1 = new ReservationDTO();
    ReservationDTO testReservationToDTO = new ReservationDTO();
    Reservation testDTOtoReservation = new Reservation();

    ReservationDTO incorrectReservation1 = new ReservationDTO();
    ReservationDTO correctReservation1 = new ReservationDTO();


    @Before
    public void setUp() throws Exception {
        organizationsService = new OrganizationsService(organizationRepository, objectMapper);
        roomsService = new RoomsService(roomRepository, objectMapper);
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

        existReservation1.setId("reserv1");
        existReservation1.setOrganizationId("Org1");
        existReservation1.setConferenceRoomId("CR1");
        existReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 13, 00));
        existReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 14, 00));

        incorrectReservation1.setId("reserv2");
        incorrectReservation1.setOrganizationId("Org1");
        incorrectReservation1.setConferenceRoomId("CR1");

        correctReservation1.setId("reserv2");
        correctReservation1.setOrganizationId("Org1");
        correctReservation1.setConferenceRoomId("CR1");

        roomsService.save(room1);
        organizationsService.save(organization);
        reservationsService.save(existReservation1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void save13to14() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 13, 00));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 14, 00));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13to13_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 13, 00));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 13, 30));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save12_30to13_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 12, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 13, 30));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save12_30to14_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 12, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 14, 30));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13_30to13_50() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 13, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 13, 50));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13_30to14_30() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 13, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 14, 30));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13_30to14_00() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 13, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 14, 00));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save12_30to13_01() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 12, 30));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 13, 01));
        reservationsService.save(incorrectReservation1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void save13_59to14_05() {
        incorrectReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 13, 59));
        incorrectReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 14, 05));
        reservationsService.save(incorrectReservation1);
    }
    @Test
    public void save12to13() {
        correctReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 12, 00));
        correctReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 13, 00));
        reservationsService.save(correctReservation1);
        assertSame(correctReservation1.getBeginDate(), reservationsService.findById(correctReservation1.getId()).getBeginDate());
    }
    @Test
    public void save14to15() {
        correctReservation1.setBeginDate(LocalDateTime.of(2019, 01, 20, 14, 00));
        correctReservation1.setEndDate(LocalDateTime.of(2019, 01, 20, 15, 00));
        reservationsService.save(correctReservation1);
        assertSame(correctReservation1.getBeginDate(), reservationsService.findById(correctReservation1.getId()).getBeginDate());
    }
//
//    @Test
//    public void getAll() {
//    }
//
//    @Test
//    public void findById() {
//    }
//
//    @Test
//    public void update() {
//    }
//
//    @Test
//    public void deleteById() {
//    }
//
//    @Test
//    public void deleteAll() {
//    }

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
        testDTOtoReservation.setId("reserv2");
        testDTOtoReservation.setOrganizationId("Org1");
        testDTOtoReservation.setConferenceRoomId("CR1");
        testDTOtoReservation.setBeginDate(LocalDateTime.of(2019, 01, 20, 13, 30));
        testDTOtoReservation.setEndDate(LocalDateTime.of(2019, 01, 20, 14, 30));
        testReservationToDTO = reservationsService.reservationToDTO(testDTOtoReservation);
        assertNotNull(testReservationToDTO);
        assertSame(testReservationToDTO.getId(), testDTOtoReservation.getId());
        assertSame(testReservationToDTO.getConferenceRoomId(), testDTOtoReservation.getConferenceRoomId());
        assertSame(testReservationToDTO.getBeginDate(), testDTOtoReservation.getBeginDate());
        assertSame(testReservationToDTO.getEndDate(), testDTOtoReservation.getEndDate());

    }
}