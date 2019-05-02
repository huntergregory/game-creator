package auth;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RunTest extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Rectangle rect = new Rectangle(200, 200, Color.RED);
        ScrollPane s1 = new ScrollPane();
        Pane p = new Pane();
        p.getChildren().add(rect);
        p.setPrefSize(500, 500);
        s1.setPannable(true);
        s1.setPrefSize(510, 200);
        s1.setContent(p);
        Circle c = new Circle(50);
        c.setFill(Color.BLACK);
        c.relocate(300, 300);
        ((Pane)s1.getContent()).getChildren().add(c);
        s1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Scene s = new Scene(s1);
        stage.setScene(s);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}