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
        return "UWAGA! BŁĄD:\nOrganizacja '" + check.getName() + "' już istnieje";
    }

    public List getAll() {
        return organizationRepository.findAll();
    }

    public Organization findById(String id){
        return organizationRepository.findById(id);
    }

    public String update(String id, Organization organization) {

        //Czy stara nazwa istnieje
        String checkOldName = findById(id).getName();
        //Czy nowa nazwa istnieje
        String checkNewName = findById(organization.getName()).getName();
        String errorMessage = "";

        //Jeżeli nowa nazwa jest już używana
        if (!checkNewName.equals("pusty")&!checkNewName.equals(checkOldName)) errorMessage = "UWAGA! BŁĄD:\nNazwa: '" + checkNewName + "' jest już zajęta!";
        //Jeżeli nieznaleziono organizacji
        if (checkOldName.equals("pusty")) errorMessage = "UWAGA! BŁĄD:\nNie znaleziono organizacji o nazwie: '" + id + "'";
        if ((checkNewName.equals("pusty")||checkNewName.equals(checkOldName)) & !checkOldName.equals("pusty")) {
            organizationRepository.update(id, organization);
            return "ok";
        } else return errorMessage;
    }

    public void deleteById(String id){
        organizationRepository.deleteById(id);
    }

    public void deleteAll(){
        organizationRepository.deleteAll();
    }
}
