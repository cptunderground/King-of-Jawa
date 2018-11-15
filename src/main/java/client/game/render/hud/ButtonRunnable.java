package client.game.render.hud;

import javafx.scene.input.MouseButton;

public interface ButtonRunnable {

  public void run(MouseButton mouseButton, int posX, int posY);
}
