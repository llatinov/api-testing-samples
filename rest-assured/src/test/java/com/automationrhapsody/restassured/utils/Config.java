package com.automationrhapsody.restassured.utils;

import org.apache.commons.lang3.StringUtils;

public final class Config {

    private static final String ENDPOINT_GET_ALL = "/person/all";
    private static final String ENDPOINT_GET_BY_ID = "/person/get/";
    private static final String ENDPOINT_SAVE = "/person/save";
    private static final String ENDPOINT_REMOVE = "/person/remove";
    private static final String HOST_PROPERTY = "personServiceHost";
    private static final String DEFAULT_HOST = "http://localhost:9000";
    private static final String HOST = resolveHost();

    public static String getEndpointGetAll() {
        return HOST + ENDPOINT_GET_ALL;
    }

    public static String getEndpointGetById(int id) {
        return HOST + ENDPOINT_GET_BY_ID + id;
    }

    public static String getEndpointSave() {
        return HOST + ENDPOINT_SAVE;
    }

    public static String getEndpointRemove() {
        return HOST + ENDPOINT_REMOVE;
    }

    private static String resolveHost() {
        String host = System.getProperty(HOST_PROPERTY);
        return StringUtils.isNotBlank(host) ? host : DEFAULT_HOST;
    }
}
