package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Room;
import pl.marcool.intivepatronage.repositores.ConferenceRoomRepository;

@Service
public class RoomService {

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    public Room save(Room room) throws MyExceptions {
        if (conferenceRoomRepository.findById(room.getId()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"" + room.getId() + " - already exist\"}");
        if (conferenceRoomRepository.findByName(room.getName()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"" + room.getName() + " - already exists\"}");
        return conferenceRoomRepository.save(room);
    }

    public Iterable<Room> getAll() {
        return conferenceRoomRepository.findAll();
    }

    public Room findById(String id) throws MyExceptions {
        return conferenceRoomRepository.findById(id)
                .orElseThrow(() -> new MyExceptions(404,
                        "{\"Error\":\"" + id + " - not found\"}"));
    }

    public Room update(String id, Room room) throws MyExceptions {
        if (conferenceRoomRepository.findByName(room.getName()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"" + room.getName() + " - already exist\"}");
        conferenceRoomRepository.deleteById(findById(id).getId());
        return conferenceRoomRepository.save(room);
    }

    public String deleteById(String id) throws MyExceptions {
        conferenceRoomRepository.deleteById(findById(id).getId());
        return "{\"" + id + " - deleted\"}";
    }

    public String deleteAll() {
        conferenceRoomRepository.deleteAll();
        return "{\"Entire Conference Room database deleted\"}";
    }
}