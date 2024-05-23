package br.com.lowlevel.promotion_app.services;

import br.com.lowlevel.promotion_app.data.vo.v1.PersonVO;
import br.com.lowlevel.promotion_app.exceptions.RequiredObjectIsNullException;
import br.com.lowlevel.promotion_app.models.Person;
import br.com.lowlevel.promotion_app.repositories.PersonRepository;
import br.com.lowlevel.promotion_app.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService personService;

    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        var result = personService.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("</api/person/v1/1>"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();

        when(personRepository.findAll()).thenReturn(list);

        var people = personService.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);

        assertNotNull(personOne);
        assertNotNull(personOne.getKey());
        assertNotNull(personOne.getLinks());

        assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());

        var personFour = people.get(4);

        assertNotNull(personFour);
        assertNotNull(personFour.getKey());
        assertNotNull(personFour.getLinks());

        assertTrue(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());

        var personSeven = people.get(7);

        assertNotNull(personSeven);
        assertNotNull(personSeven.getKey());
        assertNotNull(personSeven.getLinks());

        assertTrue(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));
        assertEquals("Address Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Last Name Test7", personSeven.getLastName());
        assertEquals("Female", personSeven.getGender());

    }

    @Test
    void create() {
        Person person = input.mockEntity(1);

        Person persistedPerson = person;
        persistedPerson.setId(1L);

        PersonVO personVO = input.mockVO(1);
        personVO.setKey(1L);

        when(personRepository.save(person)).thenReturn(persistedPerson);

        var result = personService.create(personVO);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("</api/person/v1/1>"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }
    @Test
    void createWithNullPersonException() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            personService.create(null);
        });
        String expectedMessage = "It's not allowed to persist a null object.";
        String currentMessage = exception.getMessage();

        assertTrue(currentMessage.contains(expectedMessage));
    }

    @Test
    void updateWithNullPersonException() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            personService.update(null);
        });
        String expectedMessage = "It's not allowed to persist a null object.";
        String currentMessage = exception.getMessage();

        assertTrue(currentMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        Person persistedPerson = person;
        persistedPerson.setId(1L);

        PersonVO personVO = input.mockVO(1);
        personVO.setKey(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(persistedPerson);

        var result = personService.update(personVO);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("</api/person/v1/1>"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        personService.delete(1L);
    }

}