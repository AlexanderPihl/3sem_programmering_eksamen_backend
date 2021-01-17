/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Coach;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexa
 */
public class CoachesDTO {

    List<CoachDTO> all = new ArrayList();

    public CoachesDTO(List<Coach> coachesEntities) {
        coachesEntities.forEach((c) -> {
            all.add(new CoachDTO(c));
        });
    }

    public List<CoachDTO> getAll() {
        return all;
    }

    public void setAll(List<CoachDTO> all) {
        this.all = all;
    }

}
