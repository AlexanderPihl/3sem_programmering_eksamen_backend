/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Role;
import entities.Sport;
import entities.SportTeam;
import facades.PersonFacade;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static rest.PersonResourceTest.startServer;
import utils.EMF_Creator;

/**
 *
 * @author alexa
 */
public class SportResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private Sport s1, s2, s3;
    private SportTeam sT1, sT2, sT3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(emf);

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        s1 = new Sport("Basket", "boldsport med hænderne");
        s2 = new Sport("Fodbold", "boldsport med fødderne");
        s3 = new Sport("Tennis", "boldsport med ketcher");
        sT1 = new SportTeam("Bulls", "120", "12", "15");
        sT2 = new SportTeam("Tigers", "220", "15", "20");
        sT3 = new SportTeam("Dolphins", "349", "17", "21");

        s1.addSportTeams(sT3);
        s2.addSportTeams(sT2);
        s3.addSportTeams(sT1);

        try {
            em.getTransaction().begin();
            em.createQuery("delete from SportTeam").executeUpdate();
            em.createQuery("delete from Sport").executeUpdate();
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
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given()
                .when()
                .get("/info")
                .then().statusCode(200);
    }

    @Test
    public void testCount() {
        given()
                .contentType("application/json")
                .get("/info/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body(equalTo("[" + 3 + "]"));
    }

    @Test
    public void testGetInfoForAll() {
        given()
                .contentType("application/json")
                .get("/sport").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body(equalTo("{\"msg\":\"Hello from Sport\"}"));
    }

    @Test
    public void testGetFromSport() {
        given()
                .when()
                .get("sport/Basket/")
                .then().statusCode(200);
    }

}
