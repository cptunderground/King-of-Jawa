package client.ui;

import client.Client;
import client.game.GameManager;
import client.game.lobby.LobbyManager;
import client.game.render.GameRender;
import client.game.render.HudRender;
import client.sound.Player;
import client.sound.Sound;
import client.ui.controller.GameController;
import client.ui.controller.IntroController;
import client.ui.controller.LobbyController;
import client.ui.controller.MainController;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shared.util.ConsoleLog;

public class WindowManager extends Application {

  private static Stage primaryStage;

  private static Stage gameStage;

  private static IntroController introController;
  private static MainController mainController;
  private static GameController gameController;
  private static LobbyController lobbyController;

  private static AnimationTimer animationTimer;
  private static final KeyCombination keyCombinationAltEnter = new KeyCodeCombination(
      KeyCode.ENTER, KeyCombination.ALT_DOWN);
  private static Window window;

  private static double frameRate;
  private static final long[] frameTimes = new long[100];
  private static int frameTimeIndex = 0;
  private static boolean arrayFilled = false;

  private static boolean gameWindowInitiated = false;

  public static LobbyController getLobbyController() {
    return lobbyController;
  }

  public static Window getWindow() {
    return window;
  }

  public static Stage getGameStage() {
    return gameStage;
  }


  @Override
  public void start(Stage primaryStage) {
    WindowManager.primaryStage = primaryStage;
    startIntro();
    new Thread(() -> {
      Client.getInstance().start();
    }).start();
  }

  private static void setMainWindow() {
    try {

      FXMLLoader loader = new FXMLLoader();

      loader.setLocation(WindowManager.class.getResource("/fxml/launcher.fxml"));
      AnchorPane root = loader.load();
      Scene scene = primaryStage.getScene();
      scene.setRoot(root);
      ConsoleLog.debug("Initiated gui");
      scene.getStylesheets()
          .addAll(WindowManager.class.getResource("/css/launcher.css").toExternalForm());

      mainController = loader.getController();

      primaryStage.getIcons().add(new Image("/img/Logo.png"));
      primaryStage.setTitle("King of Jawa");
      primaryStage.setMinHeight(480);
      primaryStage.setMinWidth(640);
      primaryStage.setFullScreen(primaryStage.isFullScreen());
      scene.setOnKeyPressed(event -> {
        if (keyCombinationAltEnter.match(event)) {
          toggleFullScreen();
        }
      });
      primaryStage.setResizable(true);
      primaryStage.setOnCloseRequest(t -> {
        if (lobbyController != null) {
          lobbyController.exit();
        }
        Platform.exit();
        System.exit(0);
      });
      primaryStage.show();
      mainController.initChat();
    } catch (IOException e) {
      ConsoleLog.debug(e.getMessage());
    }
  }

  private static void startIntro() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(WindowManager.class.getResource("/fxml/intro.fxml"));


      primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
      primaryStage.setFullScreen(true);
      primaryStage.getIcons().add(new Image("/img/Logo.png"));
      primaryStage.setTitle("King of Jawa");

      AnchorPane root = loader.load();

      Scene scene = new Scene(root);

      primaryStage.setScene(scene);
      primaryStage.setMinHeight(480);
      primaryStage.setMinWidth(640);

      introController = loader.getController();
      introController.setupIntro();

