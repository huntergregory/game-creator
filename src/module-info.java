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

    exports Launcher.src.Initial;
    exports auth.auth_fxml_controllers;
    exports gamecenter;
    exports gamedata to gson;
}