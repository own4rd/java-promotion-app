package br.com.lowlevel.promotion_app.data.vo.v1;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class PersonVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String address;
    private String gender;
    private String lastName;
    private String firstName;

    public PersonVO(Long id, String address, String gender, String lastName, String firstName) {
        this.id = id;
        this.address = address;
        this.gender = gender;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }


    public String getAddress() {
        return address;
    }


    public String getGender() {
        return gender;
    }


    public String getLastName() {
        return lastName;
    }


    public String getFirstName() {
        return firstName;
    }


}
