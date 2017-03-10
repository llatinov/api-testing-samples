package com.automationrhapsody.jersey1;

import com.automationrhapsody.jersey1.model.Person;
import com.automationrhapsody.jersey1.rules.PersonServiceJerseyClient;

import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PersonServiceTest {

    @ClassRule
    public static final PersonServiceJerseyClient CLIENT = new PersonServiceJerseyClient();

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

        CLIENT.remove();
        persons = CLIENT.getAll();
        assertThat(persons.size(), is(4));
    }
}
