/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Sport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexa
 */
public class SportsDTO {
    
        List<SportDTO> all = new ArrayList();

    public SportsDTO(List<Sport> sportsEntities) {
        sportsEntities.forEach((s) -> {
            all.add(new SportDTO(s));
        });
    }

    public List<SportDTO> getAll() {
        return all;
    }

    public void setAll(List<SportDTO> all) {
        this.all = all;
    }
    
    
    
}
