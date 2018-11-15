package shared.net.protocol.packages;

import shared.net.protocol.Package;
import shared.util.ConsoleLog;
import shared.util.Serialization;

public enum PackageType {
  CHAT, PING, USER, CONNECTION, HIGHSCORE, LOBBY, GAME, SHAQ;

  /**
   * Casts a packageType to a generic T type.
   *
   * @param p package to be casted.
   * @param <T> generic T type.
   * @return the casted package as generic T type.
   */
  public <T extends Package> T cast(Package p) {
    switch (this) {
      case CHAT:
        return (T) new ChatPackage(p);
      case PING:
        return (T) new PingPackage(p);
      case USER:
        return (T) new UserPackage(p);
      case CONNECTION:
        return (T) new ConnectionPackage(p);
      case HIGHSCORE:
        return (T) new HighscorePackage(p);
      case LOBBY:
        return (T) new LobbyPackage(p);
      case SHAQ:
        return (T) new ShaqPackage(p);
      case GAME:
        return (T) new GamePackage(p);
      default:
        ConsoleLog.warning(
            "This shouldn't be happening anyways, please contact p.buerklin@stud.unibas.ch");
    }
    return null;
  }
}