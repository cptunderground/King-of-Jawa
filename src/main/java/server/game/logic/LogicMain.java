package server.game.logic;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import server.game.GameContainer;
import server.game.lobby.Lobby;
import server.game.logic.entity.building.resource.WoodFarm;
import server.game.logic.map.island.Island;
import server.game.logic.map.tile.Tile;
import server.user.User;
import shared.util.ConsoleLog;

public class LogicMain {

  /**
   * This method is for testing purpouse while developing please ignore.
   */
  public static void main(String[] args) throws IOException {
    BasicConfigurator.configure();
    GameContainer gameContainer = new GameContainer(
        new Lobby(new User(new Socket(), "Hidin", "Hidin")), "basic");
    int j = 0;
    int k = 0;
    Island is = gameContainer.getIsland(0);
    Tile tile = is.getTiles().get(2);
    // Player player = new Player(gameContainer);
    // WoodFarm woodFarm = new WoodFarm(gameContainer, WoodFarm.class, player, tile.getX(),
    //     tile.getY());
    List<WoodFarm> buildings = gameContainer.getEntityManager().getEntitiesByType(WoodFarm.class);
    ConsoleLog.info(buildings.size() + "");
  }
}
