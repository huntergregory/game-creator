package auth.screens;

import auth.RunAuth;
import javafx.stage.Stage;
import network_account.UserIdentity;

public abstract class Screen {
    public abstract Stage createScreen(Stage s, RunAuth a);
}
