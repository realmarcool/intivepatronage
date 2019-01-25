package pl.marcool.intivepatronage.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Room;
import pl.marcool.intivepatronage.models.dto.RoomDTO;
import pl.marcool.intivepatronage.repositores.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoomsService {

    private RoomRepository roomRepository;
    private ObjectMapper objectMapper;

    public RoomsService(RoomRepository roomRepository, ObjectMapper objectMapper){
        this.roomRepository = roomRepository;
        this.objectMapper = objectMapper;
    }

    public RoomDTO save(RoomDTO roomDTO) throws IllegalArgumentException {
        var room = objectMapper.convertValue(roomDTO, Room.class);
        if (roomRepository.findById(room.getId()).isPresent()) {
            throw new IllegalArgumentException(room.getId() + " - already exist");
        }
        if (roomRepository.findByName(room.getName()).isPresent()) {
            throw new IllegalArgumentException(room.getName() + " - already exists");
        }
        return objectMapper.convertValue(roomRepository.save(room), RoomDTO.class);
    }

    public List<RoomDTO> getAll() {
        return StreamSupport.stream(roomRepository.findAll().spliterator(), false)
                .map(t -> objectMapper.convertValue(t, RoomDTO.class))
                .collect(Collectors.toList());
    }

    public RoomDTO findById(String id) throws IllegalArgumentException {
        return objectMapper.convertValue(roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " - not found")), RoomDTO.class);
    }

    public RoomDTO update(String id, RoomDTO roomDTO) throws IllegalArgumentException {
        var room = objectMapper.convertValue(roomDTO, Room.class);
        if (roomRepository.findByName(room.getName()).isPresent()) {
            throw new IllegalArgumentException(room.getName() + " - already exist");
        }
        roomRepository.deleteById(findById(id).getId());
        return objectMapper.convertValue(roomRepository.save(room), RoomDTO.class);
    }

    public void deleteById(String id) throws IllegalArgumentException {
        roomRepository.deleteById(findById(id).getId());
    }

    public void deleteAll() {
        roomRepository.deleteAll();
    }
}