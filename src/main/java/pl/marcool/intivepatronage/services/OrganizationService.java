package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;

import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public void save(Organization organization) throws MyExceptions {
        if (organizationRepository.findById(organization.getName()).isPresent())
            throw new MyExceptions("Organization ID:" + organization.getName() + " - already exists.");
        organizationRepository.save(organization);
    }

    public Iterable<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public Organization findById(String id) throws MyExceptions {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (organization.isEmpty()) throw new MyExceptions("Organization ID:" + id + " - not found.");
        return organization.get();
    }

    public void update(String id, Organization organization) throws MyExceptions {
        if (organizationRepository.findById(id).isEmpty())
            throw new MyExceptions("Organization ID:" + id + " - not found.");
        if (organizationRepository.findById(organization.getName())
                .stream()
                .anyMatch(cr -> !cr.getName().equals(id)))
            throw new MyExceptions("Organization ID:" + organization.getName() + " - already exists.");
        organizationRepository.deleteById(id);
        organizationRepository.save(organization);
    }

    public void deleteById(String id) throws MyExceptions {
        if (organizationRepository.findById(id).isEmpty())
            throw new MyExceptions("Organization ID:" + id + " - not found.");
        organizationRepository.deleteById(id);
    }

    public void deleteAll() {
        organizationRepository.deleteAll();
    }
}
