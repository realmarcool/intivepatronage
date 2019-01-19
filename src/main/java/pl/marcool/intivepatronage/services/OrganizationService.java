package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private CheckingService checkingService;

    public Organization save(Organization organization, BindingResult bindingResult) throws MyExceptions {
        checkingService.checkBindingResult(bindingResult);
        if (organizationRepository.findById(organization.getName()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"'" + organization.getName() + "' - already exist\"}");
        return organizationRepository.save(organization);
    }

    public Iterable<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public Organization findById(String id) throws MyExceptions {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new MyExceptions(404,
                        "{\"Error\":\"'" + id + "' - not found\"}"));
    }

    public Organization update(String id, Organization organization, BindingResult bindingResult) throws MyExceptions {
        checkingService.checkBindingResult(bindingResult);
        if (organizationRepository.findById(organization.getName()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"'" + organization.getName() + "' - already exist\"}");
        organizationRepository.deleteById(findById(id).getName());
        return organizationRepository.save(organization);
    }

    public String deleteById(String id) throws MyExceptions {
        organizationRepository.deleteById(findById(id).getName());
        return "{\"'" + id + "' - deleted\"}";
    }

    public String deleteAll() {
        organizationRepository.deleteAll();
        return "{\"Entire Organization database deleted\"}";
    }
}