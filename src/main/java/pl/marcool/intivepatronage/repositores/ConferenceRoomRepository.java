package pl.marcool.intivepatronage.repositores;

import org.springframework.stereotype.Repository;
import pl.marcool.intivepatronage.models.ConferenceRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ConferenceRoomRepository {

    Map<String,ConferenceRoom> conferenceRoomList = new HashMap<>();

    public void save(ConferenceRoom conferenceRoom){
        conferenceRoomList.put(conferenceRoom.getId(), conferenceRoom);
    }

    public List<ConferenceRoom> findAll() {
        return new ArrayList<>(conferenceRoomList.values());
    }

    public ConferenceRoom findById(String id){
        ConferenceRoom empty = new ConferenceRoom();
        empty.setId("pusty");
        if (conferenceRoomList.containsKey(id)) return conferenceRoomList.get(id);
        return empty;
    }

    public ConferenceRoom findByName(String name){
        ConferenceRoom empty = new ConferenceRoom();
        empty.setName("pusty");
        for(ConferenceRoom checkName: conferenceRoomList.values()){
            if(checkName.getName().equals(name)) return checkName;
        }
        return empty;
    }

    public void update(String id, ConferenceRoom conferenceRoom){
        conferenceRoomList.remove(id);
        conferenceRoomList.put(conferenceRoom.getId(), conferenceRoom);
    }

    public void deleteById(String id){
        conferenceRoomList.remove(id);
    }

    public void deleteAll(){
        conferenceRoomList.clear();
    }

}