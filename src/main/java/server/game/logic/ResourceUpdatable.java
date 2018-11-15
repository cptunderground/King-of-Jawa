package server.game.logic;

import server.game.logic.entity.player.Player;

public interface ResourceUpdatable {
  void update(long now);

  Player getOwner();
}
