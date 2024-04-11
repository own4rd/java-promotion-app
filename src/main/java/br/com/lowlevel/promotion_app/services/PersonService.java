package br.com.lowlevel.promotion_app.services;


import br.com.lowlevel.promotion_app.data.vo.v1.PersonVO;
import br.com.lowlevel.promotion_app.exceptions.ResourceNotFoundException;
import br.com.lowlevel.promotion_app.mapper.ModelMapperFactory;
import br.com.lowlevel.promotion_app.models.Person;
import br.com.lowlevel.promotion_app.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return ModelMapperFactory.parseListObjects(personRepository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
        return ModelMapperFactory.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO personVO) {
        logger.info("Creating one person!");
        Person person = ModelMapperFactory.parseObject(personVO, Person.class);
        return ModelMapperFactory.parseObject(personRepository.save(person), PersonVO.class);
    }

    public PersonVO update(PersonVO personVO) {
        logger.info("Updating one person!");
        Person person = ModelMapperFactory.parseObject(personVO, Person.class);
        var entity =  personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
        return ModelMapperFactory.parseObject(personRepository.save(entity), PersonVO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person");
        var entity =  personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
        personRepository.delete(entity);
    }
}
