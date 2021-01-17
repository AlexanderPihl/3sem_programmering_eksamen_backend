/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.SportDTO;
import dto.SportsDTO;
import entities.Sport;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import facades.SportFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
@Path("sport")
public class SportResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    private static final SportFacade FACADE = SportFacade.getSportFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSportForAll() {
        return "{\"msg\":\"Hello from Sport\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count")
    public String countSports() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Sport> query = em.createQuery("SELECT s from Sport s", Sport.class);
            List<Sport> sport = query.getResultList();
            return "[" + sport.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("all")
    public String getAllSports() throws NotFoundException {
        SportsDTO sSDTO = FACADE.getAllSports();
        return GSON.toJson(sSDTO);
    }

    @Path("{sportName}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getHobby(@PathParam("sportName") String sportName) throws NotFoundException {
        return GSON.toJson(FACADE.getSport(sportName));
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addSport(String sport) throws MissingInputException {
        SportDTO s = GSON.fromJson(sport, SportDTO.class);
        SportDTO sportDTO = FACADE.addSport(s.getSportName(), s.getDescription());
        return GSON.toJson(sportDTO);
    }

    @PUT
    @Path("update/{sportName}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateSport(@PathParam("sportName") String sportName, String description) throws MissingInputException, NotFoundException {
        SportDTO sportDTO = GSON.fromJson(description, SportDTO.class);
        sportDTO.setSportName(sportName);
        SportDTO sportNew = FACADE.editSport(sportDTO);
        return GSON.toJson(sportNew);
    }

    @DELETE
    @Path("delete/{sportName}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteSport(@PathParam("sportName") String sportName) throws NotFoundException {
        SportDTO sportDelete = FACADE.deleteSport(sportName);
        return GSON.toJson(sportDelete);
    }

}
