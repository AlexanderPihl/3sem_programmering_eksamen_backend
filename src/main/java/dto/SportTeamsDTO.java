/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author alexa
 */
public class SportTeamsDTO {
    
    private String teamName;
    private String pricePerYear;
    private String minAge;
    private String maxAge;

    public SportTeamsDTO() {
    }

    public SportTeamsDTO(String teamName, String pricePerYear, String minAge, String maxAge) {
        this.teamName = teamName;
        this.pricePerYear = pricePerYear;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPricePerYear() {
        return pricePerYear;
    }

    public void setPricePerYear(String pricePerYear) {
        this.pricePerYear = pricePerYear;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }
    
    
}
