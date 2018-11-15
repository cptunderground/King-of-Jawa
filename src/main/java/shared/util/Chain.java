package shared.util;

import java.util.HashMap;
import java.util.Map;

public class Chain {

  private static Map<String, String> escapeStrings = new HashMap<>();
  private String key;
  private Object value;
  private Chain next;
  private Chain prev;

  /**
   * Creates a chain object, which can be appended to other chains and can get other chains
   * appended.
   *
   * @param key the key of the chain element.
   * @param value the value of the chain element.
   */
  public Chain(String key, Object value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Creates a chain object, which can be appended to other chains and can get other chains
   * appended.
   *
   * @param key the key of an chain element without any value
   */
  public Chain(String key) {
    if (!key.equals("")) {
      this.key = key;
      this.value = null;
    } else {
      //TODO: Add denial!
    }
  }

  //TODO make escaping characters bijective to integers (e.g. escapeChars.get(0) is always "*")

  /**
   * With this method, strings can be escaped that it doesn't interfere with toString.
   *
   * @param s string which has to be escape.
   * @return escaped string.
   */
  public static String escape(String s) {
    String ret = s;
    for (Map.Entry<String, String> entry : escapeStrings.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (key.equals("*")) {
        ret = ret.replace(key, value);
      }
    }
    for (Map.Entry<String, String> entry : escapeStrings.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (!key.equals("*")) {
        ret = ret.replace(key, value);
      }
    }
    return ret;
  }

  /**
   * With this method, strings can be unescaped to regain the original string.
   *
   * @param s string which has to be unescape.
   * @return unescaped string.
   */
  public static String unescape(String s) {
    String ret = s;
    for (Map.Entry<String, String> entry : escapeStrings.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (!key.equals("*")) {
        ret = ret.replace(value, key).replace(value, key);
      }
    }
    for (Map.Entry<String, String> entry : escapeStrings.entrySet()) {
      String k;
      k = entry.getKey();
      String value = entry.getValue();
      if (k.equals("*")) {
        ret = ret.replace(value, k);
      }
    }
    return ret;
  }

  /**
   * Registering escapeStings which will be escaped.
   *
   * @param val string which has to be escaped.
   * @param replacement escaping string.
   */
  public static void registerEscapeString(String val, String replacement) {
    escapeStrings.put(val, replacement);
  }

  /**
   * Returns key of this chain element.
   *
   * @return key
   */
  public String getKey() {
    return key;
  }

  /**
   * Returns escaped key or just key.
   *
   * @param escaped boolean which determines return value
   * @return value if it exists
   */
  public String getKey(boolean escaped) {
    if (escaped) {
      return escape(key);
    }
    return key;
  }


  /**
   * Returns value if it exists.
   *
   * @return value if it exists
   */
  public Object getValue() {
    return value;
  }

  /**
   * Returns escaped value or just the value.
   *
   * @param escaped boolean which determines return value
   * @return value if it exists
   */
  public String getValue(boolean escaped) {
    if (value == null) {
      return "";
    }
    if (escaped) {
      return Chain.escape((String) value);
    }
    return (String) value;
  }

  /**
   * Sets value of this chainelement.
   *
   * @param value sets this value
   */
  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * Returns next chainelement.
   *
   * @return next
   */
  public Chain getNext() {
    return next;
  }

  /**
   * Sets this to next chainelement.
   *
   * @param next sets this next chainelement
   */
  public void setNext(Chain next) {
    this.next = next;
  }

  /**
   * Returns previous chainelement.
   *
   * @return prev
   */
  public Chain getPrev() {
    return prev;
  }

  /**
   * Sets this prev to previous chainelement.
   *
   * @param prev sets this previous chainelement
   */
  public void setPrev(Chain prev) {
    this.prev = prev;
  }

  /**
   * Returns last chainelement.
   *
   * @return pivot
   */
  public Chain getLast() {
    Chain pivot = this;
    while (pivot.getNext() != null) {
      pivot = pivot.getNext();
    }
    return pivot;
  }

  /**
   * Inserts the value of an existing key which is inserted, if the key doesn't exist it appends
   * it.
   *
   * @param key key of the value which has to be inserted.
   * @param value value
   */
  public void insert(String key, Object value) {
    Chain valueToChange = find(key);
    if (valueToChange == null) {
      append(key, value);
    } else {
      valueToChange.setValue(value);
    }
  }

  /**
   * Appends chainelement.
   *
   * @param key key of the value which has to be appended.
   * @param value appends new chain element
   */
  public void append(String key, Object value) {
    Chain lastChainElement = getLast();
    Chain chainToAppend = new Chain(key, value);
    lastChainElement.setNext(chainToAppend);
    chainToAppend.setPrev(lastChainElement);
  }

  /**
   * Appends a new chainelement without value.
   *
   * @param key appends new chainelement without value
   */
  public void append(String key) {
    Chain lastChainElement = getLast();
    Chain chainToAppend = new Chain(key);
    lastChainElement.setNext(chainToAppend);
    chainToAppend.setPrev(lastChainElement);
  }

  /**
   * Appends chain.
   *
   * @param c chain which has to be appended.
   */
  public void append(Chain c) {
    Chain lastChainElement = getLast();
    lastChainElement.setNext(c);
    c.setPrev(lastChainElement);
  }

  /**
   * Returns a chain with the given key.
   *
   * @param key key of the value which should be found.
   * @return chainelement with matching key
   */
  public Chain find(String key) {
    Chain pivot = this;
    while (pivot.getPrev() != null) {
      pivot = pivot.getPrev();
    }

    while (!pivot.getKey().equals(key) && pivot.getNext() != null) {
      pivot = pivot.getNext();
    }
    if (pivot.getKey().equals(key)) {
      return pivot;
    } else {
      return null;
    }
  }

  /**
   * Formats the whole chain into one sting.
   *
   * @return chain in formatted string
   */
  public String toString() {
    String s = toString(false);
    return s.substring(1, s.length() - 1);
  }

  /**
   * Formats the whole chain into one sting.
   *
   * @param innerElement a boolean which determines if the first element has to be found or not.
   * @return string of a chain
   */
  public String toString(boolean innerElement) {
    String s;
    Chain p = this;
    Chain next = p.getNext();
    Chain prev = p.getPrev();

    if (!innerElement) {
      while (p.getPrev() != null) {
        p = p.getPrev();
      }
      prev = p.getPrev();
      next = p.getNext();
    }

    if (p.getValue() instanceof Chain) {
      s = "'" + p.getKey(true) + "'|" + ((Chain) p.getValue()).toString(false) + "";
    } else {
      s = "'" + p.getKey(true) + "'|'" + String.valueOf(p.getValue(true)) + "'";
    }

    if (prev == null) {
      s = "{" + s;
    }

    if (next == null) {
      s = s + "}";
    } else {
      s = s + "," + next.toString(true);
    }

    return s;
  }

}
