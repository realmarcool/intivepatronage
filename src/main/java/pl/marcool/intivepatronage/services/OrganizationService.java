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

    public String save(Organization organization) {
        Organization check = organizationRepository.findById(organization.getName());
        if (check.getName().equals("pusty")) {
            organizationRepository.save(organization);
            return "ok";
        }
        return "Uwaga, organizacja '" + check.getName() + "' już istnieje";
    }

    public List getAll() {
        return organizationRepository.findAll();
    }

    public Organization findById(String id){
        return organizationRepository.findById(id);
    }

    public void update(String id, Organization organization){
        organizationRepository.update(id, organization);
    }

    public void deleteById(String id){
        organizationRepository.deleteById(id);
    }

    public void deleteAll(){
        organizationRepository.deleteAll();
    }
}
