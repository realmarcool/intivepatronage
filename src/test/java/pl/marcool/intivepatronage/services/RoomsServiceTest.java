package pl.marcool.intivepatronage.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marcool.intivepatronage.models.dto.RoomDTO;
import pl.marcool.intivepatronage.repositores.RoomRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomsServiceTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private RoomsService roomsService;

    private final RoomDTO room1 = new RoomDTO();
    private final RoomDTO room2 = new RoomDTO();

    @Before
    public void setUp() {
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
        room2.setName("conference room 2");
        room2.setId("CR2");
        room2.setFloor(10);
        room2.setAvailability(true);
        room2.setSeating(20);
        room2.setStandingPlace(20);
        room2.setLyingPlace(20);
        room2.setHammock(20);
        room2.setPhone(true);
        room2.setPhoneInNumber(91);
        room2.setPhoneOutNumber("+12 123456789");
        room2.setCommunicationInterface("bluetooth");
        roomsService.save(room1);
    }

    @Test
    public void save() {
        roomsService.save(room2);
        assertEquals(2, roomsService.getAll().size());
    }

    @Test
    public void getAll() {
        assertEquals(1, roomsService.getAll().size());
    }

    @Test
    public void findById() {
        assertEquals(roomsService.findById(room1.getId()).getId(), room1.getId());
    }

    @Test
    public void update() {
        room1.setId("CR2");
        room1.setName("Room2");
        roomsService.update("CR1", room1);
        assertEquals("Room2", roomsService.findById("CR2").getName());
    }

    @Test
    public void deleteById() {
        roomsService.deleteById(room1.getId());
        assertEquals(0, roomsService.getAll().size());
    }

    @Test
    public void deleteAll() {
        roomsService.deleteAll();
        assertEquals(0, roomsService.getAll().size());
    }
}