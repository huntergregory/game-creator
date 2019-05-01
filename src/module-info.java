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

    requires java.scripting;
    requires org.apache.commons.lang3;

    opens GameCenter.main to javafx.fxml, javafx.graphics;
    opens GameCenter.gameData to gson;
    //opens Engine to org.codehaus.groovy, java.scripting;
    opens Engine.src.Controller to org.codehaus.groovy, java.scripting;

    opens Player.PlayerMain to javafx.graphics;
    opens Engine to org.codehaus.groovy, java.scripting;
    opens Engine.src.EngineData to org.codehaus.groovy, java.scripting;
    opens Engine.src.ECS to org.codehaus.groovy, java.scripting;
    opens Engine.src.Manager to org.codehaus.groovy, java.scripting;
    opens Engine.src.Manager.Events to org.codehaus.groovy, java.scripting;
    opens Engine.src.Manager.Events.AI to org.codehaus.groovy, java.scripting;
    opens Engine.src.Manager.Events.Aim to org.codehaus.groovy, java.scripting;
    opens Engine.src.Manager.Events.Health to org.codehaus.groovy, java.scripting;
    opens Engine.src.Manager.Events.Motion to org.codehaus.groovy, java.scripting;
    opens Engine.src.Timers to org.codehaus.groovy, java.scripting;
    opens Engine.src.EngineData.Components to org.codehaus.groovy, java.scripting;
    opens Engine.src.EngineData.ComponentExceptions to org.codehaus.groovy, java.scripting;
    opens gamedata to org.codehaus.groovy, java.scripting;
    opens ez_engine to org.codehaus.groovy, java.scripting;
    opens dummy_player to org.codehaus.groovy, java.scripting;

    exports auth;
    exports GameCenter;
    exports auth.screens;
    exports auth.auth_fxml_controllers;
    exports network_account;
    exports GameCenter.main;
    exports Engine.src.Controller;
    exports Engine.src.EngineData.Components;
    exports Engine.src.EngineData;
    exports Engine.src.ECS;
    exports Engine.src.Manager;
    exports Engine.src.Manager.Events;
    exports Engine.src.Manager.Events.AI;
    exports Engine.src.Manager.Events.Aim;
    exports Engine.src.Manager.Events.Health;
    exports Engine.src.Manager.Events.Motion;
    exports Engine.src.Timers;
    exports Engine.src.EngineData.ComponentExceptions;
    exports gamedata;
    exports ez_engine;
    exports dummy_player;
}