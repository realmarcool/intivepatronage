package pl.marcool.intivepatronage.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.models.dto.OrganizationDTO;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrganizationsService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public OrganizationDTO save(OrganizationDTO organizationDTO) throws IllegalArgumentException {
        Organization organization = objectMapper.convertValue(organizationDTO, Organization.class);
        if (organizationRepository.findById(organization.getName()).isPresent())
            throw new IllegalArgumentException(organization.getName() + " - already exist");
        return objectMapper.convertValue(organizationRepository.save(organization), OrganizationDTO.class);
    }

    public List<OrganizationDTO> getAll() {
        return StreamSupport.stream(organizationRepository.findAll().spliterator(), false)
                .map(t -> objectMapper.convertValue(t, OrganizationDTO.class))
                .collect(Collectors.toList());
    }

    public OrganizationDTO findById(String id) throws IllegalArgumentException {
        return objectMapper.convertValue(organizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " - not found")), OrganizationDTO.class);
    }

    public OrganizationDTO update(String id, OrganizationDTO organizationDTO) throws IllegalArgumentException {
        Organization organization = objectMapper.convertValue(organizationDTO, Organization.class);
        if (organizationRepository.findById(organization.getName()).isPresent())
            throw new IllegalArgumentException(organization.getName() + " - already exist");
        organizationRepository.deleteById(findById(id).getName());
        return objectMapper.convertValue(organizationRepository.save(organization), OrganizationDTO.class);
    }

    public String deleteById(String id) throws IllegalArgumentException {
        organizationRepository.deleteById(findById(id).getName());
        return "{\"" + id + " - deleted\"}";
    }

    public String deleteAll() {
        organizationRepository.deleteAll();
        return "{\"Entire Organization database deleted\"}";
    }
}