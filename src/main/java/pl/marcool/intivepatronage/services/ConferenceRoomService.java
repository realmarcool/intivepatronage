package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.ConferenceRoom;
import pl.marcool.intivepatronage.repositores.ConferenceRoomRepository;
import java.util.List;

@Service
public class ConferenceRoomService {

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;
    private String message = "The following error was detected:\n";

    public void save(ConferenceRoom conferenceRoom) throws MyExceptions {
        String isNameExist = findByName(conferenceRoom.getName()).getName();
        String isIdExist = findById(conferenceRoom.getId()).getId();
        if (!isIdExist.equals("empty"))
            throw new MyExceptions(message + "ID: " + conferenceRoom.getId() + "' already exists");
        if (!isNameExist.equals("empty"))
            throw new MyExceptions(message + "Name: '" + conferenceRoom.getName() + "' already exists");
        conferenceRoomRepository.save(conferenceRoom);
    }

    public List getAll() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom findById(String id) {
        return conferenceRoomRepository.findById(id);
    }

    public void update(String id, ConferenceRoom conferenceRoom) throws MyExceptions {
        String isOldIdExist = findById(id).getId();
        String isNewIdExist = findById(conferenceRoom.getId()).getId();
        ConferenceRoom isNewNameExist = findByName(conferenceRoom.getName());
        if (isOldIdExist.equals("empty")) throw new MyExceptions(message + "ID: '" + id + "' not found");
        if (!isNewIdExist.equals("empty") & !isOldIdExist.equals(isNewIdExist))
            throw new MyExceptions(message + "ID: '" + isNewIdExist + "' already exists");
        if (!isNewNameExist.getName().equals("empty") & !isNewNameExist.getId().equals(id))
            throw new MyExceptions(message + "Name: '" + conferenceRoom.getName() + "' already exists");
        conferenceRoomRepository.update(id, conferenceRoom);
    }

    public void deleteById(String id) {
        conferenceRoomRepository.deleteById(id);
    }

    public void deleteAll() {
        conferenceRoomRepository.deleteAll();
    }

    public ConferenceRoom findByName(String name) {
        return conferenceRoomRepository.findByName(name);
    }
}