/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HobbyDTO;
import dto.SportDTO;
import dto.SportsDTO;
import entities.Hobby;
import entities.Sport;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alexa
 */
public class SportFacade {

    private static EntityManagerFactory emf;
    private static SportFacade instance;

    private SportFacade() {
    }

    public static SportFacade getSportFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SportFacade();
        }
        return instance;
    }

    public long getSportCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long sportCount = (long) em.createQuery("SELECT COUNT(s) FROM Sport s").getSingleResult();
            return sportCount;
        } finally {
            em.close();
        }
    }

    public SportsDTO getAllSports() throws NotFoundException {

        EntityManager em = emf.createEntityManager();
        SportsDTO sSDTO;
        try {
            sSDTO = new SportsDTO(em.createQuery("SELECT s FROM Sport s").getResultList());
        } catch (Exception e) {
            throw new NotFoundException("No connection to the database");
        } finally {
            em.close();
        }
        return sSDTO;
    }

    public SportDTO getSport(String sportName) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Sport sport = em.find(Sport.class, sportName);
        if (sport == null) {
            throw new NotFoundException("No hobby with provided sport name found");
        } else {
            try {
                return new SportDTO(sport);
            } finally {
                em.close();
            }
        }
    }

    public SportDTO addSport(String sportName, String description) throws MissingInputException {
        if (isNameInValid(sportName, description)) {
            throw new MissingInputException("Description and/or name is missing");
        }
        EntityManager em = emf.createEntityManager();
        Sport sport = new Sport(sportName, description);
        try {
            em.getTransaction().begin();
            em.persist(sport);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new SportDTO(sport);
    }

    public SportDTO editSport(SportDTO s) throws MissingInputException, NotFoundException {
        if (isNameInValid(s.getSportName(), s.getDescription())) {
            throw new MissingInputException("Description and/or name is missing");
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Sport sport = em.find(Sport.class, s.getSportName());
            if (sport == null) {
                throw new NotFoundException("No hobby with provided sport name found");
            } else {
                sport.setDescription(s.getDescription());
            }
            em.getTransaction().commit();
            return new SportDTO(sport);
        } finally {
            em.close();
        }
    }

    public SportDTO deleteSport(String sportName) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Sport sport = em.find(Sport.class, sportName);
        if (sport == null) {
            throw new NotFoundException("Could not delete, provided sport does not exist");
        } else {
            try {
                em.getTransaction().begin();
                em.remove(sport);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new SportDTO(sport);
        }
    }

    private static boolean isNameInValid(String sportName, String description) {
        return (sportName.length() == 0) || (description.length() == 0);
    }

}
