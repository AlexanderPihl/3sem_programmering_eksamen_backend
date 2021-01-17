/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
import entities.SportTeam;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexa
 */
public class SportTeamsDTO {
        List<SportTeamDTO> all = new ArrayList();

    public SportTeamsDTO(List<SportTeam> sportTeamsEntities) {
        sportTeamsEntities.forEach((sT) -> {
            all.add(new SportTeamDTO(sT));
        });
    }

    public List<SportTeamDTO> getAll() {
        return all;
    }

    public void setAll(List<SportTeamDTO> all) {
        this.all = all;
    }
    
    
}
