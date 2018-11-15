package client.game.chat;

import client.Client;
import client.chat.ChatManager;
import client.game.lobby.Lobby;
import client.ui.WindowManager;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class IngameChat {

  private static boolean open = false;
  private static boolean isFirstMessage = true;

  /**
   * toggles the chatInputs.
   */
  public static void toggleInput() {
    open = !open;
    if (WindowManager.getGameController() != null) {
      Pane ingameChatPane = WindowManager.getGameController().getIngameChat();
      if (ingameChatPane != null) {
        TextArea out = (TextArea) ingameChatPane.lookup("#chatOutput");
        TextField in = (TextField) ingameChatPane.lookup("#chatInput");
        if (out != null && in != null) {
          if (open) {
            out.setLayoutY(10);
            out.setPrefHeight(200);

            in.setVisible(true);
            in.setDisable(false);
            in.requestFocus();
            in.setOnKeyPressed((event) -> {
              if (event.getCode() == KeyCode.ENTER) {
                if ((in.getText() != null && !in.getText().isEmpty())) {
                  String msg = in.getText();
                  Lobby lobby = Client.getInstance().getLocalUser().getLobby();
                  lobby.getChatRoom().send(msg);
                  in.setText("");
                  in.clear();
                }
                toggleInput();
                return;
              }
              if (event.getCode() == KeyCode.ESCAPE) {
                toggleInput();
                return;
              }
            });
          } else {
            out.setLayoutY(35);
            out.setPrefHeight(220);
            in.setVisible(false);
            in.setDisable(true);
            WindowManager.getGameController().resetFocus();
          }
        }
      }
    }
  }

  /**
   * This method sends a message.
   *
   * @param msg message that wants to be sent.
   */
  public static void sendMessage(String msg) {
    System.out.println("message: " + msg);
    if (WindowManager.getGameController() != null) {
      Pane ingameChatPane = WindowManager.getGameController().getIngameChat();
      if (ingameChatPane != null) {
        TextArea out = (TextArea) ingameChatPane.lookup("#chatOutput");
        if (out != null) {
          if (isFirstMessage) {
            Platform.runLater(() -> {
              out.appendText(msg);
            });
            isFirstMessage = false;
          } else {
            Platform.runLater(() -> {
              out.appendText("\n" + msg);
            });

          }
        }
      }
    }
  }
}
