module voogasalad.crackingopen {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;
    requires javafx.fxml;
    requires org.json;
    requires org.apache.commons.lang3;
    requires java.scripting;
    requires org.codehaus.groovy;

    exports auth.screens;
    exports Launcher.src.Initial;
    exports auth.auth_fxml_controllers;
    exports gamecenter;
}