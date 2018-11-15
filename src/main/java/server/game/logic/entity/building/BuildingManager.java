package server.game.logic.entity.building;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import server.game.logic.entity.building.amusement.AmusementBuilding;
import server.game.logic.entity.building.resource.ResourceBuilding;
import shared.util.ConsoleLog;

public class BuildingManager<R extends ResourceBuilding, A extends AmusementBuilding> {

  private HashMap<Class, ArrayList<R>> resourceBuildings;
  private HashMap<Class, ArrayList<A>> amusementBuildings;

  public BuildingManager() {
    resourceBuildings = new HashMap<>();
    amusementBuildings = new HashMap<>();
  }

  private <T extends ResourceBuilding> void addResourceBuildingList(Class<T> buildingClass) {
    if (resourceBuildings.containsKey(buildingClass)) {
      ConsoleLog.warning("BuildingList already existing! (" + buildingClass.toString() + ")");
      return;
    }
    resourceBuildings.put(buildingClass, new ArrayList<>());
  }


  private void addAmusementBuildingList(Class<A> buildingClass) {
    if (amusementBuildings.containsKey(buildingClass)) {
      ConsoleLog.warning("BuildingList already existing! (" + buildingClass.toString() + ")");
      return;
    }
    amusementBuildings.put(buildingClass, new ArrayList<>());
  }

  private void addResourceBuilding(R building, Class cls) {
    Class buildingClass = cls;
    if (!resourceBuildings.containsKey(buildingClass)) {
      ConsoleLog.warning(
          "Someone tried to add a building with a non-existing type (" + building.getClass()
              .toString() + ")");
      return;
    }
    ArrayList<R> buildings = resourceBuildings.get(buildingClass);
    if (buildings.contains(building)) {
      ConsoleLog.warning("Building already exists. Check your code for duplicated adding.");
      return;
    }
    buildings.add(building);
  }


  private void addAmusementBuilding(A building, Class cls) {
    Class buildingClass = cls;
    if (!amusementBuildings.containsKey(buildingClass)) {
      ConsoleLog.warning(
          "Someone tried to add a building with a non-existing type (" + building.getClass()
              .toString() + ")");
      return;
    }
    ArrayList<A> buildings = amusementBuildings.get(buildingClass);
    if (buildings.contains(building)) {
      ConsoleLog.warning("Building already exists. Check your code for duplicated adding.");
      return;
    }
    buildings.add(building);
  }

  private ArrayList<R> getResourceBuildings(Class<R> resClass, boolean suppressWarning) {
    if (!resourceBuildings.containsKey(resClass)) {
      if (!suppressWarning) {
        ConsoleLog.warning("No such building-type found (" + resClass.toString() + ")");
      }
      return null;
    }
    return resourceBuildings.get(resClass);
  }

  public ArrayList<R> getResourceBuildings(Class<R> resClass) {
    return getResourceBuildings(resClass, false);
  }


  private ArrayList<A> getAmusementBuildings(Class<A> amuClass, boolean suppressWarning) {
    if (!amusementBuildings.containsKey(amuClass)) {
      if (!suppressWarning) {
        ConsoleLog.warning("No such building-type found (" + amuClass.toString() + ")");
      }
      return null;
    }
    return amusementBuildings.get(amuClass);
  }

  /**
   * This method gets all amusementBuildings of this BuildingManager.
   *
   * @return all AmusementBuildings.
   */
  public List<A> getAllAmusementBuildings() {
    List<A> ret = new ArrayList<>();
    for (List<A> buildingLists : amusementBuildings.values()) {
      ret.addAll(buildingLists);
    }
    return ret;
  }

  /**
   * Gets all the buildings which the buildingManager contains.
   *
   * @param typeClass the corresponding class to the building.
   * @param <T> generic type which extends the Building class.
   * @return all buildings as an ArrayList.
   */
  public <T extends Building> ArrayList<T> getBuildings(Class typeClass) {
    ArrayList<T> ret = null;

    if (typeClass.equals(ResourceBuilding.class) || typeClass.equals(Building.class)) {
      ret = new ArrayList<>();
      for (ArrayList<R> building : resourceBuildings.values()) {
        ret.addAll((ArrayList<T>) building);
      }
    }
    if (typeClass.equals(AmusementBuilding.class) || typeClass.equals(Building.class)) {
      if (ret == null) {
        ret = new ArrayList<>();
      }
      for (ArrayList<A> building : amusementBuildings.values()) {
        ret.addAll((ArrayList<T>) building);
      }
    }

    if (ret == null) {
      ret = getResourceBuildings(typeClass, true);
    }
    if (ret == null) {
      ret = getAmusementBuildings(typeClass, true);
    }

    return ret;
  }

  /**
   * Adds this building to the buildingManager object, where it's handled.
   *
   * @param building the building to be added.
   * @param cls the corresponding class.
   */
  public void addBuilding(Building building, Class cls) {
    System.out.println("Hans1");
    if (cls.cast(building) instanceof ResourceBuilding) {
      if (!resourceBuildings.containsKey(cls)) {
        addResourceBuildingList(cls);
      }
      addResourceBuilding((R) building, cls);
      return;
    }
    if (cls.cast(building) instanceof AmusementBuilding) {
      System.out.println("Hans2: " + cls.toString());
      if (!amusementBuildings.containsKey(cls)) {
        addAmusementBuildingList(cls);
      }
      addAmusementBuilding((A) building, cls);
      return;
    }
  }

}
