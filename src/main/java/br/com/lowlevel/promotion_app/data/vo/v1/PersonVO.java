package br.com.lowlevel.promotion_app.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "first_name", "last_name", "address", "gender"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long key;
    @NotEmpty
    private String address;
    private String gender;
    @JsonProperty("last_name")
    private String lastName;
    @NotEmpty(message = "Nome obrigatório!")
    @JsonProperty("first_name")
    private String firstName;

    public PersonVO() {
    }

    public PersonVO(Long key, String address, String gender, String lastName, String firstName) {
        this.key = key;
        this.address = address;
        this.gender = gender;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
