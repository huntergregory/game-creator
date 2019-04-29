module voogasalad.crackingopen {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires java.scripting;
    requires org.codehaus.groovy;
    requires org.apache.commons.lang3;
    requires gson;
    requires google.cloud.storage;
    requires google.oauth.client;
    requires google.auth.library.credentials;
    requires google.auth.library.oauth2.http;
    requires google.api.client;
    requires pusher.http.java;
    requires pusher.java.client;

    exports auth.auth_fxml_controllers;
    exports gamedata to gson;
    exports auth.helpers to gson;

    opens ez_engine to org.codehaus.groovy, java.scripting;
    opens dummy_player to org.codehaus.groovy, java.scripting;

    exports ez_engine;
    exports dummy_player;
}