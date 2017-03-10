package com.automationrhapsody.okhttp3.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonServiceHttpClient {
    private static final MediaType JSON = MediaType.parse("application/json");
    private static final String ENDPOINT_GET_ALL = "/person/all";
    private static final String ENDPOINT_GET_BY_ID = "/person/get/";
    private static final String ENDPOINT_SAVE = "/person/save";
    private static final String ENDPOINT_REMOVE = "/person/remove";
    private static final String HOST_PROPERTY = "personServiceHost";
    private static final String DEFAULT_HOST = "http://localhost:9000";

    OkHttpClient client = new OkHttpClient();

    public Response getAll() {
        return getRequest(ENDPOINT_GET_ALL);
    }

    public Response get(int id) {
        return getRequest(ENDPOINT_GET_BY_ID + id);
    }

    public Response save(String person) {
        try {
            RequestBody body = RequestBody.create(JSON, person);
            Request request = new Request.Builder()
                .url(resolveHost() + ENDPOINT_SAVE)
                .method("POST", body)
                .build();
            return client.newCall(request).execute();
        } catch (IOException e) {
            return new Response.Builder().build();
        }
    }

    public Response remove() {
        return getRequest(ENDPOINT_REMOVE);
    }

    private Response getRequest(String path) {
        try {
            Request request = new Request.Builder()
                .url(resolveHost() + path)
                .get()
                .build();
            return client.newCall(request).execute();
        } catch (IOException e) {
            return new Response.Builder().build();
        }
    }

    private String resolveHost() {
        String host = System.getProperty(HOST_PROPERTY);
        return StringUtils.isNotBlank(host) ? host : DEFAULT_HOST;
    }
}
