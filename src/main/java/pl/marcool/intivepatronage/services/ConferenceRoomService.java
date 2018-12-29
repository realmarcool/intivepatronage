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

    public String save(ConferenceRoom conferenceRoom) {
        String check = conferenceRoomRepository.checkIfIdOrNameExists(conferenceRoom.getId(), conferenceRoom.getName());
        if (check.equals("ok")) {
            conferenceRoomRepository.save(conferenceRoom);
            return "ok";
        }
        return "Uwaga, pole '" + check + "' już istnieje";
    }

    public List getAll() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom findById(String id){
        return conferenceRoomRepository.findById(id);
    }

    public void update(String id, ConferenceRoom conferenceRoom){
        conferenceRoomRepository.update(id, conferenceRoom);
    }

    public void deleteById(String id){
        conferenceRoomRepository.deleteById(id);
    }

    public void deleteAll(){
        conferenceRoomRepository.deleteAll();
    }
}