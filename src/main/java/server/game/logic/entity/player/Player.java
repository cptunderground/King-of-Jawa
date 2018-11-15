package server.game.logic.entity.player;

import javafx.scene.paint.Color;
import server.game.GameContainer;
import server.game.logic.entity.Entity;
import server.game.logic.entity.building.base.Base;
import server.game.logic.entity.component.Amusements;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.component.Resources;
import server.game.logic.entity.component.Storage;
import server.game.logic.entity.component.Wallet;
import server.user.User;
import shared.game.building.BuildingType;

public class Player extends Entity {

  private User user;
  private Resources buildingLimitation;
  private Amusements amusementLimitation;
  private int inhabitants;
  private Color color;

  /**
   * Creates a player object for a specific gameContainer and a specific user.
   *
   * @param gameContainer the gameContainer where the player operates.
   * @param user the user which belongs to the player.
   * @param color the color of the player.
   */
  public Player(GameContainer gameContainer, User user, Color color) {
    super(gameContainer, Player.class);
    this.user = user;
    addComponent(new Wallet(), Wallet.class);
    getWallet().limitation.coin = 500;
    getWallet().limitation.stone = 10;
    getWallet().limitation.wood = 15;
    inhabitants = 0;
    this.color = color;
    Resources startMoney = new Resources();
    startMoney.coin = 500;
    getWallet().increaseAmount(startMoney, 1);
  }

  public Wallet getWallet() {
    return getComponent(Wallet.class);
  }

  /**
   * Updates the players limitations.
   */
  public void updateLimitations() {
    Resources limitation = new Resources();
    buildingLimitation = new Resources();
    amusementLimitation = new Amusements();
    for (Base base : getGameContainer().getEntityManager().getBases()) {
      if (base.getOwner().equals(this)) {
        limitation.addCurrencies(Base.get(Storage.class, Base.class)
            .getWalletLimitations(base.getComponent(Level.class).currentLevel), 1);
        buildingLimitation
            .addCurrencies(Base.getStorage(Base.class).getBuildingLimit(base.getCurrentLevel()), 1);
        amusementLimitation.addAmusements(
            Base.getStorage(Base.class).getAmusementLimitation(base.getCurrentLevel()));

      }
    }
    getWallet().limitation = limitation;
  }

  public User getUser() {
    return user;
  }

  /**
   * This Method gets the limitation of the specific BuildingType for the player.
   *
   * @param buildingType the specific buildingType.
   * @return the specific limitation of the buildingType.
   */
  public int getBuildingLimitation(BuildingType buildingType) {
    switch (buildingType) {
      case WOOD:
        return buildingLimitation.wood;
      case STONE:
        return buildingLimitation.stone;
      case INHABITANT:
        return buildingLimitation.coin;
      case BROTHEL:
        return amusementLimitation.brothel;
      case CHURCH:
        return amusementLimitation.church;
      case COURT:
        return amusementLimitation.court;
      case ALCHEMIST:
        return amusementLimitation.alchemist;
      case PARK:
        return amusementLimitation.park;
      case TAVERN:
        return amusementLimitation.tavern;
      case RANGE:
        return amusementLimitation.range;
      case SMITH:
        return amusementLimitation.smith;
      case SMALLTOWER:
        return amusementLimitation.smallTower;
      case STABLE:
        return amusementLimitation.stable;
      default:
        return buildingLimitation.coin;
    }
    //return -1;
  }

  public void setInhabitants(int inhabitants) {
    this.inhabitants = inhabitants;
  }

  public int getInhabitants() {
    return inhabitants;
  }

  public Color getColor() {
    return color;
  }
}
