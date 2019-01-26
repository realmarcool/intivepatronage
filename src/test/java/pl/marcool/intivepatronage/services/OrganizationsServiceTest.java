package pl.marcool.intivepatronage.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marcool.intivepatronage.models.dto.OrganizationDTO;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrganizationsServiceTest {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private OrganizationsService organizationsService;

    private final OrganizationDTO organization1 = new OrganizationDTO();
    private final OrganizationDTO organization2 = new OrganizationDTO();

    @Before
    public void setUp() {
        organizationsService = new OrganizationsService(organizationRepository, objectMapper);
        organization1.setName("Organization1");
        organizationsService.save(organization1);
    }

    @Test
    public void save() {
        organization2.setName("Org2");
        organizationsService.save(organization2);
        assertEquals(2, organizationsService.getAll().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectNameSave(){
        organization2.setName("");
        organizationsService.save(organization1);
    }

    @Test
    public void getAll() {
        assertEquals(1, organizationsService.getAll().size());
    }

    @Test
    public void findById() {
        assertEquals(organizationsService.findById(organization1.getName()).getName(), organization1.getName());
    }

    @Test
    public void update() {
        organization1.setName("Organization2");
        organizationsService.update("Organization1", organization1);
        assertEquals("Organization2", organizationsService.findById("Organization2").getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectUpdate(){
        organization2.setName("Organization2");
        organizationsService.save(organization2);
        organizationsService.update("Organization1", organization2);
    }

    @Test
    public void deleteById() {
        organizationsService.deleteById(organization1.getName());
        assertEquals(0, organizationsService.getAll().size());
    }

    @Test
    public void deleteAll() {
        organizationsService.deleteAll();
        assertEquals(0, organizationsService.getAll().size());
    }
}