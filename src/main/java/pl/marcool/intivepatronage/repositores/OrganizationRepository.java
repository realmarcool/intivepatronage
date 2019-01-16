package pl.marcool.intivepatronage.repositores;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.marcool.intivepatronage.models.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
}
