module voogasalad.crackingopen {

    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;
    requires javafx.fxml;
    requires org.json;
    requires org.codehaus.groovy;
    requires java.desktop;

    requires java.scripting;
    requires org.apache.commons.lang3;

    requires google.cloud.storage;
    requires google.oauth.client;
    requires google.auth.library.credentials;
    requires google.auth.library.oauth2.http;
    requires google.api.client;
    requires pusher.http.java;
    requires pusher.java.client;
    requires gson;

    exports auth.auth_fxml_controllers;
    exports gamedata to gson;
    exports auth.helpers to gson;

    opens GameCenter.main to javafx.fxml, javafx.graphics;
    opens GameCenter.gameData to gson;
    opens Engine.src.Controller to org.codehaus.groovy, java.scripting;

    opens Player.PlayerMain to javafx.graphics;
    opens Engine.src.EngineData to org.codehaus.groovy, java.scripting;
    opens Engine.src.ECS to org.codehaus.groovy, java.scripting;
    opens Engine.src.Controller.Events to org.codehaus.groovy, java.scripting;
    opens Engine.src.Controller.Events.AI to org.codehaus.groovy, java.scripting;
    opens Engine.src.Controller.Events.Aim to org.codehaus.groovy, java.scripting;
    opens Engine.src.Controller.Events.Health to org.codehaus.groovy, java.scripting;
    opens Engine.src.Controller.Events.Motion to org.codehaus.groovy, java.scripting;
    opens Engine.src.Timers to org.codehaus.groovy, java.scripting;
    opens Engine.src.EngineData.Components to org.codehaus.groovy, java.scripting;
    opens Engine.src.EngineData.ComponentExceptions to org.codehaus.groovy, java.scripting;
    opens gamedata to org.codehaus.groovy, java.scripting;
    opens ez_engine to org.codehaus.groovy, java.scripting;
    opens dummy_player to org.codehaus.groovy, java.scripting;

    exports auth;
    exports GameCenter;
    exports auth.screens;
    exports network_account;
    exports GameCenter.main;
    exports Engine.src.Controller;
    exports Engine.src.EngineData.Components;
    exports Engine.src.EngineData;
    exports Engine.src.ECS;
    exports Engine.src.Controller.Events;
    exports Engine.src.Controller.Events.AI;
    exports Engine.src.Controller.Events.Aim;
    exports Engine.src.Controller.Events.Health;
    exports Engine.src.Controller.Events.Motion;
    exports Engine.src.Timers;
    exports Engine.src.EngineData.ComponentExceptions;
    exports ez_engine;
    exports dummy_player;
}