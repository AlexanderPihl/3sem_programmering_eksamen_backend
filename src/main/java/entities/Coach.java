/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
@Table(name = "coach")
@NamedQuery(name = "Coach.deleteAllRows", query = "DELETE from Coach")
public class Coach implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public Coach() {
    }

    public Coach(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
//    Coach outcommented because of all currrent tests and setupTestUsers will fail if implementet.
//    @JoinColumn(name = "coachTeam")
//    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "coach")
//    private List<SportTeam> coachTeamsList;
//
//    public void addSportTeams(SportTeam SportTeam) {
//        if (SportTeam != null) {
//            coachTeamsList.add(SportTeam);
//            SportTeam.setCoach(this);
//        }
//    }
//    
//        public List<SportTeam> getSportTeams() {
//        return coachTeamsList;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
