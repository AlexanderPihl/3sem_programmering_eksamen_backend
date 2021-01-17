/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.HobbiesDTO;
import dto.HobbyDTO;
import dto.SportTeamDTO;
import dto.SportTeamsDTO;
import entities.Hobby;
import entities.SportTeam;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import facades.SportTeamFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author alexa
 */
@Path("sportTeam")
public class SportTeamResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    private static final SportTeamFacade FACADE = SportTeamFacade.getSportTeamFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSportTeamForAll() {
        return "{\"msg\":\"Hello from SportTeam\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count")
    public String countSportTeam() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<SportTeam> query = em.createQuery("SELECT s from SportTeam s", SportTeam.class);
            List<SportTeam> sportTeam = query.getResultList();
            return "[" + sportTeam.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("all")
    public String getAllSportTeam() throws NotFoundException {
        SportTeamsDTO sTsDTO = FACADE.getAllSportTeam();
        return GSON.toJson(sTsDTO);
    }

    @Path("{teamName}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getSportTeam(@PathParam("teamName") String teamName) throws NotFoundException {
        return GSON.toJson(FACADE.getSportTeam(teamName));
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addSportTeam(String sportTeam) throws MissingInputException {
        SportTeamDTO s = GSON.fromJson(sportTeam, SportTeamDTO.class);
        SportTeamDTO sportTeamDTO = FACADE.addSportTeam(s.getTeamName(), s.getPricePerYear(), s.getMinAge(), s.getMaxAge());
        return GSON.toJson(sportTeamDTO);
    }

    @PUT
    @Path("addSportTeam/{sportName}/{teamName}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addSportTeamToSport(@PathParam("sportName") String sportName, @PathParam("teamName") String teamName) throws NotFoundException {

        FACADE.addSportTeamToSport(sportName, teamName);

        return Response.status(Response.Status.OK).entity("Team updated OK").build();
    }

}
