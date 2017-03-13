package com.automationrhapsody.okhttp3.utils;

import com.samskivert.mustache.Mustache;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import okhttp3.Response;

public final class JsonManager {

    private JsonManager() {
        // Utils class
    }

    public static String createPerson(String id, String firstName, String lastName, String email) {
        Map<String, String> data = new HashMap<>();
        data.put("id", id);
        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("email", email);
        return mapJsonBody("person.json", data);
    }

    public static JSONObject getJsonObject(Response response) {
        try {
            return (JSONObject) new JSONParser().parse(response.body().string());
        } catch (ParseException | IOException e) {
            return new JSONObject();
        }
    }

    public static JSONObject getJsonObject(JSONArray jsonArray, int index) {
        return (JSONObject) jsonArray.get(index);
    }

    public static JSONArray getJsonArray(Response response) {
        try {
            return (JSONArray) new JSONParser().parse(response.body().string());
        } catch (ParseException | IOException e) {
            return new JSONArray();
        }
    }

    private static String mapJsonBody(String fileName, Map data) {
        try {
            InputStream stream = JsonManager.class.getClassLoader().getResourceAsStream(fileName);
            String fileContent = IOUtils.toString(stream);
            return Mustache.compiler()
                .defaultValue("")
                .compile(fileContent)
                .execute(data);
        } catch (IOException ioe) {
            return StringUtils.EMPTY;
        }
    }
}
