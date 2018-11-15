package shared.net.protocol.packages;

import java.net.Socket;
import java.util.UUID;

import javafx.scene.paint.Color;
import server.user.User;
import shared.game.building.BuildingType;
import shared.net.protocol.Package;
import shared.util.Serialization;

public class GamePackage extends Package {

  public GamePackage(Socket s, String callName) {
    super(callName, PackageType.GAME, s);
  }

  public GamePackage(Package p) {
    super(p);
  }


  public void setMap(String mapName) {
    setData("mapName", mapName);
  }

  /**
   * Gets the map name from the package as String.
   *
   * @return the ma name as String.
   */
  public String getMapName() {
    if (getData("mapName") == null) {
      return null;
    }
    return getData("mapName");
  }

  public void setResourceToUpdate(String resourceName, int amount) {
    setData("res:" + resourceName, amount + "");
  }

  /**
   * Gets the resource which should be updated from the package as String and parses the amount of
   * the resource to an int.
   *
   * @return the as.
   */
  public int getResourceToUpdate(String resourceName) {
    if (getData("res:" + resourceName) == null) {
      return 0;
    }
    return Integer.parseInt(getData("res:" + resourceName));
  }

  public void setRequestedBuilding(BuildingType requestedBuilding) {
    setData("building", requestedBuilding.toString());
  }

  /**
   * Gets the requested building from the package as String.
   *
   * @return the requested building as String.
   */
  public String getRequestedBuilding() {
    if (getData("building") == null) {
      return null;
    }
    return getData("building");
  }

  public void setTilePosition(String pos, int position) {
    setData("buildingPosition:" + pos, position + "");
  }

  /**
   * Gets the tile position from the package as String and parses it to an int.
   *
   * @return the tile position as int.
   */
  public int getTilePosition(String pos) {
    if (getData("buildingPosition:" + pos) == null) {
      return 0;
    }
    return Integer.parseInt(getData("buildingPosition:" + pos));
  }

  public void setIslandId(int island) {
    setData("islandId", island + "");
  }

  /**
   * Gets the island id from the package as String and parses it to an int.
   *
   * @return the island id as int.
   */
  public int getIslandId() {
    if (getData("islandId") == null) {
      return 0;
    }
    return Integer.parseInt(getData("islandId"));
  }

  public void setColor(String color) {
    setData("color", color);
  }


  /**
   * Gets the color from the package as String and parses it to an enum.
   *
   * @return the color as enum.
   */
  public Color getColor() {
    if (getData("color") == null) {
      return null;
    }
    return Color.valueOf(getData("color"));
  }

  public void setBuildingToBuild(String s) {
    setData("buildingClass", s);
  }

  public String getBuildingToBuild() {
    return getData("buildingClass");
  }

  public void setEntityUUid(UUID uuid) {
    setData("entityUUID", uuid.toString());
  }

  /**
   * Gets the entity UUID from the package as String and parses it to a UUID.
   *
   * @return the entity UUID as UUID.
   */
  public UUID getEntityUUid() {
    if (getData("entityUUID") == null) {
      return null;
    }
    return UUID.fromString(getData("entityUUID"));
  }

  public void setBuildingInfo(String infoType, int infoAmount) {
    setData("buildingInfo:" + infoType, infoAmount + "");
  }

  /**
   * This method gets the building-info based on a type.
   *
   * @param infoType the info-type.
   * @return the info
   */
  public int getBuildingInfo(String infoType) {
    if (getData("buildingInfo:" + infoType) == null) {
      return 0;
    }
    return Integer.parseInt(getData("buildingInfo:" + infoType));
  }

  public void setBuildingOwner(User user) {
    setData("buildingOwner", user.getUUid().toString());
  }

  /**
   * This method retrieves the owner of a specific building.
   *
   * @return the uuid of the owner.
   */
  public UUID getBuildingOwner() {
    if (getData("buildingOwner") == null) {
      return null;
    }
    return UUID.fromString(getData("buildingOwner"));
  }

}
