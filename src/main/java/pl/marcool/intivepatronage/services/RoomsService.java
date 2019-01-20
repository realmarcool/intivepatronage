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

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public RoomDTO save(RoomDTO roomDTO) throws MyExceptions {
        Room room = objectMapper.convertValue(roomDTO, Room.class);
        if (roomRepository.findById(room.getId()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"" + room.getId() + " - already exist\"}");
        if (roomRepository.findByName(room.getName()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"" + room.getName() + " - already exists\"}");
        return objectMapper.convertValue(roomRepository.save(room), RoomDTO.class);
    }

    public List<RoomDTO> getAll() {
        return StreamSupport.stream(roomRepository.findAll().spliterator(), false)
                .map(t -> objectMapper.convertValue(t, RoomDTO.class))
                .collect(Collectors.toList());
    }

    public RoomDTO findById(String id) throws MyExceptions {
        return objectMapper.convertValue(roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "{\"Error\":\"" + id + " - not found\"}")), RoomDTO.class);
    }

    public RoomDTO update(String id, RoomDTO roomDTO) throws MyExceptions {
        Room room = objectMapper.convertValue(roomDTO, Room.class);
        if (roomRepository.findByName(room.getName()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"" + room.getName() + " - already exist\"}");
        roomRepository.deleteById(findById(id).getId());
        return objectMapper.convertValue(roomRepository.save(room), RoomDTO.class);
    }

    public String deleteById(String id) throws MyExceptions {
        roomRepository.deleteById(findById(id).getId());
        return "{\"" + id + " - deleted\"}";
    }

    public String deleteAll() {
        roomRepository.deleteAll();
        return "{\"Entire Conference Room database deleted\"}";
    }
}