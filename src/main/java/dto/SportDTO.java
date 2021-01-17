/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Sport;

/**
 *
 * @author alexa
 */
public class SportDTO {

    private String sportName;
    private String description;

    public SportDTO() {
    }

    public SportDTO(Sport sport) {
        this.description = sport.getDescription();
        this.sportName = sport.getSportName();
        this.description = sport.getDescription();
    }

    public SportDTO(String sportName, String description) {
        this.sportName = sportName;
        this.description = description;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
