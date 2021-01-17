/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Coach;
import javax.persistence.Column;

/**
 *
 * @author alexa
 */
public class CoachDTO {

    private String name;
    private String email;
    private String phone;

    public CoachDTO() {
    }

    public CoachDTO(Coach coach) {
        this.name = coach.getName();
        this.email = coach.getEmail();
        this.phone = coach.getPhone();
    }

    public CoachDTO(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

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
