package client.ui;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class KeyBinder {

  private static Map<KeyCode, Runnable> keyBinds = new HashMap<>();
  private static Map<KeyCombination, Runnable> combinationBinds = new HashMap<>();

  /**
   * Binds a custom key to an action.
   *
   * @param keyCode the key to bind.
   * @param runnable the action to bind it to.
   */
  public static void bind(KeyCode keyCode, Runnable runnable) {
    if (!keyBinds.containsKey(keyCode)) {
      keyBinds.put(keyCode, runnable);
    }
  }

  /**
   * Binds a custom key combination to an action.
   *
   * @param keyCode the key combination to bind.
   * @param runnable the action to bind it ti.
   */
  public static void bind(KeyCombination keyCode, Runnable runnable) {
    if (!combinationBinds.containsKey(keyCode)) {
      combinationBinds.put(keyCode, runnable);
    }
  }

  /**
   * Executes the custom key bindings on an event.
   *
   * @param event actionable event.
   */
  public static void exec(KeyEvent event) {
    if (keyBinds.containsKey(event.getCode())) {
      keyBinds.get(event.getCode()).run();
    }
    for (KeyCombination keyComb : combinationBinds.keySet()) {
      if (keyComb.match(event)) {
        combinationBinds.get(keyComb).run();
      }
    }
  }

  /**
   * Unbinds a custom key from an action.
   *
   * @param keyCode the key to unbind.
   */
  public static void unbind(KeyCode keyCode) {
    if (keyBinds.containsKey(keyCode)) {
      keyBinds.remove(keyCode);
    }
  }

  /**
   * Unbinds a custom key combination from an action.
   *
   * @param keyCode the key combination to unbind.
   */
  public static void unbind(KeyCombination keyCode) {
    if (combinationBinds.containsKey(keyCode)) {
      combinationBinds.remove(keyCode);
    }
  }

  /**
   * Unbinds all keys and key combinations.
   */
  public static void unbindAll() {
    keyBinds = new HashMap<>();
    combinationBinds = new HashMap<>();
  }
}
