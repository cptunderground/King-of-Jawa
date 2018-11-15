package shared.util;

public class Pair<K, V> {

  private K key;
  private V element;

  public Pair(K key, V value) {
    this.key = key;
    this.element = value;
  }

  public K getKey() {
    return key;
  }

  public V getElement() {
    return element;
  }

}
