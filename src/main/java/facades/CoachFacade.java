/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.CoachesDTO;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alexa
 */
public class CoachFacade {

    private static EntityManagerFactory emf;
    private static CoachFacade instance;

    private CoachFacade() {
    }

    public static CoachFacade getCoachFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CoachFacade();
        }
        return instance;
    }

    public long getCoachCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long coachCount = (long) em.createQuery("SELECT COUNT(c) FROM Coach c").getSingleResult();
            return coachCount;
        } finally {
            em.close();
        }
    }

    public CoachesDTO getAllCoaches() throws NotFoundException {

        EntityManager em = emf.createEntityManager();
        CoachesDTO cSDTO;
        try {
            cSDTO = new CoachesDTO(em.createQuery("SELECT c FROM Coach c").getResultList());
        } catch (Exception e) {
            throw new NotFoundException("No connection to the database");
        } finally {
            em.close();
        }
        return cSDTO;
    }
    
    
}
