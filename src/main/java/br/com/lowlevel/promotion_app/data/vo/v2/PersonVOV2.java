package br.com.lowlevel.promotion_app.data.vo.v2;

import java.io.Serializable;
import java.util.Date;

public class PersonVOV2 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String address;
    private String gender;
    private String lastName;
    private String firstName;

    private Date birthday;

    public PersonVOV2() {
    }

    public PersonVOV2(Long id, String address, String gender, String lastName, String firstName) {
        this.id = id;
        this.address = address;
        this.gender = gender;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
