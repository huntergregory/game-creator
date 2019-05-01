module voogasalad.crackingopen {

    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;
    requires javafx.fxml;
    requires org.json;
    requires gson;
    requires org.codehaus.groovy;
    requires java.desktop;

    opens GameCenter.main to javafx.fxml, javafx.graphics;
    opens GameCenter.gameData to gson;
    opens Engine to org.codehaus.groovy, java.scripting;

    exports auth.screens;
    exports auth.auth_fxml_controllers;
    exports network_account;
    exports GameCenter.main;
    exports Engine.src.Controller;
    exports Engine.src.EngineData.Components;
    exports Engine.src.EngineData;
}

