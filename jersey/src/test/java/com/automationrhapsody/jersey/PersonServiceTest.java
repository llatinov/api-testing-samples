package com.automationrhapsody.jersey;

import com.automationrhapsody.jersey.model.Person;
import com.automationrhapsody.jersey.rules.PersonServiceJerseyClient;

import org.junit.After;
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

    @After
    public void cleanUp() {
        CLIENT.remove();
    }

    @Test
    public void testSaveAndGet() {
        String saveResult = CLIENT.save(person);
        assertThat(saveResult, is("Added Person with id=123"));

        Person actual = CLIENT.get(person.getId());
        assertThat(actual.toString(), is(person.toString()));
    }
}
