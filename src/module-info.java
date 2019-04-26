module voogasalad.crackingopen {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;
    requires javafx.fxml;
    requires org.json;
    requires gson;

    opens Launcher to javafx.fxml;
    opens GameCenter.main to javafx.fxml;

    exports auth.screens;
    exports Launcher;
    exports auth.auth_fxml_controllers;
    exports network_account;
    exports GameCenter.main;
}

