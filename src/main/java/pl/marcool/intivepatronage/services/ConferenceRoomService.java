package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.marcool.intivepatronage.models.ConferenceRoom;
import pl.marcool.intivepatronage.repositores.ConferenceRoomRepository;

@Service
public class ConferenceRoomService {

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;
    @Autowired
    private CheckingService checkingService;

    public ConferenceRoom save(ConferenceRoom conferenceRoom, BindingResult bindingResult) throws MyExceptions {
        checkingService.checkBindingResult(bindingResult);
        if (conferenceRoomRepository.findById(conferenceRoom.getId()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"'" + conferenceRoom.getId() + "' - already exist\"}");
        if (conferenceRoomRepository.findByName(conferenceRoom.getName()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"'" + conferenceRoom.getName() + "- already exists\"}");
        return conferenceRoomRepository.save(conferenceRoom);
    }

    public Iterable<ConferenceRoom> getAll() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom findById(String id) throws MyExceptions {
        return conferenceRoomRepository.findById(id)
                .orElseThrow(() -> new MyExceptions(404,
                        "{\"Error\":\"'" + id + "' - not found\"}"));
    }

    public ConferenceRoom update(String id, ConferenceRoom conferenceRoom) throws MyExceptions {
        if (conferenceRoomRepository.findByName(conferenceRoom.getName()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"'" + conferenceRoom.getName() + "' - already exist\"}");
        conferenceRoomRepository.deleteById(findById(id).getId());
        return conferenceRoomRepository.save(conferenceRoom);
    }

    public String deleteById(String id) throws MyExceptions {
        conferenceRoomRepository.deleteById(findById(id).getId());
        return "{\"'" + id + "' - deleted\"}";
    }

    public String deleteAll() {
        conferenceRoomRepository.deleteAll();
        return "{\"Entire Conference Room database deleted\"}";
    }
}