package server.game.logic.entity.component;

public class Wallet extends Component {

  public Resources wallet = new Resources();
  public Resources limitation = new Resources();

  /**
   * Decreases the specific resources given in the resources object.
   * @param resources the amount of which currency should be decreased.
   * @param factor the factor by which it should be decreased.
   */
  public void decreaseAmount(Resources resources, float factor) {
    if (wallet.coin - resources.coin < 0) {
      wallet.coin = 0;
    }
    if (wallet.wood - resources.wood < 0) {
      wallet.wood = 0;
    }
    if (wallet.stone - resources.stone < 0) {
      wallet.stone = 0;
    }
    if (wallet.iron - resources.iron < 0) {
      wallet.iron = 0;
    }
    if (wallet.copper - resources.copper < 0) {
      wallet.copper = 0;
    }
    if (sufficientCurrencies(resources)) {
      wallet.decreaseCurrencies(resources, factor);
    }

  }

  /**
   * Increases the specific resources given in the resources object.
   * @param resources the amount of which currency should be increased.
   * @param factor the factor by which it should be increased.
   */
  public void increaseAmount(Resources resources, float factor) {
    wallet.addCurrencies(resources, limitation, factor);
  }

  /**
   * Checks if there are enough resources provided in the wallet.
   * @param resources the the currency cost which should be compared with the wallet.
   * @return a boolean whether there are enough or not.
   */
  public boolean sufficientCurrencies(Resources resources) {
    return wallet.coin - resources.coin >= 0 && wallet.wood - resources.wood >= 0
        && wallet.stone - resources.stone >= 0 && wallet.iron - resources.iron >= 0
        && wallet.copper - resources.copper >= 0;
  }

  public boolean isBroke() {
    return wallet.coin == 0 && wallet.stone == 0 && wallet.wood == 0 && wallet.copper == 0
        && wallet.iron == 0;
  }

}
