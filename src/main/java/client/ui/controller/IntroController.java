package client.ui.controller;

import client.Client;
import client.net.connection.ConnectionManager;
import client.ui.Window;
import client.ui.WindowManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;

public class IntroController implements Initializable {

  @FXML
  private Label groupName;

  @FXML
  private Label keyLabel;

  @FXML
  private ImageView logoView;

  @FXML
  private Pane textPane;

  @FXML
  private AnchorPane introWindow;

  private Image logo;
  private Background bg = new Background(
      new BackgroundImage(
          new Image("/img/introBackground.png"), BackgroundRepeat.REPEAT,
          BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));

  private boolean pressed = false;

  private final double imageScale = 0.3;
  private final double fontScale = 0.025;
  private final double yoffsetScale = 0.01;
  private Font font;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  /**
   * This method sets up the Intro.
   */
  public void setupIntro() {
    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    double height = primaryScreenBounds.getHeight();
    double width = primaryScreenBounds.getWidth();
    double imageSize = imageScale * height;
    double ypsilonOffset = yoffsetScale * height;

    introWindow.setBackground(bg);

    logo = new Image("/img/LogoWeiss.png", imageSize, imageSize, false, false);

    double imageX = (width - imageScale) / 2;
    double imageY = (height - imageScale) / 2 - ypsilonOffset;
    logoView.setImage(logo);
    logoView.setLayoutX(imageX);
    logoView.setLayoutY(imageY);
    logoView.setFitWidth(imageSize / 20);
    logoView.setFitHeight(imageSize / 20);

    double fontSize = fontScale * height;
    font = new Font("Arial", fontSize);
    groupName.setFont(font);

    final Text textMeasure = new Text(groupName.getText());
    textMeasure.setFont(font);

    double labelWidth = textMeasure.getLayoutBounds().getWidth();

    groupName.setLayoutX((width - labelWidth) / 2);
    groupName.setLayoutY((height + imageSize) / 2 - ypsilonOffset);
    groupName.setVisible(false);

    textPane.setLayoutX((width - textPane.getPrefWidth()) / 2);
    textPane.setLayoutY(height - textPane.getHeight() - ypsilonOffset);
    textPane.setVisible(false);

    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(4000), logoView);
    scaleTransition.setByX(20);
    scaleTransition.setByY(20);
    scaleTransition.setOnFinished((ActionEvent actionEvent) -> {
      groupName.setVisible(true);
      initContinueText();
    });
    scaleTransition.play();

  }

  private void initContinueText() {
    textPane.setVisible(true);
    FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), keyLabel);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.1);
    fadeTransition.setCycleCount(Animation.INDEFINITE);
    fadeTransition.setAutoReverse(true);
    fadeTransition.play();
    textPane.requestFocus();
    textPane.setOnKeyPressed((event) -> {
      if (event.getCode() == KeyCode.SPACE) {
        if (!pressed) {
          Platform.runLater(() -> WindowManager.setWindow(Window.MAIN));
          ConnectionManager.getInstance().requestHandshake(Client.getInstance().getName());
          pressed = true;
        }
      }
    });
  }


}
