package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    private String message = "The following error was detected:\n";

    public void save(Organization organization) throws MyExceptions {
        String isIdExist = organizationRepository.findById(organization.getName()).getName();
        if (!isIdExist.equals("empty"))
            throw new MyExceptions(message + "Organization '" + isIdExist + "' already exists");
        organizationRepository.save(organization);
    }

    public List getAll() {
        return organizationRepository.findAll();
    }

    public Organization findById(String id) {
        return organizationRepository.findById(id);
    }

    public void update(String id, Organization organization) throws MyExceptions {
        String isOldNameExist = findById(id).getName();
        String isNewNameExist = findById(organization.getName()).getName();
        if (isOldNameExist.equals("empty"))
            throw new MyExceptions(message + "No organization '" + id + "' was found");
        if (!isNewNameExist.equals("empty") & !isNewNameExist.equals(isOldNameExist))
            throw new MyExceptions(message + "Name '" + isNewNameExist + "' already exists");
        organizationRepository.update(id, organization);
    }

    public void deleteById(String id) {
        organizationRepository.deleteById(id);
    }

    public void deleteAll() {
        organizationRepository.deleteAll();
    }
}