      primaryStage.show();
    } catch (IOException e) {
      ConsoleLog.debug(e.getMessage());
    }
  }

  /**
   * Sets the GameWindow.
   */
  public static void setGameWindow() {
    try {
      if (!gameWindowInitiated) {
        gameWindowInitiated = false;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WindowManager.class.getResource("/fxml/gameWindow.fxml"));
        AnchorPane root = loader.load();
        gameStage = new Stage();
        gameController = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets()
            .add(WindowManager.class.getResource("/css/IngameChat.css").toExternalForm());
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        gameStage.setX(primaryScreenBounds.getMinX());
        gameStage.setY(primaryScreenBounds.getMinY());
        gameStage.setWidth(primaryScreenBounds.getWidth());
        gameStage.setHeight(primaryScreenBounds.getHeight());
        gameStage.setScene(scene);
        gameStage.getIcons().add(new Image("/img/Logo.png"));

        gameStage.setTitle("King of Jawa");
        gameStage.show();
        gameStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());
        gameStage.setOnCloseRequest(t -> {
          if (lobbyController != null) {
            lobbyController.exit();
          }
          setWindow(Window.MAIN);

        });
      }
    } catch (IOException e) {
      ConsoleLog.debug(e.getMessage());
    }
  }

  /**
   * Sets the LobbyWindow.
   */
  private static void setLobbyWindow() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(WindowManager.class.getResource("/fxml/lobby.fxml"));
      AnchorPane root = loader.load();
      Scene scene = primaryStage.getScene();
      scene.setRoot(root);
      ConsoleLog.debug("Initiated gui");
      scene.getStylesheets()
          .addAll(WindowManager.class.getResource("/css/launcher.css").toExternalForm());

      lobbyController = loader.getController();

      primaryStage.getIcons().add(new Image("/img/Logo.png"));
      primaryStage.setTitle("King of Jawa");
      primaryStage.setFullScreen(primaryStage.isFullScreen());
      primaryStage.setMinHeight(480);
      primaryStage.setMinWidth(640);
      primaryStage.setResizable(true);
      primaryStage.show();
      lobbyController.initChat();
    } catch (IOException e) {
      ConsoleLog.debug(e.getMessage());
    }
    lobbyController.init();
  }


  /**
   * Initializes game with GameRender and HudRender.
   *
   * @param gameRender GameRender.
   * @param hudRender HudRender.
   */
  public static void initGame(GameRender gameRender, HudRender hudRender) {
    gameController.init();
    animationTimer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        gameRender.render(gameController.getGraphicsContextGame());
        hudRender.render(gameController.getGraphicsContextHud());
        updateFrameRateCounter(now);
      }
    };

    animationTimer.start();
  }

  private static void updateFrameRateCounter(long now) {
    long oldFrameTime = frameTimes[frameTimeIndex];
    frameTimes[frameTimeIndex] = now;
    frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
    if (frameTimeIndex == 0) {
      arrayFilled = true;
    }
    if (arrayFilled) {
      long elapsedNanos = now - oldFrameTime;
      long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
      frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;

    }
  }

  public static void setPrimaryStage(Stage primaryStage) {
    WindowManager.primaryStage = primaryStage;
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public static MainController getMainController() {
    return mainController;
  }

  public static GameController getGameController() {
    return gameController;
  }


  /**
   * Sets a window to a specific case.
   *
   * @param w WindowCase.
   */
  public static void setWindow(Window w) {
    if (animationTimer != null) {
      animationTimer.stop();
      animationTimer = null;
      gameStage.close();
    }

    switch (w) {
      case GAME:
        window = Window.GAME;
        setGameWindow();

        break;
      case LOBBY:
        window = Window.LOBBY;
        setLobbyWindow();
        break;
      case MAIN:
      default:
        window = Window.MAIN;
        setMainWindow();
        break;
    }

  }

  public static double getFrameRate() {
    return frameRate;
  }

  /**
   * Toggles the game to fullscreen.
   */
  public static void toggleFullScreen() {
    if (window == Window.GAME) {
      gameStage.setFullScreen(!gameStage.isFullScreen());
    }
    if (window == Window.MAIN || window == Window.LOBBY) {
      primaryStage.setFullScreen(!primaryStage.isFullScreen());
    }
  }

  public static KeyCombination getKeyCombinationAltEnter() {
    return keyCombinationAltEnter;
  }

  public static void closeLobbyWindow() {
    setWindow(Window.MAIN);
  }

  public static void closeGameWindow() {
    gameStage.close();
    setWindow(Window.MAIN);
  }

}
