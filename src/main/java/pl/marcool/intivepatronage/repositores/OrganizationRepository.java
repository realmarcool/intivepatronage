package pl.marcool.intivepatronage.repositores;

import org.springframework.stereotype.Repository;
import pl.marcool.intivepatronage.models.Organization;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrganizationRepository {

    Map<String, Organization> organizationList = new HashMap<>();

    public void save(Organization organization) {
        organizationList.put(organization.getName(), organization);
    }

    public List<Organization> findAll() {
        return new ArrayList<>(organizationList.values());
    }

    public Organization findById(String id) {
        Organization empty = new Organization();
        empty.setName("empty");
        if (organizationList.containsKey(id)) return organizationList.get(id);
        return empty;
    }

    public void update(String id, Organization organization) {
        organizationList.remove(id);
        organizationList.put(organization.getName(), organization);
    }

    public void deleteById(String id) {
        organizationList.remove(id);
    }

    public void deleteAll() {
        organizationList.clear();
    }
}
