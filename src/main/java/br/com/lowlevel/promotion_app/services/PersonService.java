package br.com.lowlevel.promotion_app.services;


import br.com.lowlevel.promotion_app.controllers.PersonController;
import br.com.lowlevel.promotion_app.data.vo.v1.PersonVO;
import br.com.lowlevel.promotion_app.data.vo.v2.PersonVOV2;
import br.com.lowlevel.promotion_app.exceptions.RequiredObjectIsNullException;
import br.com.lowlevel.promotion_app.exceptions.ResourceNotFoundException;
import br.com.lowlevel.promotion_app.mapper.ModelMapperFactory;
import br.com.lowlevel.promotion_app.mapper.custom.PersonMapper;
import br.com.lowlevel.promotion_app.models.Person;
import br.com.lowlevel.promotion_app.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {
    private final PersonMapper personMapper;
    private final Logger logger = Logger.getLogger(PersonService.class.getName());
    private final PersonRepository personRepository;

    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;

    public PersonService(PersonMapper personMapper, PersonRepository personRepository) {
        this.personMapper = personMapper;
        this.personRepository = personRepository;
    }

    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {

        logger.info("Finding all people!");

        var personPage = personRepository.findAll(pageable);

        var personVOsPage = personPage.map(person -> ModelMapperFactory.parseObject(person, PersonVO.class));
        personVOsPage.map(
                p -> p.add(
                        linkTo(methodOn(PersonController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(PersonController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(personVOsPage, link);
    }

    public PagedModel<EntityModel<PersonVO>> findPersonByName(String firstname, Pageable pageable) {

        logger.info("Finding all people!");

        var personPage = personRepository.findPersonsByName(firstname, pageable);

        var personVOsPage = personPage.map(person -> ModelMapperFactory.parseObject(person, PersonVO.class));
        personVOsPage.map(
                p -> p.add(
                        linkTo(methodOn(PersonController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(PersonController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(personVOsPage, link);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
        PersonVO vo = ModelMapperFactory.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO personVO) {
        if (personVO == null) {
            throw new RequiredObjectIsNullException();
        }
        logger.info("Creating one person!");
        Person person = ModelMapperFactory.parseObject(personVO, Person.class);
        var vo = ModelMapperFactory.parseObject(personRepository.save(person), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;

    }

    public PersonVO update(PersonVO personVO) {
        if (personVO == null) {
            throw new RequiredObjectIsNullException();
        }
        logger.info("Updating one person!");
        Person person = ModelMapperFactory.parseObject(personVO, Person.class);
        var entity = personRepository.findById(personVO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = ModelMapperFactory.parseObject(personRepository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
        personRepository.delete(entity);
    }

    public PersonVOV2 createV2(PersonVOV2 personVO) {
        logger.info("Creating one person with V2!");
        Person person = personMapper.convertVoToEntity(personVO);
        var vo = personMapper.convertEntityToVo(personRepository.save(person));
        return vo;
    }
}
