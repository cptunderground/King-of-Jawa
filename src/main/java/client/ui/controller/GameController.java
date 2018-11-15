package client.ui.controller;

import client.game.GameContainer;
import client.game.GameManager;
import client.ui.KeyBinder;
import client.ui.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class GameController implements Initializable {

  @FXML
  private Canvas img;

  @FXML
  private Canvas hud;

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private Pane toolTip;

  @FXML
  private Button levelUp;

  @FXML
  private Pane ingameChat;

  private GraphicsContext graphicsContextGame;
  private GraphicsContext graphicsContextHud;
  private double mouseX;
  private double mouseY;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    graphicsContextGame = img.getGraphicsContext2D();
    graphicsContextHud = hud.getGraphicsContext2D();
    img.setOnKeyPressed(KeyBinder::exec);
    TextArea chatOut = (TextArea) ingameChat.lookup("#chatOutput");
    chatOut.setScrollTop(Double.MAX_VALUE);
    chatOut.setWrapText(true);

  }

  public GraphicsContext getGraphicsContextGame() {
    return graphicsContextGame;
  }

  public GraphicsContext getGraphicsContextHud() {
    return graphicsContextHud;
  }

  public double getMouseX() {
    return mouseX;
  }

  public double getMouseY() {
    return mouseY;
  }

  /**
   * Initializes GameController.
   */
  public void init() {
    double width = WindowManager.getGameStage().getScene().getWidth();
    double height = WindowManager.getGameStage().getScene().getHeight();
    hud.setHeight(height);
    img.setHeight(height);
    hud.setWidth(width);
    img.setWidth(width);
    TextArea chatOut = (TextArea) ingameChat.lookup("#chatOutput");
    chatOut.clear();
    GameContainer.getCamera().setResolution((int) hud.getWidth(), (int) hud.getHeight());
    GameContainer.getGameRender().updateTilesToRender();
    GameContainer.getHudRender().updateWindowResize((int) hud.getWidth(), (int) hud.getHeight());
    ingameChat.setLayoutY(hud.getHeight() - 269);
    chatOut.setFocusTraversable(false);
    chatOut.setMouseTransparent(true);


    anchorPane.prefWidthProperty().addListener((observable, oldValue, newValue) -> {
      hud.setWidth(newValue.doubleValue());
      img.setWidth(newValue.doubleValue());
      GameContainer.getCamera().setResolution((int) hud.getWidth(), (int) hud.getHeight());
      GameContainer.getGameRender().updateTilesToRender();
      GameContainer.getHudRender().updateWindowResize((int) hud.getWidth(), (int) hud.getHeight());
      ingameChat.setLayoutY(hud.getHeight() - 269);
    });
    anchorPane.prefHeightProperty().addListener((observable, oldValue, newValue) -> {
      hud.setHeight(newValue.doubleValue());
      img.setHeight(newValue.doubleValue());
      GameContainer.getCamera().setResolution((int) hud.getWidth(), (int) hud.getHeight());
      GameContainer.getGameRender().updateTilesToRender();
      GameContainer.getHudRender().updateWindowResize((int) hud.getWidth(), (int) hud.getHeight());
      ingameChat.setLayoutY(hud.getHeight() - 269);
    });

    img.setFocusTraversable(true);
    graphicsContextGame = img.getGraphicsContext2D();
    graphicsContextHud = hud.getGraphicsContext2D();

    img.setOnKeyPressed(KeyBinder::exec);

    hud.setOnMouseMoved((event) -> {
      if (event != null) {
        mouseX = event.getX();
        mouseY = event.getY();
      }
    });

    hud.setOnMouseClicked((event) -> {
      GameContainer.getGameRender().sendClick(event.getButton());
      GameContainer.getHudRender().sendClick(event.getButton());
    });

    levelUp.setOnAction((event) -> {
      if (GameContainer.getHudRender() != null) {
        GameManager.getInstance().requestLevelUp(GameContainer.getHudRender().getCurrentBuilding());
      }
    });
  }

  public double getWidth() {
    return hud.getWidth();
  }

  public double getHeight() {
    return hud.getHeight();
  }

  public Pane getToolTip() {
    return toolTip;
  }

  public Pane getIngameChat() {
    return ingameChat;
  }

  public void resetFocus() {
    img.requestFocus();
  }
}
