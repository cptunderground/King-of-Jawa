package client.ui.controller;

import client.Client;
import client.chat.ChatManager;
import client.game.chat.IngameChat;
import client.game.highscore.HighscoreManager;
import client.game.lobby.Lobby;
import client.game.lobby.LobbyManager;
import client.ui.Window;
import client.ui.WindowManager;
import client.user.User;
import client.user.UserManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import server.game.highscore.HighscoreElement;

public class MainController implements Initializable {


  @FXML
  private TextArea chatOutput;
  @FXML
  private TextField chatInput;

  private static String msg;
  @FXML
  private TableView<User> userTable;

  @FXML
  private TableColumn<User, String> tableUserName;

  @FXML
  private AnchorPane mainWindow;

  @FXML
  private AnchorPane lobbyScreen;

  @FXML
  private AnchorPane highscoreScreen;

  @FXML
  private AnchorPane homeScreen;

  @FXML
  private Line sexySpacerLine;

  @FXML
  private Button playButton;

  @FXML
  private Button homeButton;

  @FXML
  private Button createLobby;

  @FXML
  private Button highscoreButton;

  @FXML
  private TableView<Lobby> lobbyTable;

  @FXML
  private TableColumn<Lobby, Integer> tableLobbyId;

  @FXML
  private TableColumn<Lobby, String> tableLobbyName;

  @FXML
  private TableColumn<User, String> tableLobbyOwner;

  @FXML
  private TableColumn<Lobby, String> tableLobbyPlayers;

  @FXML
  private TableColumn<Lobby, String> tableLobbyState;

  @FXML
  private TableView<HighscoreElement> highscoreList;

  @FXML
  private TableColumn<HighscoreElement, String> game;

  @FXML
  private TableColumn<HighscoreElement, String> winner;

  @FXML
  private TableColumn<HighscoreElement, Double> pointsPerTime;

  @FXML
  private Button settingsButton;

  @FXML
  private AnchorPane settingsScreen;

  @FXML
  private Button nickButton;

  @FXML
  private TextField nickEdit;

  @FXML
  private Label pingLabel;

  private ObservableList<HighscoreElement> highscoreTable;

  private ObservableMap<UUID, User> userMap;
  private ObservableList<Lobby> lobbyList;
  private static List<String> messages = new ArrayList<>();

  private boolean isFirstMessage = true;

