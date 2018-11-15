package client.ui.controller;

import client.Client;
import client.game.chat.IngameChat;
import client.game.lobby.Lobby;
import client.game.lobby.LobbyManager;
import client.game.logic.map.MapManager;
import client.ui.Window;
import client.ui.WindowManager;
import client.user.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import shared.util.ConsoleLog;


public class LobbyController implements Initializable {

  @FXML
  private ComboBox<String> mapSelector;

  @FXML
  private ImageView mapPreviewView;

  @FXML
  private ImageView player1View;

  @FXML
  private ImageView player2View;

  @FXML
  private ImageView player3View;

  @FXML
  private ImageView player4View;

  @FXML
  private Button goButton;

  @FXML
  private Button backButton;

  @FXML
  private Button playButton;

  @FXML
  private Button homeButton;

  @FXML
  private Button highscoreButton;

  @FXML
  private TextArea chatOutput;

  @FXML
  private TextField chatInput;

  @FXML
  private Label player1Name;

  @FXML
  private Label player2Name;

  @FXML
  private Label player3Name;

  @FXML
  private Label player4Name;

  @FXML
  private Line sexySpacerLine;

  @FXML
  private AnchorPane mainWindow;

  private static String msg;
  private Image mapPreviewImage;
  private boolean isFirstMessage = true;

  private static List<User> userList = new ArrayList<>();
  private static List<String> messages = new ArrayList<>();


  @Override
  public void initialize(URL url, ResourceBundle rb) {
    setReadyButton();
    initializeChatInput();
    init();
    initializeChatOutput();
    initializeMap();
    initializeTabs();
    sexySpacerLine.endXProperty().bind(mainWindow.widthProperty());
  }

  private void initializeTabs() {
    playButton.setOnAction((event) -> {
      if (Client.getInstance().getLocalUser().getLobby() != null) {
        WindowManager.setWindow(Window.LOBBY);
        resetUsers();
      }
    });

    homeButton.setOnAction((event) -> {
      WindowManager.setWindow(Window.MAIN);
      WindowManager.getMainController().showMain();
    });

    highscoreButton.setOnAction((event) -> {
      WindowManager.setWindow(Window.MAIN);
      WindowManager.getMainController().showHighscore();
    });
  }

  /**
   * Initialises the little map frame in the lobby window with a preview of the currently selected
   * map.
   */
  public void initializeMap() {

    List<String> availableMaps = MapManager.getAvailableMaps();
    if (availableMaps != null) {
      ObservableList<String> maps = FXCollections.observableArrayList(availableMaps);
      mapSelector.setItems(maps);
      mapSelector.getSelectionModel().selectedItemProperty()
          .addListener((selected, oldMap, newMap) -> {
            if (newMap != null) {
              mapPreviewImage = MapManager.loadMapPreview(newMap);
              mapPreviewView.setImage(mapPreviewImage);
              User localUser = Client.getInstance().getLocalUser();
              Lobby lobby = localUser.getLobby();
              LobbyManager.getInstance().sendMapChange(newMap, lobby);
            }
          });
      mapSelector.getSelectionModel().select(0);
    }
  }

  /**
   * Inizializes chatoutput area with the given settings.
   */
  public void initializeChatOutput() {
    chatOutput.setEditable(false);
    chatOutput.setMouseTransparent(false);
    chatOutput.setFocusTraversable(false);
    chatOutput.setScrollTop(Double.MAX_VALUE);
    chatOutput.setWrapText(true);
  }


  /**
   * Initializes chat, it allows the user to press enter to send the message, sends message to the
   * current lobby.
   */
  public void initializeChatInput() {
    chatInput.setOnKeyPressed((event) -> {
      if (event.getCode() == KeyCode.ENTER) {
        if ((chatInput.getText() != null && !chatInput.getText().isEmpty())) {
          msg = chatInput.getText();
          Lobby lobby = Client.getInstance().getLocalUser().getLobby();
          lobby.getChatRoom().send(msg);
          chatInput.setText("");
          chatInput.clear();
        } else {
          sendLobbyMessage("please write a message");
        }
      }
    });
  }

  /**
   * If it is the first message it prints the message to the chat otherwise it adds a "enter" *
   * before the message.
   */
  public void sendLobbyMessage(String text) {
    IngameChat.sendMessage(text);
    messages.add(text);
    appendToOutput(text);
  }


  /**
   * Exits LobbyController.
   */
  public void exit() {
    User localUser = Client.getInstance().getLocalUser();
    Lobby lobby = localUser.getLobby();
    if (lobby != null) {
      if (lobby.findUser(localUser)) {
        LobbyManager.getInstance().requestLobbyLeave();
      }
    }
  }

  /**
   * Button that set's the player ready the lobby Window.
   */
  public void setReadyButton() {
    goButton.setOnAction((event) -> {
      User localUser = Client.getInstance().getLocalUser();
      if (localUser != null) {
        Lobby lobby = localUser.getLobby();
        if (lobby != null) {
          if (lobby.getOwner() != Client.getInstance().getLocalUser()) {
            LobbyManager.getInstance().requestUpdateReadyState();
          } else {
            startLobbyGame();
          }
        }
      }
    });
    backButton.setOnAction((event) -> {
      exit();
    });
  }

