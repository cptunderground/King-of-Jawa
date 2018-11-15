package client.game.render;

import javafx.scene.canvas.GraphicsContext;

public interface GameRenderable {

  void render(GraphicsContext gc);

  void renderHover(GraphicsContext gc);
}
