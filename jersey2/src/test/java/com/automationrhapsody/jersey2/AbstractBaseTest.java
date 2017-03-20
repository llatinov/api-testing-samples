package com.automationrhapsody.jersey2;

import com.automationrhapsody.jersey2.rules.PersonServiceJerseyClient;

import org.junit.ClassRule;

public abstract class AbstractBaseTest {

    @ClassRule
    public static final PersonServiceJerseyClient CLIENT = new PersonServiceJerseyClient();
}
