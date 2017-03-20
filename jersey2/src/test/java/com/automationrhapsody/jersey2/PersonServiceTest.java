package com.automationrhapsody.jersey2;

import com.automationrhapsody.jersey2.model.Person;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PersonServiceTest extends AbstractBaseTest {

    private Person person;

    @Before
    public void setUp() {
        person = new Person();
        person.setId(123);
        person.setFirstName("First Name");
        person.setLastName("Last Name");
        person.setEmail("Email");
    }

    @Test
    public void testAllOperations() {
        List<Person> persons = CLIENT.getAll();
        assertThat(persons.size(), is(4));

        String saveResult = CLIENT.save(person);
        assertThat(saveResult, is("Added Person with id=123"));

        Person actual = CLIENT.get(person.getId());
        assertThat(actual.toString(), is(person.toString()));

        persons = CLIENT.getAll();
        assertThat(persons.size(), is(5));

        String result = CLIENT.remove();
        assertThat(result, is("Last person remove. Total count: 4"));

        persons = CLIENT.getAll();
        assertThat(persons.size(), is(4));
    }
}
