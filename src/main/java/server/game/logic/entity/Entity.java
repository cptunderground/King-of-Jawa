package server.game.logic.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import server.game.GameContainer;
import server.game.logic.entity.component.Component;
import server.game.logic.map.island.Island;
import server.game.logic.map.tile.Tile;
import shared.util.ConsoleLog;

public abstract class Entity {

  private UUID id;
  private Map<Class<? extends Component>, Component> components;
  private static Map<Class, Map<Class<? extends Component>, Component>>
      classComponents = new HashMap<>();
  private GameContainer gameContainer;
  private Class cls;

  /**
   * Creates an Entity and registers its component.
   */
  public Entity(GameContainer gameContainer, Class cls) {
    id = UUID.randomUUID();
    this.gameContainer = gameContainer;
    components = new HashMap<>();
    this.cls = cls;
    register(cls);
  }

  /**
   * Creates an Entity.
   */
  public Entity(GameContainer gameContainer, Class cls, boolean doNotRegister) {
    id = UUID.randomUUID();
    this.gameContainer = gameContainer;
    components = new HashMap<>();
    this.cls = cls;
    if (!doNotRegister) {
      register(cls);
    }
  }

  /**
   * Auto-generated Getter.
   *
   * @return ID of Entity.
   */
  public UUID getId() {
    return id;
  }

  /**
   * This method adds a Component to an Entity, if it doesn't exist already.
   *
   * @param component The Component, which has to be added.
   */
  public <T extends Component> void addComponent(Component component, Class<T> toAdd) {
    if (components.containsKey(toAdd)) {
      ConsoleLog.warning("Trying to add an existing component");
      return;
    }
    components.put(toAdd, component);
  }

  /**
   * Searches the Component of this entity and returns it, if it exists.
   *
   * @param myClass The class of component that exist.
   * @return The Component, if it exists.
   */
  public <T extends Component> T getComponent(Class<T> myClass) {
    if (components.containsKey(myClass)) {
      Component component = components.get(myClass);
      if (myClass.isInstance(component)) {
        return myClass.cast(component);
      } else {
        ConsoleLog.debug("Component: " + myClass.toString() + " was not found!");
        return null;
      }
    } else {
      ConsoleLog.debug("Component : " + myClass.toString() + " was not found!");
      return null;
    }
  }

  /**
   * This method adds a Component to an Entity-Type, if it doesn't exist already.
   *
   * @param comp The Component, which has to be added.
   */
  public static <T extends Component, E extends Entity> void add(Component comp, Class<T> toAdd,
      Class<E> owner) {
    Map<Class<? extends Component>, Component> componentMap = classComponents
        .computeIfAbsent(owner, k -> new HashMap<>());
    componentMap.put(toAdd, comp);
  }

  /**
   * Searches the Component of this entity and returns it, if it exists.
   *
   * @param myClass The class of component that exist.
   * @return The Component, if it exists.
   */
  public static <T extends Component, E extends Entity> T get(Class<T> myClass, Class<E> owner) {
    if (classComponents.containsKey(owner)) {
      if (classComponents.get(owner).containsKey(myClass)) {
        Component component = classComponents.get(owner).get(myClass);
        if (myClass.isInstance(component)) {
          return myClass.cast(component);
        } else {
          return null;
        }
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public GameContainer getGameContainer() {
    return gameContainer;
  }

  protected void register(Class cls) {
    gameContainer.register(this, cls);
  }

  public Class getCls() {
    return cls;
  }

}
