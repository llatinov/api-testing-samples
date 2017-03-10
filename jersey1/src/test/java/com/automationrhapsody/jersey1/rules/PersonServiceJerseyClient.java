package com.automationrhapsody.jersey1.rules;

import com.automationrhapsody.jersey1.model.Person;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.junit.rules.ExternalResource;

public class PersonServiceJerseyClient extends ExternalResource {

    private static final String ENDPOINT_GET_ALL = "/person/all";
    private static final String ENDPOINT_GET_BY_ID = "/person/get/";
    private static final String ENDPOINT_SAVE = "/person/save";
    private static final String ENDPOINT_REMOVE = "/person/remove";
    private static final String HOST_PROPERTY = "personServiceHost";
    private static final String DEFAULT_HOST = "http://localhost:9000";

    private Client personServiceClient;
    private WebResource personServiceResource;

    @Override
    public void before() throws Throwable {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);

        personServiceClient = Client.create(clientConfig);
        personServiceClient.setReadTimeout(30_000);
        personServiceClient.setConnectTimeout(2_000);
        personServiceClient.addFilter(new LoggingFilter());

        personServiceResource = personServiceClient.resource(resolveHost());
    }

    @Override
    public void after() {
        if (personServiceClient != null) {
            personServiceClient.destroy();
        }
    }

    public List<Person> getAll() {
        Person[] persons = personServiceResource
            .path(ENDPOINT_GET_ALL)
            .get(Person[].class);
        return Arrays.stream(persons).collect(Collectors.toList());
    }

    public Person get(int id) {
        return personServiceResource
            .path(ENDPOINT_GET_BY_ID + id)
            .get(Person.class);
    }

    public String save(Person person) {
        if (person != null) {
            return personServiceResource
                .path(ENDPOINT_SAVE)
                .type(MediaType.APPLICATION_JSON)
                .post(String.class, person);
        } else {
            return StringUtils.EMPTY;
        }
    }

    public String remove() {
        return personServiceResource
            .path(ENDPOINT_REMOVE)
            .get(String.class);
    }

    private String resolveHost() {
        String host = System.getProperty(HOST_PROPERTY);
        return StringUtils.isNotBlank(host) ? host : DEFAULT_HOST;
    }
}
