/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.SportTeamDTO;
import dto.SportTeamsDTO;
import entities.SportTeam;
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

/**
 *
 * @author alexa
 */
@Disabled
public class SportTeamFacadeTest {

    private static EntityManagerFactory emf;
    //private static FacadeExample facade;
    private static SportTeamFacade facade;
    private SportTeam sT1, sT2, sT3;

    public SportTeamFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = SportTeamFacade.getSportTeamFacade(emf);
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

            em.getTransaction().begin();

            em.createQuery("delete from SportTeam").executeUpdate();
            em.createQuery("delete from Sport").executeUpdate();

            sT1 = new SportTeam("Lions", "120", "12", "15");
            sT2 = new SportTeam("Dolphins", "300", "15", "20");
            sT3 = new SportTeam("Bulls", "200", "20", "25");

            em.persist(sT1);
            em.persist(sT2);
            em.persist(sT3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

//    @Disabled
//    @Test
//    public void testGetSportTeamCount() {
//        assertEquals(3, facade.getSportTeamCount(), "Expects three rows in the database");
//    }
    @Test
    public void testGetSportById() throws Exception {

        SportTeamDTO sportTeamDTO = facade.getSportTeam(sT1.getTeamName());

        assertEquals(sT1.getTeamName(), sportTeamDTO.getTeamName());
    }

    @Test
    public void testGetAllSportTeams() throws Exception {
        SportTeamsDTO sportTeamsDTO = facade.getAllSportTeam();
        List<SportTeamDTO> list = sportTeamsDTO.getAll();
        assertThat(list, everyItem(Matchers.hasProperty("teamName")));
        assertThat(list, Matchers.hasItems(Matchers.<SportTeamDTO>hasProperty("teamName", is("Lions")),
                Matchers.<SportTeamDTO>hasProperty("pricePerYear", is("120")),
                Matchers.<SportTeamDTO>hasProperty("minAge", is("12")),
                Matchers.<SportTeamDTO>hasProperty("maxAge", is("15"))
        ));
    }

    @Test
    public void testAddSportTeam() throws Exception {
        EntityManager em = emf.createEntityManager();;
        String teamName = "TestTeam";
        String pricePerYear = "Dette er en sport tester";
        String minAge = "10";
        String maxAge = "12";

        facade.addSportTeam(teamName, pricePerYear, minAge, maxAge);

        try {
            em.getTransaction().begin();

            em.find(SportTeam.class, teamName);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testEditSportTeam() throws Exception {
        EntityManager em = emf.createEntityManager();

        String teamName = "Lions";
        String pricePerYear = "120";
        String minAge = "10";
        String maxAge = "12";

        sT1.setTeamName(teamName);
        sT1.setPricePerYear(pricePerYear);
        sT1.setMinAge(minAge);
        sT1.setMaxAge(maxAge);

        SportTeamDTO sportTeamDTO = new SportTeamDTO(sT1);

        facade.editSportTeam(sportTeamDTO);

        try {
            em.getTransaction().begin();
            em.merge(sT1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        assertEquals(sT1.getTeamName(), "Lions");
    }

//    @Test
//    public void testDeleteSportTeam() throws Exception {
//
//        SportTeamDTO sportTeamDTO = facade.deleteSportTeam(sT1.getTeamName());
//
//        assertEquals(2, facade.getSportTeamCount());
//    }

}
