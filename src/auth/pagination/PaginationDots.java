package auth.pagination;

import auth.Callback;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

import static auth.Dimensions.PAGINATION_DOT_SIZE;

public class PaginationDots {
    private HBox mainView;
    private List<Circle> dots;
    private int index;
    private PaginationUIElement context;
    public PaginationDots(PaginationUIElement context) {
        this.context = context;
        dots = new ArrayList<>();
        var dot = new Circle();
        dot.setRadius(PAGINATION_DOT_SIZE);
        dot.setFill(Color.WHITE);
        dot.setStroke(Color.WHITE);
        dots.add(dot);
        dot.setCursor(Cursor.HAND);
        dot.setOnMouseClicked(e -> context.goToPage(dots.indexOf(dot)));
        mainView = new HBox(5);
        mainView.getChildren().add(dots.get(0));
        mainView.setAlignment(Pos.CENTER);
        this.index = 0;
    }
    public HBox getView() {
        return mainView;
    }
    public void increment() {
        var latest = new Circle();
        latest.setRadius(PAGINATION_DOT_SIZE);
        latest.setFill(Color.WHITE);
        latest.setStroke(Color.WHITE);
        latest.setCursor(Cursor.HAND);
        latest.setOnMouseClicked(e -> context.goToPage(dots.indexOf(latest)));
        dots.add(latest);
        mainView.getChildren().add(latest);
        index = dots.size()-1;
        resetAllBut(index);
    }
    public void decrement() {
        mainView.getChildren().remove(dots.size() - 1);
        dots.remove(dots.size() - 1);
        index = dots.size()-1;
        resetAllBut(index);
    }
    public void setIndex(int index) {
        resetAllBut(index);
        dots.get(index).setFill(Color.WHITE);
        this.index = index;
    }

    private void resetAllBut(int index) {
        for(int i = 0; i < dots.size(); i++) {
            if (i != index)
                dots.get(i).setFill(Color.TRANSPARENT);
        }
    }
}
