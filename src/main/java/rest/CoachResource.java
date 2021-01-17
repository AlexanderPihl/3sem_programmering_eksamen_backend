/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CoachesDTO;
import dto.SportsDTO;
import entities.Coach;
import entities.Sport;
import errorhandling.NotFoundException;
import facades.CoachFacade;
import facades.SportFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author alexa
 */
@Path("coach")
public class CoachResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    private static final CoachFacade FACADE = CoachFacade.getCoachFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCoachForAll() {
        return "{\"msg\":\"Hello from Coach\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count")
    public String countCoaches() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Coach> query = em.createQuery("SELECT c from Coach c", Coach.class);
            List<Coach> coach = query.getResultList();
            return "[" + coach.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("all")
    public String getAllCoaches() throws NotFoundException {
        CoachesDTO cSDTO = FACADE.getAllCoaches();
        return GSON.toJson(cSDTO);
    }

}
