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

    exports auth;
    exports gamecenter;
    exports auth.screens;
    exports Launcher.src.Initial;
    exports auth.auth_fxml_controllers;
    exports gamedata to gson;

    opens ez_engine to org.codehaus.groovy, java.scripting;
    opens dummy_player to org.codehaus.groovy, java.scripting;

    exports ez_engine;
    exports dummy_player;
}