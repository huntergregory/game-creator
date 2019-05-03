package auth.helpers;

import auth.Callback;
import auth.UIElement;
import auth.UIElementWrapper;
import auth.auth_fxml_controllers.InsPropsController;
import auth.auth_fxml_controllers.ObjPropsController;
import auth.auth_fxml_controllers.ResPropsController;
import auth.auth_fxml_controllers.ScenePropsController;
import auth.auth_ui_components.*;
import auth.pagination.PaginationUIElement;
import auth.screens.CanvasScreen;
import gamedata.Game;
import gamedata.GameObject;
import gamedata.Instance;
import gamedata.Resource;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import uiutils.panes.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import static auth.Colors.DEFAULT_TEXT_COLOR;
import static auth.Dimensions.*;
import static auth.RunAuth.*;
import static auth.Strings.*;
import static auth.Styles.BG_STYLE;
import static auth.Styles.CANVAS_STYLE;
import static auth.auth_ui_components.ToolIcon.BG_CIRCLE_RADIUS;
import static auth.helpers.DimensionCalculator.centreVertical;
import static auth.helpers.RectangleHelpers.createScrollingRectangle;
import static auth.helpers.RectangleHelpers.createStyledRectangle;

public class ScreenHelpers {
    private static final String STYLE_SHEET = "authoring.css";
    public static final String DRAGGING_CLONE = "DRAGGING_CLONE";

    private static Color selectedShadowColor = Color.rgb(255, 255, 255, 0.75);

