package br.com.lowlevel.promotion_app.mapper.custom;

import br.com.lowlevel.promotion_app.data.vo.v1.PersonVO;
import br.com.lowlevel.promotion_app.data.vo.v2.PersonVOV2;
import br.com.lowlevel.promotion_app.models.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {
    public PersonVOV2 convertEntityToVo(Person person) {
        PersonVOV2 vo = new PersonVOV2();
        vo.setId(person.getId());
        vo.setAddress(person.getAddress());
        vo.setBirthday(new Date());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setGender(person.getGender());
        return vo;
    }

    public Person convertVoToEntity(PersonVOV2 personVo) {
        Person person = new Person();
        person.setId(personVo.getId());
        person.setAddress(personVo.getAddress());
//        person.setBirthday(new Date());
        person.setFirstName(personVo.getFirstName());
        person.setLastName(personVo.getLastName());
        person.setGender(personVo.getGender());
        return person;
    }
}