  private int viewMode = 0;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initializeChatOutput();
    initializeChatInput();
    initializeUserlist();
    initializeTabs();
    initializeLobbyList();
    initLobbyTab();
    initSettingsTab();
    initializeHighscoreText();
    sexySpacerLine.endXProperty().bind(mainWindow.widthProperty());
  }

  private void initSettingsTab() {
    nickButton.setOnAction(event -> {
      String nickName = nickEdit.getText();
      ChatManager.getInstance().getRoom("global").send("/nick " + nickName);
    });
  }

  private void initLobbyTab() {
    createLobby.setOnAction(event -> {
      openLobbyCreator();
    });
  }

  private void initializeTabs() {
    playButton.setOnAction((event) -> {
      if (Client.getInstance().getLocalUser().getLobby() != null) {
        WindowManager.setWindow(Window.LOBBY);
      } else {
        setVisible(1);
      }
    });
    homeButton.setOnAction((event) -> {
      setVisible(0);
    });
    highscoreButton.setOnAction((event) -> {
      setVisible(2);
    });
    settingsButton.setOnAction((event) -> {
      setVisible(3);
    });
  }

  private void hideAll() {
    homeScreen.setVisible(false);
    lobbyScreen.setVisible(false);
    highscoreScreen.setVisible(false);
    settingsScreen.setVisible(false);
  }

  private void setVisible(int viewMode) {
    this.viewMode = viewMode;
    hideAll();
    switch (viewMode) {
      case 0:
        show(homeScreen);
        break;
      case 1:
        show(lobbyScreen);
        break;
      case 2:
        highscoreList.getItems().clear();
        HighscoreManager.getInstance().requestHighscore();
        show(highscoreScreen);
        break;
      case 3:
        show(settingsScreen);
        break;
      default:
        show(homeScreen);
    }
  }

  private void show(AnchorPane anchorPane) {
    anchorPane.setVisible(true);
  }

  /**
   * If it is the first message it prints the message to the chat otherwise it adds a black space
   * before the message.
   */
  public void sendMessage(String text) {
    IngameChat.sendMessage(text);
    messages.add(text);
    appendToOutput(text);
  }

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

  /**
   * This method starts the gameWindow.
   */
  public void startGameWindow() {
    WindowManager.setWindow(Window.GAME);
  }

  /**
   * This method starts the highscoreWindow.
   */

  public void startHighscoreWindow() {
    WindowManager.setWindow(Window.HIGHSCORE);
  }

  /**
   * Initializes the chatOutput TextArea with the given settings.
   */
  public void initializeChatOutput() {
    chatOutput.setEditable(false);
    chatOutput.setMouseTransparent(false);
    chatOutput.setFocusTraversable(false);
    chatOutput.setScrollTop(Double.MAX_VALUE);
    chatOutput.setWrapText(true);
  }

  /**
   * This method it is possible to press enter to send a message. If you press the button send, it
   * checks if a text is written and to which lobby it should be sent. As soon as the message is
   * sent it clears the textfield. If no message is written it shows a message in the textArea.
   */
  public void initializeChatInput() {
    chatInput.setOnKeyPressed((event) -> {
      if (event.getCode() == KeyCode.ENTER) {
        send();
      }
    });
  }

  /**
   * Sends a message.
   */
  public void send() {
    if ((chatInput.getText() != null && !chatInput.getText().isEmpty())) {
      msg = chatInput.getText();
      ChatManager.getInstance().getRoom("global").send(msg);
      chatInput.setText("");
      chatInput.clear();
    } else {
      sendMessage("please write a message");
    }
  }

  /**
   * This method handles the observableMap which updates the userList, gets the userList from the
   * UserManager.
   */
  public void initializeUserlist() {

    tableUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnReorder(userTable, tableUserName);
    Map<UUID, User> userList1 = UserManager.getInstance().getUsers();
    userMap = FXCollections.observableMap(userList1);
    for (UUID uuid : userMap.keySet()) {
      User user = userMap.get(uuid);
      userTable.getItems().add(user);
    }
    userMap.addListener((MapChangeListener<UUID, User>) c -> {
      if (c.wasAdded()) {
        User user = c.getValueAdded();
        Platform.runLater(() -> {
          userTable.getItems().add(user);
        });
      }
      if (c.wasRemoved()) {
        User user = c.getValueRemoved();
        Platform.runLater(() -> {
          userTable.getItems().remove(user);
        });
      }
    });
    hideTableHeader(userTable);
    updateUserList();

    userTable.setOnMousePressed(event -> {
      if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
        String str = userTable.getSelectionModel().getSelectedItem().getName();
        chatInput.setText("/whisper " + "\"" + str + "\" ");
        chatInput.requestFocus();
        chatInput.selectEnd();
      }
    });
  }

  /**
   * This method handles the observableArrayList which updates the lobbyList, gets the userList from
   * the LobbyManager.
   */
  public void initializeLobbyList() {
    tableLobbyId.setCellValueFactory(new PropertyValueFactory<>("id"));
    tableLobbyName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tableLobbyOwner.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
    tableLobbyState.setCellValueFactory(new PropertyValueFactory<>("stateString"));
    tableLobbyPlayers.setCellValueFactory(new PropertyValueFactory<>("players"));

    columnReorder(lobbyTable, tableLobbyId, tableLobbyPlayers,
        tableLobbyName, tableLobbyOwner, tableLobbyState);
    ArrayList<Lobby> lobbyList1 = (ArrayList<Lobby>) LobbyManager.getInstance().getLobbies();
    lobbyList = FXCollections.observableArrayList(lobbyList1);
    for (Lobby lobby : lobbyList) {
      lobbyTable.getItems().add(lobby);
    }
    lobbyList.addListener((ListChangeListener<Lobby>) c -> {
      while (c.next()) {
        if (c.wasAdded()) {
          List<? extends Lobby> innerList = c.getAddedSubList();
          for (Lobby l : innerList) {
            Platform.runLater(() -> {
              lobbyTable.getItems().add(l);
            });
          }
        }
        if (c.wasRemoved()) {
          List<? extends Lobby> innerList = c.getRemoved();
          for (Lobby l : innerList) {
            Platform.runLater(() -> {
              lobbyTable.getItems().remove(l);
            });
          }
        }
      }
    });

    lobbyTable.setOnMousePressed(event -> {
      if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
        Lobby lobbyToJoin = lobbyTable.getSelectionModel().getSelectedItem();
        if (lobbyToJoin != null) {
          LobbyManager.getInstance().joinLobby(lobbyToJoin);
        }

      }
    });

    hideTableHeader(lobbyTable);
  }


  private void hideTableHeader(TableView tableView) {
    tableView.widthProperty().addListener((source, oldWidth, newWidth) -> {
      Pane header = (Pane) tableView.lookup("TableHeaderRow");
      if (header.isVisible()) {
        header.setMaxHeight(0);
        header.setMinHeight(0);
        header.setPrefHeight(0);
        header.setVisible(false);
      }
    });
  }

  /**
   * This method stats the Lobby window with the button creat lobby.
   */
  public void openLobbyCreator(ActionEvent actionEvent) {
    if (WindowManager.getWindow() != Window.LOBBY) {
      LobbyManager.getInstance().openLobby();
    }
  }

  /**
   * This method stats the Lobby window with the button creat lobby.
   */
  public void openLobbyCreator() {
    if (WindowManager.getWindow() != Window.LOBBY) {
      LobbyManager.getInstance().openLobby();
    }
  }

  /**
   * Reorders the columns. /https://bittlife.com/javafx-disable-column-reorder-tableview/
   *
   * @param table specific TableView.
   * @param columns specific TableColumns.
   */
  public static void columnReorder(TableView table, TableColumn... columns) {
    List<TableColumn> tableColumns = new ArrayList<>(Arrays.asList(columns));
    table.getColumns().addListener(new ListChangeListener() {
      public boolean suspended;

      @Override
      public void onChanged(Change change) {
        change.next();
        if (change.wasReplaced() && !suspended) {
          this.suspended = true;
          table.getColumns().setAll(tableColumns);
          this.suspended = false;
        }
      }
    });
  }

  public ObservableMap<UUID, User> getUserList() {
    return userMap;
  }

  public void updateUserList() {
    userTable.refresh();
  }

  public void updateLobbyList() {
    lobbyTable.refresh();
  }

  public ObservableList<Lobby> getLobbyList() {
    return lobbyList;
  }


  /**
   * Initializes Chat.
   */
  public void initChat() {
    chatOutput.clear();
    isFirstMessage = true;
    for (String message : messages) {
      appendToOutput(message);
    }
  }

  public void showMain() {
    setVisible(0);
  }

  public void showHighscore() {
    setVisible(2);
  }

  /**
   * This method handles the observableArrayList which updates the highscoreList, gets the highscore
   * from the HighscoreManager.
   */
  public void initializeHighscoreText() {
    game.setCellValueFactory(new PropertyValueFactory<>("gameName"));
    winner.setCellValueFactory(new PropertyValueFactory<>("userName"));
    pointsPerTime.setCellValueFactory(new PropertyValueFactory<>("pointsPerMinute"));

    ArrayList<HighscoreElement> highscoreList1 =
        (ArrayList<HighscoreElement>) HighscoreManager.getInstance().getHighscores();
    highscoreTable = FXCollections.observableArrayList(highscoreList1);
    highscoreTable.addListener((ListChangeListener<HighscoreElement>) c -> {
      while (c.next()) {
        if (c.wasAdded()) {
          List<? extends HighscoreElement> innerList = c.getAddedSubList();
          for (HighscoreElement element : innerList) {
            highscoreList.getItems().add(element);
          }
        }
        if (c.wasRemoved()) {
          List<? extends HighscoreElement> innerList = c.getRemoved();
          for (HighscoreElement element : innerList) {
            highscoreList.getItems().remove(element);
          }
        }
      }
    });
  }

  /**
   * Returns the ObservableList with HighscoreElements.
   *
   * @return highscoreTable
   */
  public ObservableList<HighscoreElement> getHighscoreTable() {
    return highscoreTable;
  }

  /**
   * This method refreshes the highscoreList.
   */
  public void updateHighscoreList() {
    highscoreList.refresh();
  }

  public void setPing(long ping) {
    pingLabel.setText("Ping: " + ping);
  }
}

