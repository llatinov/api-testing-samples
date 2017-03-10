package com.automationrhapsody.jersey.rules;

import com.automationrhapsody.jersey.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.rules.ExternalResource;

public class PersonServiceJerseyClient extends ExternalResource {

    private static final String ENDPOINT_GET_ALL = "/person/all";
    private static final String ENDPOINT_GET_BY_ID = "/person/get/";
    private static final String ENDPOINT_SAVE = "/person/save";
    private static final String ENDPOINT_REMOVE = "/person/remove";
    private static final String HOST_PROPERTY = "personServiceHost";
    private static final String DEFAULT_HOST = "http://localhost:9000";

    private Client personServiceClient;
    private WebTarget personServiceTarget;

    @Override
    public void before() throws Throwable {
        ClientConfig clientConfig = new ClientConfig()
            .property(ClientProperties.READ_TIMEOUT, 50000)
            .property(ClientProperties.CONNECT_TIMEOUT, 2000);

        personServiceClient = ClientBuilder
            .newClient(clientConfig)
            .register(new LoggingFilter());

        personServiceTarget = personServiceClient.target(resolveHost());
    }

    @Override
    public void after() {
        if (personServiceClient != null) {
            personServiceClient.close();
        }
    }

    public List<Person> getAll() {
        Person[] persons = personServiceTarget
            .path(ENDPOINT_GET_ALL)
            .request()
            .get()
            .readEntity(Person[].class);
        return Arrays.stream(persons).collect(Collectors.toList());
    }

    public Person get(int id) {
        return personServiceTarget
            .path(ENDPOINT_GET_BY_ID + id)
            .request()
            .get()
            .readEntity(Person.class);
    }

    public String save(Person person) {
        if (person != null) {
            return personServiceTarget
                .path(ENDPOINT_SAVE)
                .request()
                .post(Entity.json(person))
                .readEntity(String.class);
        } else {
            return StringUtils.EMPTY;
        }
    }

    public String remove() {
        return personServiceTarget
            .path(ENDPOINT_REMOVE)
            .request()
            .get()
            .readEntity(String.class);
    }

    private String resolveHost() {
        String host = System.getProperty(HOST_PROPERTY);
        return StringUtils.isNotBlank(host) ? host : DEFAULT_HOST;
    }
}