    public static Effect makeShadow(Color color) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(color);
        return dropShadow;
    }

    public static Effect makeShadow() {
        return makeShadow(Color.color(0.0, 0.0, 0.0, 0.25));
    }

    public static Effect makeShadowSelected() {
        return makeShadow(selectedShadowColor);
    }

    public static void initScene(CanvasScreen context, Scene scene, Group root) {
        scene.setRoot(root);
        root.setStyle(BG_STYLE);
        placePanes(context);
        placeCanvas(context);
        placeScenePagination(context);
        scene.getStylesheets().add(STYLE_SHEET);
    }

    private static void placeScenePagination(CanvasScreen context) {
        var sceneText = new Text("Scene 1");
        sceneText.setFont(bebasKaiMedium);
        sceneText.setFill(DEFAULT_TEXT_COLOR);
        var pagination = new PaginationUIElement(sceneText, (arg) -> {
            final int index = (Integer) arg[0];
            context.switchToScene(index, true);
        }, SCENE_PAGINATION);
        var pane = placeScenePaginationPane();
        var borderPane = new BorderPane();
        borderPane.setCenter(pagination.getView());
        borderPane.setMinWidth(CONSOLE_PANE_WIDTH - 24);
        borderPane.setMaxWidth(CONSOLE_PANE_WIDTH - 24);
        borderPane.setLayoutY(7);
        borderPane.setLayoutX(12);
        pane.getView().getChildren().add(borderPane);
        context.registerNewUIElement(pane);
        context.setPagination(pagination);
    }

    private static Pane placeScenePaginationPane() {
        return new TopPane(CONSOLE_HORIZONTAL_OFFSET,
                CONSOLE_PANE_WIDTH,
                SCENE_PAGINATION_HEIGHT,
                SCENE_PAGINATION_PANE_ID);
    }

    private static void placeCanvas(CanvasScreen context) {
        var canvas = createScrollingRectangle(CONSOLE_HORIZONTAL_OFFSET, CANVAS_VERTICAL_OFFSET,
                CANVAS_WIDTH, CANVAS_HEIGHT, CANVAS_STYLE);
        context.registerNewUIElement(new UIElementWrapper(canvas, CANVAS_ID));
    }

    private static void populateToolsPane(CanvasScreen context, LeftPane toolsPane) {
        var vboxParent = new VBox(5);

        try {
            JSONArray tools = new JSONArray(new Scanner(new File(TOOLS_CONFIG_FILE_PATH)).useDelimiter("\\Z").next());
            for (int i = 0; i < tools.length(); i++) {
                var tool = tools.getJSONObject(i);
                var name = tool.getString("name");
                var tooltip = tool.getString("tooltip");
                var handler = tool.getString("handler");

                vboxParent.getChildren().add(new ToolIcon(name, tooltip, callback -> {
                    try {
                        var method = ToolClickHandlers.class.getMethod(handler, CanvasScreen.class);
                        method.invoke(null, context);
                    } catch (Exception e) {
                        // Would never happen
                    }
                }).getView());
            }

            vboxParent.setLayoutX(TOOLS_PANE_WIDTH/2 - BG_CIRCLE_RADIUS - 5);
            vboxParent.setLayoutY(TOOLS_PANE_HEIGHT/2 - (tools.length()*2*BG_CIRCLE_RADIUS + (tools.length()-1)*15)/2);

            toolsPane.getView().getChildren().add(vboxParent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void populateObLibPane(CanvasScreen context, Pane objLibPane) {
        var containerPane = new BorderPane();
        containerPane.setPrefWidth(RIGHT_PANE_WIDTH - RIGHT_PANE_MARGIN/2);
        containerPane.setPrefHeight(RIGHT_PANE_HEIGHT - RIGHT_PANE_MARGIN);
        containerPane.setLayoutX(RIGHT_PANE_MARGIN/4);
        containerPane.setLayoutY(RIGHT_PANE_MARGIN/2);

        String[] titles = {OBJECTS_TITLE, IMG_RES_TITLE, AUD_RES_TITLE, COLORS_TITLE};

        var titleText = new Text(titles[0]);
        titleText.setFont(bebasKai);
        titleText.setFill(DEFAULT_TEXT_COLOR);
        containerPane.setTop(titleText);
        BorderPane.setAlignment(titleText, Pos.CENTER);

        var pagination = new PaginationUIElement(wrapInScrollView(context.getObjectGrid()), (arg) -> {
            final int index = (Integer) arg[0];
            titleText.setText(titles[index]);
        }, SCENE_PAGINATION);

        pagination.addPage(wrapInScrollView(context.getImageGrid())); // for images
        pagination.addPage(wrapInScrollView(context.getAudioGrid())); // for audio
        pagination.addPage(wrapInScrollView(context.getColorGrid())); // for colour palette
        pagination.goToPage(0); // Switch back to objects

        containerPane.setCenter(pagination.getView());

        objLibPane.getView().getChildren().addAll(containerPane);
    }

    public static void repopulatePropertiesPane(CanvasScreen context) {
        var propsPane = (javafx.scene.layout.Pane) ((VBox)context.getUIElementById(RIGHT_PANES_GROUP_ID).getView()).getChildren().get(1);
        var contentPane = (javafx.scene.layout.Pane) ((ScrollPane) ((BorderPane) propsPane.getChildren().get(0)).getCenter()).getContent();
        contentPane.getChildren().clear();
        populateConsolePane(context, (BottomPane)context.getUIElementById(CONSOLE_PANE_ID));
        try {
            if (context.currentlySelected == null) {
                // Show props for scene
                ((Text) ((BorderPane) propsPane.getChildren().get(0)).getTop()).setText(SCENE_PROPERTIES_TITLE);
                FXMLLoader loader = new FXMLLoader(ScreenHelpers.class.getResource("/properties_pane_fxml/sceneprops.fxml"));
                var fxmlPane = (javafx.scene.layout.Pane) loader.load();
                loader.<ScenePropsController>getController().initData(propsPane, context);
                contentPane.getChildren().add(fxmlPane);
            } else if (context.selectedType == GameObject.class) {
                // Show props for game object
                ((Text) ((BorderPane) propsPane.getChildren().get(0)).getTop()).setText(OBJECTS_PROPERTIES_TITLE);
                FXMLLoader loader = new FXMLLoader(ScreenHelpers.class.getResource("/properties_pane_fxml/objprops.fxml"));
                var fxmlPane = (javafx.scene.layout.Pane) loader.load();
                loader.<ObjPropsController>getController().initData(propsPane, context);
                contentPane.getChildren().add(fxmlPane);
            } else if (context.selectedType == Image.class) {
                // Show props for image resource
                ((Text) ((BorderPane) propsPane.getChildren().get(0)).getTop()).setText(IMG_RES_PROPERTIES_TITLE);
                loadResourceFXML(context, propsPane, contentPane);
            } else if (context.selectedType == AudioClip.class) {
                // Show props for audio resource
                ((Text) ((BorderPane) propsPane.getChildren().get(0)).getTop()).setText(AUD_RES_PROPERTIES_TITLE);
                loadResourceFXML(context, propsPane, contentPane);
            } else if (context.selectedType == Color.class) {
                // Show props for color resource
                ((Text) ((BorderPane) propsPane.getChildren().get(0)).getTop()).setText(COL_RES_PROPERTIES_TITLE);
                loadResourceFXML(context, propsPane, contentPane);
            } else if (context.selectedType == Instance.class) {
                // Show props for instance
                ((Text) ((BorderPane) propsPane.getChildren().get(0)).getTop()).setText(INSTANCE_PROPERTIES_TITLE);
                FXMLLoader loader = new FXMLLoader(ScreenHelpers.class.getResource("/properties_pane_fxml/insprops.fxml"));
                var fxmlPane = (javafx.scene.layout.Pane) loader.load();
                loader.<InsPropsController>getController().initData(propsPane, context);
                contentPane.getChildren().add(fxmlPane);
            }
        } catch (IOException e) {
            // shouldn't ever happen
            e.printStackTrace();
        }
    }

    private static void loadResourceFXML(CanvasScreen context, javafx.scene.layout.Pane propsPane, javafx.scene.layout.Pane contentPane) throws IOException{
        FXMLLoader loader = new FXMLLoader(ScreenHelpers.class.getResource("/properties_pane_fxml/resprops.fxml"));
        var fxmlPane = (javafx.scene.layout.Pane) loader.load();
        loader.<ResPropsController>getController().initData(propsPane, context);
        contentPane.getChildren().add(fxmlPane);
    }

    private static ScrollPane wrapInScrollView(Node v) {
        var sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setMaxHeight(60*3+15*2);
        sp.setContent(v);
        sp.setStyle("-fx-background: #333333;\n" +
                "   -fx-border-color: transparent;" +
                "-fx-background-color: #33333300;");

        return sp;
    }

    public static void initialiseGrids(CanvasScreen context) {
        initialiseObjectsGrid(context);
        initialiseImagesGrid(context);
        initialiseAudioGrid(context);
        initialiseColorGrid(context);
    }

    public static void initialiseColorGrid(CanvasScreen context) {
        if (context.getColorGrid().getChildren().size() != 0) {
            context.getColorGrid().getChildren().clear(); // Remove all the HBox's within this VBox
        }

        var row = new HBox(5);
        for (var r : context.getGame().resources) {
            if (row.getChildren().size() == 3) {
                VBox.setMargin(row, new Insets(0, 0, 0, (30)/2.0));
                context.getColorGrid().getChildren().add(row);
                row = new HBox(5);
            }
            if (r.resourceType.equals(Resource.ResourceType.COLOR_RESOURCE)) {
                var icon = new ColorIcon(getColorByID(context.getGame(), r.resourceID), r.resourceID, e -> {
                    // TODO
                    var thisIcon = (Selectable) e[0];
                    if (context.currentlySelected != null && context.currentlySelected != thisIcon) {
                        context.currentlySelected.deselect();
                        clearSelection(context);
                        repopulatePropertiesPane(context);
                    }
                    if (context.currentlySelected != thisIcon) {
                        context.currentlySelected = thisIcon;
                        thisIcon.select();
                        context.selectedID = r.resourceID;
                        context.selectedType = Color.class;
                        repopulatePropertiesPane(context);
                    } else {
                        thisIcon.deselect();
                        clearSelection(context);
                        repopulatePropertiesPane(context);
                    }
                });
                if(context.selectedType == Color.class && context.selectedID.equals(r.resourceID)) {
                    context.currentlySelected = icon;
                    context.selectedID = r.resourceID;
                    context.selectedType = Color.class;
                    icon.select();
                }
                row.getChildren().add(icon.getView());
            }
        }
        VBox.setMargin(row, new Insets(0, 0, 0, (30)/2.0));
        context.getColorGrid().getChildren().add(row);
    }

    public static void clearSelection(CanvasScreen context) {
        context.currentlySelected = null;
        context.selectedID = null;
        context.selectedType = null;
    }

    public static void initialiseAudioGrid(CanvasScreen context) {
        if (context.getAudioGrid().getChildren().size() != 0) {
            context.getAudioGrid().getChildren().clear(); // Remove all the HBox's within this VBox
        }

        var row = new HBox(5);
        for (var r : context.getGame().resources) {
            if (row.getChildren().size() == 3) {
                VBox.setMargin(row, new Insets(0, 0, 0, (30)/2.0));
                context.getAudioGrid().getChildren().add(row);
                row = new HBox(5);
            }
            if (r.resourceType.equals(Resource.ResourceType.AUDIO_RESOURCE)) {
                var icon = new ToolIcon("audio", r.resourceID, e -> {
                    // TODO
                    var thisIcon = (Selectable) e[0];
                    if (context.currentlySelected != null && context.currentlySelected != thisIcon) {
                        context.currentlySelected.deselect();
                        clearSelection(context);
                        repopulatePropertiesPane(context);
                    }
                    if (context.currentlySelected != thisIcon) {
                        context.currentlySelected = thisIcon;
                        thisIcon.select();
                        context.selectedID = r.resourceID;
                        context.selectedType = AudioClip.class;
                        repopulatePropertiesPane(context);
                    } else {
                        thisIcon.deselect();
                        clearSelection(context);
                        repopulatePropertiesPane(context);
                    }
                }, true);
                if(context.selectedType == AudioClip.class && context.selectedID.equals(r.resourceID)) {
                    context.currentlySelected = icon;
                    context.selectedID = r.resourceID;
                    context.selectedType = AudioClip.class;
                    icon.select();
                }
                row.getChildren().add(icon.getView());
            }
        }
        VBox.setMargin(row, new Insets(0, 0, 0, (30)/2.0));
        context.getAudioGrid().getChildren().add(row);
    }

    public static void initialiseImagesGrid(CanvasScreen context) {
        if (context.getImageGrid().getChildren().size() != 0) {
            context.getImageGrid().getChildren().clear(); // Remove all the HBox's within this VBox
        }

        var row = new HBox(5);
        for (var r : context.getGame().resources) {
            if (row.getChildren().size() == 3) {
                VBox.setMargin(row, new Insets(0, 0, 0, (30)/2.0));
                context.getImageGrid().getChildren().add(row);
                row = new HBox(5);
            }
            if (r.resourceType.equals(Resource.ResourceType.IMAGE_RESOURCE)) {
                var icon = new ImageIcon(getImageById(context.getGame(), r.resourceID), r.resourceID, e -> {
                    // TODO
                    var thisIcon = (Selectable) e[0];
                    if (context.currentlySelected != null && context.currentlySelected != thisIcon) {
                        context.currentlySelected.deselect();
                        clearSelection(context);
                        repopulatePropertiesPane(context);
                    }
                    if (context.currentlySelected != thisIcon) {
                        context.currentlySelected = thisIcon;
                        thisIcon.select();
                        context.selectedID = r.resourceID;
                        context.selectedType = Image.class;
                        repopulatePropertiesPane(context);
                    } else {
                        thisIcon.deselect();
                        clearSelection(context);
                        repopulatePropertiesPane(context);
                    }
                });
                if(context.selectedType == Image.class && context.selectedID.equals(r.resourceID)) {
                    context.currentlySelected = icon;
                    context.selectedID = r.resourceID;
                    context.selectedType = Image.class;
                    icon.select();
                }
                row.getChildren().add(icon.getView());
            }
        }
        VBox.setMargin(row, new Insets(0, 0, 0, (30)/2.0));
        context.getImageGrid().getChildren().add(row);
    }

    private static Group draggingObjectClone;
    private static UIElement draggingObjectCloneUIElement;

    private static double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;

    public static void initialiseObjectsGrid(CanvasScreen context) {
        if (context.getObjectGrid().getChildren().size() != 0) {
            context.getObjectGrid().getChildren().clear(); // Remove all the HBox's within this VBox
        }

        var row = new HBox(5);
        for (var o : context.getGame().gameObjects) {
            if (row.getChildren().size() == 3) {
                VBox.setMargin(row, new Insets(0, 0, 0, (30)/2.0));
                context.getObjectGrid().getChildren().add(row);
                row = new HBox(5);
            }
            var callback = new Callback(){
                @Override
                public void onCallback(Object... optionalArgs) {
                    // TODO
                    var thisIcon = (Selectable) optionalArgs[0];
                    if (context.currentlySelected != null && context.currentlySelected != thisIcon) {
                        context.currentlySelected.deselect();
                        clearSelection(context);
                        repopulatePropertiesPane(context);
                    }
                    if (context.currentlySelected != thisIcon) {
                        if(context.currentlySelected != null) {
                            context.currentlySelected.deselect();
                        }
                        context.currentlySelected = thisIcon;
                        thisIcon.select();
                        context.selectedID = o.objectID;
                        context.selectedType = GameObject.class;
                        repopulatePropertiesPane(context);
                    } else {
                        thisIcon.deselect();
                        clearSelection(context);
                        repopulatePropertiesPane(context);
                    }
                }
            };
            Icon icon, duplicate;
            if (o.bgImage.isEmpty() || o.bgImage.isBlank()) {
                // No image, use bgColor
                icon = new ColorIcon(getColorByID(context.getGame(), o.bgColor), o.objectID, callback);
                duplicate = new ColorIcon(getColorByID(context.getGame(), o.bgColor), "", e -> {});
            } else {
                icon = new ImageIcon(getImageById(context.getGame(), o.bgImage), o.objectID, callback);
                duplicate = new ImageIcon(getImageById(context.getGame(), o.bgImage), "", e -> {});
            }
            if(context.selectedType == GameObject.class && context.selectedID.equals(o.objectID)) {
                context.currentlySelected = icon;
                context.selectedID = o.objectID;
                context.selectedType = GameObject.class;
                icon.select();
            }

            icon.getView().setOnMousePressed(t -> {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Group)(t.getSource())).getTranslateX();
                orgTranslateY = ((Group)(t.getSource())).getTranslateY();
            });

            icon.getView().setOnMouseDragged(t -> {
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;

                if (Math.sqrt(Math.pow(newTranslateX,2)+Math.pow(newTranslateY, 2)) > 30) {
                    // If they're actually dragging (i.e. going beyond the size of the icon)
                    if (draggingObjectClone == null) {
                        draggingObjectClone = duplicate.getView();
                        draggingObjectCloneUIElement = new UIElementWrapper(draggingObjectClone, DRAGGING_CLONE);
                        draggingObjectClone.setLayoutX(t.getSceneX() - 30);
                        draggingObjectClone.setLayoutY(t.getSceneY() - 30);
                        context.registerNewUIElement(draggingObjectCloneUIElement);
                    }
                    draggingObjectClone.setLayoutX(t.getSceneX() - 30);
                    draggingObjectClone.setLayoutY(t.getSceneY() - 30);
                }
            });
            icon.getView().setOnMouseReleased(t -> {
                if (draggingObjectCloneUIElement != null) {
                    //System.out.println("New location: (" + t.getSceneX() + ", " + t.getSceneY() + ")");
                    createNewInstance(context, context.getGame(), o, t.getSceneX(), t.getSceneY());
                    context.removeUIElement(draggingObjectCloneUIElement);
                }
                draggingObjectClone = null;
            });
            row.getChildren().add(icon.getView());
            Bounds boundsInScene = icon.getView().localToScene(icon.getView().getBoundsInLocal());
            duplicate.getView().setLayoutX(boundsInScene.getCenterX());
            duplicate.getView().setLayoutY(boundsInScene.getCenterY());
        }
        VBox.setMargin(row, new Insets(0, 0, 0, (30)/2.0));
        context.getObjectGrid().getChildren().add(row);
    }

    private static void createNewInstance(CanvasScreen context, Game game, GameObject instanceOf, double absoluteX, double absoluteY) {
        if (absoluteX-30 >= CONSOLE_HORIZONTAL_OFFSET && absoluteX-30 <= CONSOLE_HORIZONTAL_OFFSET + CANVAS_WIDTH &&
                absoluteY-30 >= CANVAS_VERTICAL_OFFSET && absoluteY <= CANVAS_VERTICAL_OFFSET-30 + CANVAS_HEIGHT) {
            var newInstance = new Instance();
            newInstance.bgImage = instanceOf.bgImage; newInstance.bgColor = instanceOf.bgColor; newInstance.instanceOf = instanceOf.objectID;
            newInstance.instanceID = "instance_"+(game.scenes.get(context.getCurrentScene()).instances.size()+1);
            newInstance.instanceLogic = "// Type your Groovy scripts for " + newInstance.instanceID + " here";
            newInstance.zIndex = 1;
            newInstance.width = (instanceOf.width > 0 ? instanceOf.width : 60);
            newInstance.height = (instanceOf.height > 0 ? instanceOf.height : 60);
            newInstance.x = absoluteX - newInstance.width/2 - CONSOLE_HORIZONTAL_OFFSET;
            newInstance.y = absoluteY - newInstance.height/2 - CANVAS_VERTICAL_OFFSET;
            game.scenes.get(context.getCurrentScene()).instances.add(newInstance);
            // System.out.println("Instance created requested for " + instanceOf.objectID +" at ("+absoluteX+","+absoluteY+")");
            refreshCanvas(context);
        }
    }

    public static Image getImageById(Game game, String id) {
        try {
            for (var r : game.resources) {
                if (r.resourceType.equals(Resource.ResourceType.IMAGE_RESOURCE) && r.resourceID.equals(id)) {
                    return new Image(new File(r.src).toURI().toURL().toExternalForm());
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Color getColorByID(Game game, String id) {
        for (var r : game.resources) {
            if (r.resourceType.equals(Resource.ResourceType.COLOR_RESOURCE) && r.resourceID.equals(id)) {
                return Color.valueOf(r.src);
            }
        }
        return Color.WHITE;
    }

    private static void populatePropsPane(CanvasScreen context, Pane propsPane) {
        var containerPane = new BorderPane();
        containerPane.setMaxWidth(RIGHT_PANE_WIDTH - RIGHT_PANE_MARGIN/2);
        containerPane.setPrefHeight(RIGHT_PANE_HEIGHT - RIGHT_PANE_MARGIN);
        containerPane.setLayoutX(RIGHT_PANE_MARGIN/4);
        containerPane.setLayoutY(RIGHT_PANE_MARGIN/2);

        var titleText = new Text(SCENE_PROPERTIES_TITLE);
        titleText.setFont(bebasKai);
        titleText.setFill(DEFAULT_TEXT_COLOR);
        containerPane.setTop(titleText);
        BorderPane.setAlignment(titleText, Pos.CENTER);

        var mainContainer = new javafx.scene.layout.Pane();
        var centerView = wrapInScrollView(mainContainer);
        containerPane.setCenter(centerView);

        propsPane.getView().getChildren().addAll(containerPane);

        repopulatePropertiesPane(context);
    }

    private static void placePanes(CanvasScreen context) {
        var toolsPane = new LeftPane(centreVertical(TOOLS_PANE_HEIGHT), TOOLS_PANE_WIDTH, TOOLS_PANE_HEIGHT, TOOLS_PANE_ID);
        var namePane = new RightPane(TOP_EDGE, RIGHT_PANE_WIDTH, NAME_PANE_HEIGHT, NAME_PANE_ID);
        var propsPane = new RightPane(TOP_EDGE, RIGHT_PANE_WIDTH, RIGHT_PANE_HEIGHT, PROPS_PANE_ID);
        var objLibPane = new RightPane(TOP_EDGE, RIGHT_PANE_WIDTH, RIGHT_PANE_HEIGHT, OBJ_LIB_PANE_ID);

        var rightPanesGroup = new VBox(RIGHT_PANE_MARGIN);
        rightPanesGroup.getChildren().addAll(namePane.getView(), propsPane.getView(), objLibPane.getView());
        rightPanesGroup.setLayoutX(ENV_WINDOW_WIDTH - RIGHT_PANE_WIDTH);

        var consolePane = new BottomPane(CONSOLE_HORIZONTAL_OFFSET, CONSOLE_PANE_WIDTH, CONSOLE_PANE_HEIGHT, CONSOLE_PANE_ID);
        context.registerNewUIElement(toolsPane, new UIElementWrapper(rightPanesGroup, RIGHT_PANES_GROUP_ID), consolePane);

        rightPanesGroup.setLayoutY(centreVertical(RIGHT_PANE_HEIGHT*2+RIGHT_PANE_MARGIN*2+NAME_PANE_HEIGHT));
        populateNamePane(context, namePane, propsPane, objLibPane, rightPanesGroup);
        populateToolsPane(context, toolsPane);
        populatePropsPane(context, propsPane);
        populateObLibPane(context, objLibPane);
        populateConsolePane(context, consolePane);
    }

    private static boolean isMenuOpen = false;

    private static void populateNamePane(CanvasScreen context, Pane namePane, Pane propsPane, Pane objLibPane, VBox rightPanesGroup) {
        var nameText = new Text("Hey, "+context.getLoggedInName()+"!");
        nameText.setFont(bebasKai); nameText.setFill(Color.WHITE);
        nameText.setX(RIGHT_PANE_MARGIN); nameText.setY(30);
        var unameText = new Text("@"+context.getLoggedInUsername());
        unameText.setFont(sofiaProSmall); unameText.setFill(Color.WHITE);
        unameText.setX(RIGHT_PANE_MARGIN); unameText.setY(50);
        var menuIcon = new ToolIcon("menu", "Menu", callback -> {
            if (isMenuOpen) {
                closeMenu(context, propsPane, objLibPane, rightPanesGroup, namePane);
            } else {
                openMenu(context, propsPane, objLibPane, rightPanesGroup, namePane);
            }
        });
        menuIcon.getView().setLayoutY(12.5);
        menuIcon.getView().setLayoutX(RIGHT_PANE_WIDTH - 62.5);
        namePane.getView().getChildren().addAll(nameText, unameText, menuIcon.getView());
    }

    public static void closeMenu(CanvasScreen context, Pane a, Pane b, VBox paneContainer, Pane namePane) {
        if (isMenuOpen) {
            var imgView = (ImageView) ((Group) namePane.getView().getChildren().get(2)).getChildren().get(1);
            imgView.setImage(new Image(ScreenHelpers.class.getResourceAsStream("/icons/menu.png")));
            a.getView().setVisible(true);
            b.getView().setVisible(true);
            paneContainer.getChildren().remove(1);
            isMenuOpen = false;
        }
    }

    public static void openMenu(CanvasScreen context, Pane a, Pane b, VBox paneContainer, Pane namePane) {
        if (!isMenuOpen) {
            var imgView = (ImageView) ((Group) namePane.getView().getChildren().get(2)).getChildren().get(1);
            imgView.setImage(new Image(ScreenHelpers.class.getResourceAsStream("/icons/close.png")));
            a.getView().setVisible(false);
            b.getView().setVisible(false);
            var menuPane = new RightPane(TOP_EDGE, RIGHT_PANE_WIDTH, RIGHT_PANE_HEIGHT * 2 + RIGHT_PANE_MARGIN, "MENU_PANE_ID");
            paneContainer.getChildren().add(1, menuPane.getView());
            var vboxParent = new VBox(15);
            vboxParent.setPrefWidth(RIGHT_PANE_WIDTH);
            try {
                JSONArray menuItems = new JSONArray(new Scanner(new File(MENU_CONFIG_FILE_PATH)).useDelimiter("\\Z").next());
                for (int i = 0; i < menuItems.length(); i++) {
                    var menuItem = menuItems.getJSONObject(i);
                    var name = menuItem.getString("name");
                    var handler = menuItem.getString("handler");

                    var nameText = new Text(name);
                    nameText.setFont(bebasKaiMedium);
                    nameText.setFill(Color.WHITE);
                    nameText.setCursor(Cursor.HAND);
                    nameText.setOnMouseClicked(e -> {
                        try {
                            MenuClickHandlers.paneA = a;
                            MenuClickHandlers.paneB = b;
                            MenuClickHandlers.paneContainer = paneContainer;
                            MenuClickHandlers.namePane = namePane;

                            var method = MenuClickHandlers.class.getMethod(handler, CanvasScreen.class);
                            method.invoke(null, context);
                        } catch (Exception ex) {
                            // Would never happen
                            ex.printStackTrace();
                        }
                    });

                    nameText.setTextAlignment(TextAlignment.CENTER);
                    nameText.setWrappingWidth(RIGHT_PANE_WIDTH);
                    vboxParent.getChildren().add(nameText);
                }
                var numChild = vboxParent.getChildren().size();
                var vBoxHeight = numChild * 30 + (numChild - 1) * 15;
                vboxParent.setLayoutY((RIGHT_PANE_HEIGHT * 2 + RIGHT_PANE_MARGIN) / 2 - (vBoxHeight) / 2);
                menuPane.getView().getChildren().add(vboxParent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            isMenuOpen = true;
        }
    }

    private static double orgSceneYInstance, orgSceneXInstance, orgTranslateXInstance, orgTranslateYInstance;
    private static boolean actuallyDragging = false;

    public static void refreshCanvas(CanvasScreen context) {
        ScrollPane sp = (ScrollPane)context.getUIElementById(CANVAS_ID).getView();
        javafx.scene.layout.Pane pane = (javafx.scene.layout.Pane) sp.getContent();
        var x = context.getUIElementById("CANVAS_ITEM");
        while (x != null) {
            context.removeUIElement(x);
            x = context.getUIElementById("CANVAS_ITEM");
        }

        if (!context.getGame().scenes.get(context.getCurrentScene()).bgImage.isEmpty()) {
            pane.setStyle("-fx-background-image: url('"+
                    getImageById(context.getGame(), context.getGame().scenes.get(context.getCurrentScene()).bgImage).getUrl()+"');" +
                    "-fx-border-radius: 5 5 5 5;" +
                    "-fx-background-size: cover;" +
                    "-fx-background-radius: 5 5 5 5;");
            Rectangle clip = new Rectangle(
                    pane.getLayoutBounds().getWidth(), pane.getLayoutBounds().getHeight()
            );
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            pane.setClip(clip);
        } else {
            if (!context.getGame().scenes.get(context.getCurrentScene()).bgColor.isEmpty()) {
                pane.setStyle("-fx-background-color: #"+
                        getColorByID(context.getGame(), context.getGame().scenes.get(context.getCurrentScene()).bgColor).toString().substring(2)+";"+
                        "-fx-border-radius: 5 5 5 5;" +
                        "-fx-background-radius: 5 5 5 5;");
            } else {
                pane.setStyle(CANVAS_STYLE);
            }
        }

        for (var i : context.getGame().scenes.get(context.getCurrentScene()).instances) {
            var iui = new InstanceUI(i, context.getGame());
            var view = iui.getView();
            view.setOnMouseClicked(e -> {
                if(iui.selected) {
                    iui.deselect();
                    clearSelection(context);
                } else {
                    if (context.currentlySelected != null)
                        context.currentlySelected.deselect();
                    iui.select();
                    context.selectedType = Instance.class;
                    context.selectedID = i.instanceID;
                    context.currentlySelected = iui;
                }
                repopulatePropertiesPane(context);
            });

            final double xDistToCanvasCorner = i.x;
            final double yDistToCanvasCorner = i.y;

//            iui.getView().setOnMousePressed(t -> {
//                orgSceneXInstance = t.getSceneX();
//                orgSceneYInstance = t.getSceneY();
//                orgTranslateXInstance = ((Node)(t.getSource())).getTranslateX();
//                orgTranslateYInstance = ((Node)(t.getSource())).getTranslateY();
//            });
//
//            iui.getView().setOnMouseDragged(t -> {
//                double offsetX = t.getSceneX() - orgSceneXInstance;
//                double offsetY = t.getSceneY() - orgSceneYInstance;
//                double newTranslateX = orgTranslateXInstance + offsetX;
//                double newTranslateY = orgTranslateYInstance + offsetY;
//
//                if (Math.sqrt(Math.pow(newTranslateX,2)+Math.pow(newTranslateY, 2)) > 30) {
//                    // If they're actually dragging (i.e. going beyond the size of the icon)
//                    actuallyDragging = true;
//                    view.setLayoutX(t.getSceneX() - CONSOLE_HORIZONTAL_OFFSET - xDistToCanvasCorner);
//                    view.setLayoutY(t.getSceneY() - CANVAS_VERTICAL_OFFSET - yDistToCanvasCorner);
//                }
//            });
//            iui.getView().setOnMouseReleased(t -> {
//                // if released in bounds, just update position, else revert to old position
//                if (actuallyDragging) {
//                    if (t.getSceneY() < CANVAS_VERTICAL_OFFSET + (CANVAS_HEIGHT*2) &&
//                            t.getSceneY() > CANVAS_VERTICAL_OFFSET &&
//                            t.getSceneX() > CONSOLE_HORIZONTAL_OFFSET &&
//                            t.getSceneX() < CONSOLE_HORIZONTAL_OFFSET + (2*CANVAS_WIDTH)) {
//                        i.x = t.getSceneX() - CONSOLE_HORIZONTAL_OFFSET;
//                        i.y = t.getSceneY() - CANVAS_VERTICAL_OFFSET;
//                        refreshCanvas(context);
//                        repopulatePropertiesPane(context);
//                    } else {
//                        refreshCanvas(context);
//                        repopulatePropertiesPane(context);
//                    }
//                    actuallyDragging = false;
//                }
//            });

            context.registerNewIcon(new UIElementWrapper(view, "CANVAS_ITEM"));
            if(context.selectedType == Instance.class && context.selectedID.equals(i.instanceID)) {
                context.currentlySelected = iui;
                context.selectedID = i.instanceID;
                context.selectedType = Instance.class;
                iui.select();
            }
        }
    }

    public static void populateConsolePane(CanvasScreen context, Pane consolePane) {
        var webView = new WebView();
        webView.getEngine().load(
                ScreenHelpers.class.getResource("/groovy_editor/index.html").toString());
        webView.setPrefWidth(CONSOLE_PANE_WIDTH);
        webView.setPrefHeight(CONSOLE_PANE_HEIGHT - 10);
        webView.setLayoutX(0); webView.setLayoutY(10);
        Rectangle clip = new Rectangle(
                CONSOLE_PANE_WIDTH, CONSOLE_PANE_HEIGHT
        );
        clip.setArcWidth(25);
        clip.setArcHeight(25);
        webView.setClip(clip);
        webView.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
            @Override public void onChanged(Change<? extends Node> change) {

                // TO GET CODE FROM EDITOR:
                // System.out.println((String) webView.getEngine().executeScript("ace.edit(\"editor\").getValue();"));
                String code = "// Select a scene, instance or object to write some Groovy scripts for them here";
                if (context.selectedType == null) {
                    code = context.getGame().scenes.get(context.getCurrentScene()).sceneLogic;
                } else if (context.selectedType == Instance.class) {
                    for (var i : context.getGame().scenes.get(context.getCurrentScene()).instances) {
                        if (i.instanceID.equals(context.selectedID))
                            code = i.instanceLogic;
                    }
                } else if (context.selectedType == GameObject.class) {
                    for (var o : context.getGame().gameObjects) {
                        if (o.objectID.equals(context.selectedID))
                            code = o.objectLogic;
                    }
                }

                webView.getEngine().executeScript("ace.edit(\"editor\").setValue(\""+StringEscapeUtils.escapeJava(code)+"\");");
                webView.setOnKeyPressed(e -> {
                    String newScript = (String) webView.getEngine().executeScript("ace.edit(\"editor\").getValue();");
                    if (context.selectedType == null) {
                        context.getGame().scenes.get(context.getCurrentScene()).sceneLogic = newScript;
                    } else if (context.selectedType == Instance.class) {
                        for (var i : context.getGame().scenes.get(context.getCurrentScene()).instances) {
                            if (i.instanceID.equals(context.selectedID))
                                i.instanceLogic = newScript;
                        }
                    } else if (context.selectedType == GameObject.class) {
                        for (var o : context.getGame().gameObjects) {
                            if (o.objectID.equals(context.selectedID))
                                o.objectID = newScript;
                        }
                    }
                });

                Set<Node> deadSeaScrolls = webView.lookupAll(".scroll-bar");
                for (Node scroll : deadSeaScrolls) {
                    scroll.setVisible(false);
                }
            }
        });

        consolePane.getView().getChildren().add(webView);
    }
}
