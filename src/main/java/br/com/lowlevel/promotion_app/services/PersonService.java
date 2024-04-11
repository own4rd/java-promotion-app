package br.com.lowlevel.promotion_app.services;


import br.com.lowlevel.promotion_app.data.vo.v1.PersonVO;
import br.com.lowlevel.promotion_app.exceptions.ResourceNotFoundException;
import br.com.lowlevel.promotion_app.mapper.PersonMapper;
import br.com.lowlevel.promotion_app.models.Person;
import br.com.lowlevel.promotion_app.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonVO> findAll() {
        logger.info("Find all people");
        List<Person> result = personRepository.findAll();
        return result.stream().map(PersonMapper.INSTANCE::personToPersonVO).toList();
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
        return PersonMapper.INSTANCE.personToPersonVO(person);
    }

    public PersonVO create(PersonVO personVO) {
        logger.info("Creating one person!");
        Person person = PersonMapper.INSTANCE.personVOToPerson(personVO);
        Person savedPerson = personRepository.save(person);
        return PersonMapper.INSTANCE.personToPersonVO(savedPerson);
    }

    public PersonVO update(PersonVO personVO) {
        logger.info("Updating one person!");
        Person person = PersonMapper.INSTANCE.personVOToPerson(personVO);
        var entity =  personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
        Person updatedPerson = personRepository.save(entity);
        return PersonMapper.INSTANCE.personToPersonVO(updatedPerson);
    }

    public void delete(Long id) {
        logger.info("Deleting one person");
        var entity =  personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
        personRepository.delete(entity);
    }
}
