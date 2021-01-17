/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.SportDTO;
import dto.SportTeamDTO;
import dto.SportTeamsDTO;
import entities.Sport;
import entities.SportTeam;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alexa
 */
public class SportTeamFacade {

    private static EntityManagerFactory emf;
    private static SportTeamFacade instance;

    private SportTeamFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static SportTeamFacade getSportTeamFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SportTeamFacade();
        }
        return instance;
    }

    public long getSportTeamCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long sportTeamCount = (long) em.createQuery("SELECT COUNT(s) FROM sportTeam s").getSingleResult();
            return sportTeamCount;
        } finally {
            em.close();
        }
    }

    public SportTeamsDTO getAllSportTeam() throws NotFoundException {

        EntityManager em = emf.createEntityManager();
        SportTeamsDTO sTsDTO;
        try {
            sTsDTO = new SportTeamsDTO(em.createQuery("SELECT s FROM SportTeam s").getResultList());
        } catch (Exception e) {
            throw new NotFoundException("No connection to the database");
        } finally {
            em.close();
        }
        return sTsDTO;
    }

    public SportTeamDTO getSportTeam(String teamName) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        SportTeam sportTeam = em.find(SportTeam.class, teamName);
        if (sportTeam == null) {
            throw new NotFoundException("No hobby with provided id found");
        } else {
            try {
                return new SportTeamDTO(sportTeam);
            } finally {
                em.close();
            }
        }
    }

    public SportTeamDTO addSportTeam(String teamName, String pricePerYear, String minAge, String maxAge) throws MissingInputException {
        if (isTeamInValid(teamName, pricePerYear, minAge, maxAge)) {
            throw new MissingInputException("Description and/or name is missing");
        }
        EntityManager em = emf.createEntityManager();
        SportTeam sportTeam = new SportTeam(teamName, pricePerYear, minAge, maxAge);
        try {
            em.getTransaction().begin();
            em.persist(sportTeam);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new SportTeamDTO(sportTeam);
    }

    public void addSportTeamToSport(String sportName, String teamName) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        Sport sport = em.find(Sport.class, sportName);
        SportTeam sportTeam = em.find(SportTeam.class, teamName);

        System.out.println("SPORTTEAM   :   " + sportTeam);
        sport.addSportTeams(sportTeam);

        if (sport == null) {
            throw new NotFoundException("No person found");
        }
        try {
            em.getTransaction().begin();
            em.merge(sport);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public SportTeamDTO editSportTeam(SportTeamDTO s) throws MissingInputException, NotFoundException {
        if (isTeamInValid1(s.getTeamName(), s.getPricePerYear())) {
            throw new MissingInputException("Description and/or name is missing");
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SportTeam sportTeam = em.find(SportTeam.class, s.getTeamName());
            if (sportTeam == null) {
                throw new NotFoundException("No teamName with provided id found");
            } else {
                sportTeam.setPricePerYear(s.getPricePerYear());
                sportTeam.setMinAge(s.getMinAge());
                sportTeam.setMaxAge(s.getMaxAge());
            }
            em.getTransaction().commit();
            return new SportTeamDTO(sportTeam);
        } finally {
            em.close();
        }
    }

    public SportTeamDTO deleteSportTeam(String teamName) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        SportTeam sportTeam = em.find(SportTeam.class, teamName);
        if (sportTeam == null) {
            throw new NotFoundException("Could not delete, provided sport does not exist");
        } else {
            try {
                em.getTransaction().begin();
                em.remove(sportTeam);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new SportTeamDTO(sportTeam);
        }
    }

    private static boolean isTeamInValid(String teamName, String pricePerYear, String minAge, String maxAge) {
        return (teamName.length() == 0) || (pricePerYear.length() == 0) || (minAge.length() == 0) || (maxAge.length() == 0);
    }

    private static boolean isTeamInValid1(String teamName, String pricePerYear) {
        return (teamName.length() == 0) || (pricePerYear.length() == 0);
    }
}
