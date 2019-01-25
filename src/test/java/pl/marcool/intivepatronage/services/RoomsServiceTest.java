package pl.marcool.intivepatronage.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marcool.intivepatronage.models.Room;
import pl.marcool.intivepatronage.models.dto.RoomDTO;
import pl.marcool.intivepatronage.repositores.RoomRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomsServiceTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ObjectMapper objectMapper;

    RoomsService roomsService;

    RoomDTO room1 = new RoomDTO();
    RoomDTO room2 = new RoomDTO();
    RoomDTO testDTOtoRoom = new RoomDTO();
    Room testRoomToDTO = new Room();

    @Before
    public void setUp() throws Exception {
        roomsService = new RoomsService(roomRepository, objectMapper);
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
        room1.setName("conference room 2");
        room1.setId("CR2");
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
        roomsService.save(room1);
    }

    @Test
    public void save() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void update() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void deleteAll() {
    }
}