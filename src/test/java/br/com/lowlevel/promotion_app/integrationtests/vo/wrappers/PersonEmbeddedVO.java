package br.com.lowlevel.promotion_app.integrationtests.vo.wrappers;

import br.com.lowlevel.promotion_app.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class PersonEmbeddedVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("personVOList")
    private List<PersonVO> persons;


    public List<PersonVO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonVO> persons) {
        this.persons = persons;
    }
}
