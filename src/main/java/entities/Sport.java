/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexa
 */
@Entity
@Table(name = "sport")
@NamedQuery(name = "Sport.deleteAllRows", query = "DELETE from Sport")
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sportName", length = 50)
    private String sportName;

    @Column(name = "description", length = 135)
    private String description;

    @JoinColumn(name = "sportTeam")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sport")
    private List<SportTeam> sportTeamsList;

    public Sport() {
    }

    public Sport(String sportName, String description) {
        this.sportName = sportName;
        this.description = description;
        this.sportTeamsList = new ArrayList<>();
    }

    public void addSportTeams(SportTeam sportTeams) {
        if (sportTeams != null) {
            sportTeamsList.add(sportTeams);
            sportTeams.setSport(this);
        }
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

    public List<SportTeam> getSportTeams() {
        return sportTeamsList;
    }

}
