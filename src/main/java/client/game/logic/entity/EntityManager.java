package client.game.logic.entity;

import client.game.logic.entity.building.Building;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityManager {

  private static Map<UUID, Building> buildings = new HashMap<>();

  /**
   * This method registers a building into the buildings list.
   *
   * @param uuid the building uuid.
   * @param building the building itself.
   */
  public static void register(UUID uuid, Building building) {
    if (!buildings.containsKey(uuid)) {
      buildings.put(uuid, building);
    }
  }

  /**
   * This method gets an id for a specific building.
   *
   * @param building the building the id is requested for.
   * @return the uuid for the building.
   */
  public static UUID getBuildingId(Building building) {
    for (UUID uuid : buildings.keySet()) {
      if (buildings.get(uuid).equals(building)) {
        return uuid;
      }
    }
    return null;
  }

  /**
   * This method gets a building based on its id.
   *
   * @param uuid the building id.
   * @return the building.
   */
  public static Building getBuilding(UUID uuid) {
    if (buildings.containsKey(uuid)) {
      return buildings.get(uuid);
    }
    return null;
  }
}
