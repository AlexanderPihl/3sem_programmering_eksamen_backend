/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexa
 */
@Entity
@Table(name = "sportTeams")
@NamedQuery(name = "SportTeams.deleteAllRows", query = "DELETE from SportTeams")
public class SportTeams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "teamName", length = 50)
    private String teamName;
    
    @Column(name = "pricePerYear")
    private String pricePerYear;
    
    @Column(name = "minAge")
    private String minAge;

    @Column(name = "maxAge")
    private String maxAge;

    @JoinColumn(name = "sport")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Sport sport;

    public SportTeams() {
    }

    public SportTeams(String teamName, String pricePerYear, String minAge, String maxAge) {
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

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }



    
}
