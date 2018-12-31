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
        //Sprawdzenie czy nazwa jest już użyta
        String checkIfNameExist = findByName(conferenceRoom.getName()).getName();
        //Sprawdzenie czy Id jest już użyte
        String checkIfIdExist = findById(conferenceRoom.getId()).getId();
        String errorMessage = "";
        if(!checkIfIdExist.equals("pusty")) errorMessage = "UWAGA! BŁĄD:\nId: " + conferenceRoom.getId() + "' jest zajęte";
        if(!checkIfNameExist.equals("pusty")) errorMessage = "UWAGA! BŁĄD:\nNazwa: '" + conferenceRoom.getName() + "' jest zajęta";
        if (checkIfIdExist.equals("pusty")&checkIfNameExist.equals("pusty")) {
            conferenceRoomRepository.save(conferenceRoom);
            return "ok";
        }
        return errorMessage;
    }

    public List getAll() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom findById(String id){
        return conferenceRoomRepository.findById(id);
    }

    public String update(String id, ConferenceRoom conferenceRoom) {

        //Wyszukaj stare Id
        String checkOldId = findById(id).getId();
        //Wyszukaj nowe Id
        String checkNewId = findById(conferenceRoom.getId()).getId();
        //Wyszukaj nową nazwę
        ConferenceRoom checkNewName = findByName(conferenceRoom.getName());
        //Ustaw TRUE jeżeli nowe Id jest już używane przez inną salę konferencyjną
        boolean checkValidityOfId = (!checkNewId.equals("pusty"))&(!checkOldId.equals(checkNewId));
        boolean checkValidityOfName = false;
        //Ustaw TRUE jeżeli nowa nazwa jest już używana przez inną salę konferencyjną
        if (!checkNewName.getName().equals("pusty")) {
            if (!checkNewName.getId().equals(id)) checkValidityOfName = true;}
        String errorMessage = "";

        if (checkValidityOfId) errorMessage = "UWAGA! BŁĄD:\nId: '" + checkNewId + "' jest już zajęte!";
        if (checkValidityOfName) errorMessage = "UWAGA! BŁĄD:\nNazwa: '" + conferenceRoom.getName() + "' jest już zajęta!";
        if (checkOldId.equals("pusty")) return "UWAGA! BŁĄD:\nNie znaleziono Id: '" + checkOldId;
        if (!checkValidityOfId & !checkValidityOfName) {
            conferenceRoomRepository.update(id, conferenceRoom);
            return "ok";
        }
        else return errorMessage;
    }

    public void deleteById(String id){
        conferenceRoomRepository.deleteById(id);
    }

    public void deleteAll(){
        conferenceRoomRepository.deleteAll();
    }

    public ConferenceRoom findByName(String name){
        return conferenceRoomRepository.findByName(name);
    }

}