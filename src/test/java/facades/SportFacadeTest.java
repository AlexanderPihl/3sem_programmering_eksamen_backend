package facades;


import dto.SportDTO;
import dto.SportsDTO;
import entities.Sport;
import facades.SportFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alexa
 */

@Disabled
public class SportFacadeTest {

    private static EntityManagerFactory emf;
    //private static FacadeExample facade;
    private static SportFacade facade;
    private Sport s1, s2, s3;

    public SportFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = SportFacade.getSportFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
//Delete existing users and roles to get a "fresh" database
            em.getTransaction().begin();
            
            em.createQuery("delete from SportTeam").executeUpdate();
            em.createQuery("delete from Sport").executeUpdate();

            s1 = new Sport("Basket", "et boldspil");
            s2 = new Sport("Svømning", "Vandsport");
            s3 = new Sport("Dart", "Bar dominans");

            em.persist(s1);
            em.persist(s2);
            em.persist(s3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    /**
     * Test of getSportCount method, of class SportFacade.
     */
    @Test
    public void testGetSportCount() {
        assertEquals(3, facade.getSportCount(), "Expects three rows in the database");
    }

    /**
     * Test of getSportByName method, of class SportFacade.
     */
    @Test
    public void testGetSportById() throws Exception {

        SportDTO sportDTO = facade.getSport(s1.getSportName());

        assertEquals(s1.getSportName(), sportDTO.getSportName());
    }

    /**
     * Test of getAllSport method, of class SportFacade.
     */
    @Test
    public void testGetAllSport() throws Exception {
        SportsDTO sportsDTO = facade.getAllSports();
        List<SportDTO> list = sportsDTO.getAll();
        assertThat(list, everyItem(Matchers.hasProperty("sportName")));
        assertThat(list, Matchers.hasItems(Matchers.<SportDTO>hasProperty("sportName", is("Basket")),
                Matchers.<SportDTO>hasProperty("description", is("et boldspil"))
        ));
    }

    /**
     * Test of addSport method, of class SportFacade.
     */
    @Test
    public void testAddSport() throws Exception {
        EntityManager em = emf.createEntityManager();;
        String sportName = "TestSport";
        String description = "Dette er en sport tester";

        facade.addSport(sportName, description);

        try {
            em.getTransaction().begin();

            em.find(Sport.class, sportName);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Test of editSport method, of class SportFacade.
     */
    @Test
    public void testEditSport() throws Exception {
        EntityManager em = emf.createEntityManager();

        String sportName = "Basket";
        String description = "Test mig";

        s1.setSportName(sportName);
        s1.setDescription(description);

        SportDTO sportDTO = new SportDTO(s1);

        facade.editSport(sportDTO);

        try {
            em.getTransaction().begin();
            em.merge(s1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        assertEquals(s1.getSportName(), "Basket");
    }

    /**
     * Test of deleteSport method, of class SportFacade.
     */
    @Test
    public void testDeleteSport() throws Exception {

        SportDTO sportDTO = facade.deleteSport(s1.getSportName());

        assertEquals(2, facade.getSportCount());
    }
}