  /**
   * This method will be executed, when the exit is successfully done.
   */
  public void exitDone() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        WindowManager.closeLobbyWindow();
      }
    });
  }

  public void startGameWindow() {
    WindowManager.setWindow(Window.GAME);
  }

  /**
   * Initializes UserList.
   */
  public void initializeUserlist() {

  }

  /**
   * Starts the game in the lobby.
   */
  public void startLobbyGame() {
    User localUser = Client.getInstance().getLocalUser();
    if (localUser != null) {
      Lobby lobby = localUser.getLobby();
      LobbyManager.getInstance()
          .requestStart(lobby, mapSelector.getSelectionModel().getSelectedItem());
    }
  }

  /**
   * Enables odr disables the start button in the lobby window for the user. Enables for the owner
   * and disables for all the other users.
   */
  public void init() {
    User localUser = Client.getInstance().getLocalUser();
    if (localUser != null) {
      Lobby lobby = localUser.getLobby();
      if (lobby != null) {
        resetUsers();
        if (lobby.getOwner() != Client.getInstance().getLocalUser()) {
          goButton.setText("READY");
          mapSelector.setDisable(true);
        } else {
          goButton.setText("GO");
          mapSelector.setDisable(false);
        }
      }
    }
  }

  /**
   * Sets the currently selected map in the lobby window.
   *
   * @param currentMap the currently selected map.
   */
  public void setCurrentMap(String currentMap) {
    Platform.runLater(() -> {
      int index = MapManager.getAvailableMaps().indexOf(currentMap);
      mapSelector.getSelectionModel().select(index);
    });
  }

  public synchronized void setUserReady(User user, boolean isReady) {
    setUserReady(getPosition(user), isReady);
  }

  /**
   * Sets the State of the User for ready to play.
   *
   * @param id id of the User.
   * @param isReady boolean whether a Player is ready or not.
   */
  public synchronized void setUserReady(int id, boolean isReady) {
    Image img = new Image("img/ready-user.png");
    if (!isReady) {
      img = new Image("img/default-user.png");
    }
    switch (id) {
      case 0:
        player1View.setImage(img);
        break;
      case 1:
        player2View.setImage(img);
        break;
      case 2:
        player3View.setImage(img);
        break;
      case 3:
        player4View.setImage(img);
        break;
      default:
        ConsoleLog.warning("NOPE");
    }
  }

  public synchronized void setUserName(User user, String name) {
    setUserName(getPosition(user), name);
  }

  /**
   * This method sets the UserName.
   *
   * @param id Integer of the id.
   * @param name name of the User.
   */
  public synchronized void setUserName(int id, String name) {
    switch (id) {
      case 0:
        player1Name.setText(name);
        break;
      case 1:
        player2Name.setText(name);
        break;
      case 2:
        player3Name.setText(name);
        break;
      case 3:
        player4Name.setText(name);
        break;
      default:
        ConsoleLog.warning("NOPE");
    }
  }

  /**
   * Adds a User.
   *
   * @param user User to add.
   */
  public synchronized void addUser(User user) {
    userList.add(user);
    setUserReady(user, !user.getState().equals("WAITING"));
    setUserName(user, user.getName());
  }

  /**
   * Removes a User.
   *
   * @param user User to remove.
   */
  public synchronized void remove(User user) {
    if (user != null) {
      setUserReady(user, false);
      setUserName(user, "N/A");
      userList.remove(user);
      resetUsers();
    }
  }

  private void resetUsers() {
    for (int i = 0; i < 4; i++) {
      if (i < userList.size()) {
        User user = userList.get(i);
        if (user != null) {
          setUserName(user, user.getName());
          setUserReady(user, !user.getState().equals("WAITING"));
        }
      } else {
        setUserReady(i, false);
        setUserName(i, "N/A");
      }
    }
  }

  /**
   * Removes all User.
   */
  public synchronized void removeAllUser() {
    for (int i = 0; i < Math.min(4, userList.size()); i++) {
      remove(userList.get(i));
    }
  }

  private int getPosition(User toFind) {
    for (int i = 0; i < Math.min(4, userList.size()); i++) {
      User user = userList.get(i);
      if (user != null) {
        if (toFind.equals(user)) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * Initializes chat.
   */
  public void initChat() {
    chatOutput.clear();
    isFirstMessage = true;
    for (String message : messages) {
      appendToOutput(message);
    }
  }

  @SuppressWarnings("Duplicates")
  private void appendToOutput(String text) {
    if (isFirstMessage) {
      int x = 0;
      isFirstMessage = false;
      Platform.runLater(() -> {
        chatOutput.appendText(text);
      });

    } else {
      Platform.runLater(() -> {
        chatOutput.appendText("\n" + text);
      });

    }
  }
}

