package com.automationrhapsody.restassured;

import com.automationrhapsody.restassured.model.Person;
import com.automationrhapsody.restassured.utils.Config;
import com.jayway.restassured.http.ContentType;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class PersonServiceTest {

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
    public void testGetAll() {
        List<String> ids = when().get(Config.getEndpointGetAll())
            .then()
            .extract().body().jsonPath().get("id");

        assertThat(ids, is(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    public void testAllOperations() {
        when().get(Config.getEndpointGetAll())
            .then()
            .statusCode(200)
            .body("id", hasSize(4))
            .body("id", hasItems(1, 2, 3, 4));

        given().contentType(ContentType.fromContentType("application/json")).body(person)
            .when().post(Config.getEndpointSave())
            .then()
            .body(is("Added Person with id=123"));

        Person actual = when().get(Config.getEndpointGetById(123))
            .then().extract().body().as(Person.class);
        assertThat(actual.toString(), is(person.toString()));

        when().get(Config.getEndpointGetById(123))
            .then().body("id", is(person.getId()));

        when().get(Config.getEndpointGetAll())
            .then()
            .body("id", hasSize(5));

        when().get(Config.getEndpointRemove())
            .then().body(is("Last person remove. Total count: 4"));

        when().get(Config.getEndpointGetAll())
            .then()
            .body("id", hasSize(4));
    }
}
