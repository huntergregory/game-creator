package auth.pagination;

import auth.Callback;
import auth.UIElement;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PaginationUIElement implements UIElement {
    private BorderPane mainView;
    private PaginationDots dots;
    private int index = 0, numPages = 1; // Always start with 1 page
    private Arrow leftArrow;
    private Arrow rightArrow;
    private List<Node> pages;
    private String ID;
    private Callback onPageChanged;

    public PaginationUIElement(Node dynamicView, Callback onPageChanged, String ID) {
        leftArrow = new LeftArrow();
        rightArrow = new RightArrow();
        leftArrow.onClick((o) -> goBack());
        rightArrow.onClick((o) -> goForwards());
        pages = new ArrayList<>();
        pages.add(dynamicView);
        dots = new PaginationDots(this);
        mainView = new BorderPane();
        mainView.setCenter(dynamicView);
        mainView.setLeft(leftArrow.getView());
        BorderPane.setAlignment(leftArrow.getView(), Pos.CENTER);
        mainView.setRight(rightArrow.getView());
        BorderPane.setAlignment(rightArrow.getView(), Pos.CENTER);
        mainView.setBottom(dots.getView());
        BorderPane.setAlignment(dots.getView(), Pos.CENTER);
        this.ID = ID;
        this.onPageChanged = onPageChanged;
    }

    public void goToPage(int newIndex) {
        if (newIndex < numPages && newIndex > -1) {
            mainView.setCenter(pages.get(newIndex));
            dots.setIndex(newIndex);
            onPageChanged.onCallback(newIndex);
            index = newIndex;
        }
    }

    private void goForwards() {
        if (index < numPages) {
            mainView.setCenter(pages.get(++index));
            dots.setIndex(index);
            onPageChanged.onCallback(index);
        }
    }

    private void goBack() {
        if (index > 0) {
            mainView.setCenter(pages.get(--index));
            dots.setIndex(index);
            onPageChanged.onCallback(index);
        }
    }

    public void addPage(Node pageView) {
        pages.add(pageView);
        ++numPages;
        dots.increment();

        mainView.setCenter(pages.get(numPages-1));
        onPageChanged.onCallback(numPages-1);
        index = numPages-1;
    }

    public void removePage(int index) {
        pages.remove(index);
        numPages--;
        dots.decrement();
        if (index == numPages) {
            goBack();
        }
    }

    @Override
    public Node getView() {
        return mainView;
    }

    @Override
    public String getID() {
        return ID;
    }
}
