package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.ConferenceRoom;
import pl.marcool.intivepatronage.repositores.ConferenceRoomRepository;

import java.util.Optional;

@Service
public class ConferenceRoomService {

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    public void save(ConferenceRoom conferenceRoom) throws MyExceptions {
        if (conferenceRoomRepository.findById(conferenceRoom.getId()).isPresent())
            throw new MyExceptions("Conference Room ID:" + conferenceRoom.getId() + "- already exists");
        if (conferenceRoomRepository.findByName(conferenceRoom.getName()).isPresent())
            throw new MyExceptions("Conference Room name:" + conferenceRoom.getName() + "- already exists");
        conferenceRoomRepository.save(conferenceRoom);
    }

    public Iterable<ConferenceRoom> getAll() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom findById(String id) throws MyExceptions {
        Optional<ConferenceRoom> conferenceRoom = conferenceRoomRepository.findById(id);
        if (conferenceRoom.isEmpty()) throw new MyExceptions("Conference Room ID:" + id + "- not found");
        return conferenceRoom.get();
    }

    public void update(String id, ConferenceRoom conferenceRoom) throws MyExceptions {
        if (conferenceRoomRepository.findById(id).isEmpty())
            throw new MyExceptions("Conference Room ID:" + id + "- not found");
        if (conferenceRoomRepository.findById(conferenceRoom.getId())
                .stream()
                .anyMatch(cr -> !cr.getId().equals(id)))
            throw new MyExceptions("Conference Room ID:" + conferenceRoom.getId() + "- already exists");
        if (conferenceRoomRepository.findByName(conferenceRoom.getName())
                .stream()
                .anyMatch(cr -> !cr.getId().equals(id)))
            throw new MyExceptions("Conference Room name:" + conferenceRoom.getName() + "- already exists");
        conferenceRoomRepository.deleteById(id);
        conferenceRoomRepository.save(conferenceRoom);
    }

    public void deleteById(String id) throws MyExceptions {
        if (conferenceRoomRepository.findById(id).isEmpty())
            throw new MyExceptions("Conference Room ID:" + id + "- not found");
        conferenceRoomRepository.deleteById(id);
    }

    public void deleteAll() {
        conferenceRoomRepository.deleteAll();
    }
}