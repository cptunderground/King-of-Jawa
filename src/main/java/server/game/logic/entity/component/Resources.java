package server.game.logic.entity.component;

public class Resources {

  public int wood = 0;
  public int stone = 0;
  public int coin = 0;
  public int iron = 0;
  public int copper = 0;

  /**
   * Adds resources to to this resources.
   *
   * @param resources the resources to add.
   * @param limit the limit, which the currency-value can not go beyond.
   * @param factor the factor the currency is multiplied.
   */
  public void addCurrencies(Resources resources, Resources limit, float factor) {
    wood += resources.wood * factor;
    stone += resources.stone * factor;
    coin += resources.coin * factor;
    iron += resources.iron * factor;
    copper += resources.copper * factor;

    wood = Math.min(wood, limit.wood);
    stone = Math.min(stone, limit.stone);
    coin = Math.min(coin, limit.coin);
    iron = Math.min(iron, limit.iron);
    copper = Math.min(copper, limit.copper);
  }

  /**
   * Adds resources to to this resources.
   *
   * @param resources the resources to add.
   * @param factor the factor the currency is multiplied.
   */
  public void addCurrencies(Resources resources, float factor) {
    wood += resources.wood * factor;
    stone += resources.stone * factor;
    coin += resources.coin * factor;
    iron += resources.iron * factor;
    copper += resources.copper * factor;
  }

  /**
   * This method copies another resources object into this.
   *
   * @param resources the other resources object.
   */
  public void set(Resources resources) {
    wood = resources.wood;
    stone = resources.stone;
    coin = resources.coin;
    iron = resources.iron;
    copper = resources.copper;
  }

  /**
   * Takes away resources from this resources.
   *
   * @param resources the resources to be taken off.
   * @param factor the factor the currency is multiplied.
   */
  public void decreaseCurrencies(Resources resources, float factor) {
    wood -= resources.wood * factor;
    stone -= resources.stone * factor;
    coin -= resources.coin * factor;
    iron -= resources.iron * factor;
    copper -= resources.copper * factor;
  }


}
