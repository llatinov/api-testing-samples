package com.automationrhapsody.jersey2;

import com.automationrhapsody.jersey2.model.Person;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PersonServiceGetTest extends AbstractBaseTest {

    private static final int ID = 1;
    private static Person personUnderTest;

    @BeforeClass
    public static void setUp() {
        personUnderTest = CLIENT.get(ID);
    }

    @Test
    public void testGet_Id() {
        assertThat(personUnderTest.getId(), is(ID));
    }

    @Test
    public void testGet_FirstName() {
        assertThat(personUnderTest.getFirstName(), is("FN1"));
    }

    @Test
    public void testGet_LastName() {
        assertThat(personUnderTest.getLastName(), is("LN1"));
    }

    @Test
    public void testGet_Email() {
        assertThat(personUnderTest.getEmail(), is("email1@email.na"));
    }
}
