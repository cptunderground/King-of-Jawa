package client.game.surprise;

import client.sound.Player;
import client.sound.Sound;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.ShaqPackage;

public class SurpriseManager extends ProtocolManagement implements PackageExecutable {

  private static SurpriseManager instance;

  private SurpriseManager() {
    ProtocolManager.getInstance().registerCaller("play", this);
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return SurpriseManager instance.
   */
  public static SurpriseManager getInstance() {
    if (SurpriseManager.instance == null) {
      SurpriseManager.instance = new SurpriseManager();
    }
    return SurpriseManager.instance;
  }

  @Override
  public <T extends Package> void run(T p) {
    ShaqPackage shaqPackage = p.cast();
    switch (p.getName()) {
      case "play":
        play(shaqPackage.getSound());
        break;
      default:
        return;
    }
  }

  private void play(String sound) {
    Player.playSound(new Sound(sound), 0.1);
  }
}
