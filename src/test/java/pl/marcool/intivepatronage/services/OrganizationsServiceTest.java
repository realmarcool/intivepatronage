package pl.marcool.intivepatronage.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marcool.intivepatronage.models.Organization;
import pl.marcool.intivepatronage.models.dto.OrganizationDTO;
import pl.marcool.intivepatronage.models.dto.RoomDTO;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;
import pl.marcool.intivepatronage.repositores.RoomRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrganizationsServiceTest {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ObjectMapper objectMapper;

    OrganizationsService organizationsService;

    OrganizationDTO organization1 = new OrganizationDTO();
    OrganizationDTO organization2 = new OrganizationDTO();

    @Before
    public void setUp() throws Exception {
        organizationsService = new OrganizationsService(organizationRepository, objectMapper);
        organization1.setName("Organization1");
        organizationsService.save(organization1);
    }

    @Test
    public void save() {
        organization2.setName("Org2");
        organizationsService.save(organization2);
        assertTrue(organizationsService.getAll().size() == 2);
    }

    @Test
    public void getAll() {
        assertTrue(organizationsService.getAll().size() == 1);
    }

    @Test
    public void findById() {
        assertTrue(organizationsService.findById(organization1.getName()).getName().equals(organization1.getName()));
    }

    @Test
    public void update() {
        organization1.setName("Organization2");
        organizationsService.update("Organization1", organization1);
        assertTrue(organizationsService.findById("Organization2").getName().equals("Organization2"));
    }

    @Test
    public void deleteById() {
        organizationsService.deleteById(organization1.getName());
        assertTrue(organizationsService.getAll().size() == 0);
    }

    @Test
    public void deleteAll() {
        organizationsService.deleteAll();
        assertTrue(organizationsService.getAll().size() == 0);
    }
}