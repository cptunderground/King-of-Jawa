package shared.util;

import java.util.ArrayList;
import java.util.List;

public class Serialization {

  private Chain chain;

  /**
   * This is the constructor. it registers all escaped characters to the chain class.
   */
  public Serialization() {
    Chain.registerEscapeString("'", "*'");
    Chain.registerEscapeString("}", "*}");
    Chain.registerEscapeString("{", "*{");
    Chain.registerEscapeString("[", "*[");
    Chain.registerEscapeString("[", "*]");
    Chain.registerEscapeString(",", "*,");
    Chain.registerEscapeString("|", "*|");
    Chain.registerEscapeString("\n", "*n");
    Chain.registerEscapeString("*", "**");
    clearAll();
  }

  /**
   * Packs the chain into a string.
   *
   * @return packed String.
   **/
  public String pack() {
    return chain.toString();
  }


  /**
   * Unpacks an serialized string and creates a chain out of it.
   *
   * @param data Packed String which has to be unpacked.
   * @return Unpacked Chain.
   **/
  public Chain unpack(String data) {
    String test = data;
    String[] layeredData = getLayeredString(test);
    int[] depths = getDepthOfChainElements(test, layeredData);
    int length = layeredData.length;

    //Wenn der string auf | endet ist das nächste Layer das das v.
    //Wenn der string auf , endet ist das nächste Layer ein neues ChainElement
    //Wenn der string auf ' endet, ist das nächste Layer leer
    //und bedeuted das ende eines chainelements
    Chain ret = null;
    Chain[] layeredChains = new Chain[layeredData.length];
    for (int i = length - 1; i >= 0; i--) {
      layeredChains[i] = generateChainForLayer(layeredData[i]);
      if (layeredData[i].endsWith("|")) {
        if (depths[i] + 1 == depths[i + 1]) {
          String val = layeredData[i];
          String[] elements = val.split("','");
          if (elements.length == 1) {
            if (val.charAt(0) == ',') {
              val = val.substring(1);
            }
            val = val.substring(1, val.length() - 2);
          } else {
            val = elements[elements.length - 1];
            val = val.substring(0, val.length() - 2);
          }

          if (val.charAt(0) == ',') {
            val = val.substring(1);
          }
          layeredChains[i].insert(val, layeredChains[i + 1]);
        }
      }
    }
    Chain[] lastDepthElement = new Chain[getMaxDepth(depths) + 1];
    for (int i = 0; i < length; i++) {
      if (i > 0) {
        if (depths[i] < depths[i - 1]) {
          lastDepthElement[depths[i]].append(layeredChains[i]);
        }
      }
      lastDepthElement[depths[i]] = layeredChains[i];

      if (depths[i] == 0) {
        if (ret == null) {
          if (layeredChains[i] != null) {
            ret = new Chain(layeredChains[i].getKey(), layeredChains[i].getValue());
          }
        } else {
          if (layeredChains[i] != null) {
            ret.append(new Chain(layeredChains[i].getKey(), layeredChains[i].getValue()));
          }
        }
      }
    }
    chain = ret;
    return this.chain;
  }

  /**
   * Gets the maximum depth of the chain.
   *
   * @param array the layeredDepth array.
   * @return the maximum depth of layeredChains.
   **/
  private int getMaxDepth(int[] array) {
    int max = -1;
    for (int i = 0; i < array.length; i++) {
      if (max < array[i]) {
        max = array[i];
      }
    }
    return max;
  }

  /**
   * Gets the specified depth for each layer.
   *
   * @param s unprocessed String which has to be converted.
   * @param layeredData the layered Strings.
   * @return the depth for every layer.
   **/
  private int[] getDepthOfChainElements(String s, String[] layeredData) {
    int depth = 0;
    int layer = 0;
    int[] depths = new int[layeredData.length];
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) != '{' && s.charAt(i) != '}') {
        if (layer < layeredData.length) {
          depths[layer] = depth;
        }
      }
      if ((s.charAt(i) == '{' || s.charAt(i) == '}')) {
        if (i > 0) {
          if (s.charAt(i - 1) != '*') {
            depth += (s.charAt(i) == '}') ? -1 : 1;
            layer++;
          }
        } else {
          depth += (s.charAt(i) == '}') ? -1 : 1;
          layer++;
        }
      }
    }
    return depths;
  }

  /**
   * Converts the string into layers, to make parsing easier.
   *
   * @param str the unproccesed String.
   * @return an array containing the String in different layers, each index means one layer.
   **/
  private String[] getLayeredString(String str) {
    String lenString = str.replace("*{", "").replace("*}", "");
    int count = lenString.length() - lenString.replace("{", "").length();
    count += lenString.length() - lenString.replace("}", "").length();
    int layer = 0;
    String[] simplifiedData = new String[count + 1];
    for (int i = 0; i < str.length(); i++) {

      String elementToAdd = "" + str.charAt(i);
      if (i > 0) {
        if ((str.charAt(i) != '{' && str.charAt(i) != '}') || str.charAt(i - 1) == '*') {
          if (simplifiedData[layer] == null) {
            simplifiedData[layer] = elementToAdd + "";
          } else {
            simplifiedData[layer] = simplifiedData[layer] + elementToAdd;
          }
        }
      } else {
        if (str.charAt(i) != '{' && str.charAt(i) != '}') {
          if (simplifiedData[layer] == null) {
            simplifiedData[layer] = elementToAdd + "";
          } else {
            simplifiedData[layer] = simplifiedData[layer] + elementToAdd;
          }
        }
      }

      if (str.charAt(i) == '{' || str.charAt(i) == '}') {
        if (i > 0) {
          if (str.charAt(i - 1) != '*') {
            layer++;
          }
        } else {
          layer++;
        }
      }
    }

    List<String> layeredDataList = new ArrayList<>();
    int index = 0;
    for (int i = 0; i < simplifiedData.length; i++) {
      if (simplifiedData[i] != null) {
        layeredDataList.add(index, simplifiedData[i]);
        index++;
      }
    }

    return layeredDataList.toArray(new String[0]);
  }

  /**
   * This method is a pre-call method for createChain. it prepares the layerData to be chainified.
   *
   * @param layeredData the string is one element of the layered elements.
   * @return a chain.
   **/
  private Chain generateChainForLayer(String layeredData) {
    return createChain(layeredData);
  }

  /**
   * This method generates a Chain out of a layer.
   *
   * @param v the string is one element of the layered elements.
   * @return a chain.
   **/
  private Chain createChain(String v) {
    String val = v;
    Chain ret = null;
    if (val.endsWith("|")) {
      String[] elements = val.split("','");
      if (elements.length == 1) {
        if (val.charAt(0) == ',') {
          val = val.substring(1);
        }
        val = val.substring(1, val.length() - 2);
        ret = new Chain(val);
      } else {
        val = elements[elements.length - 1];
        String otherElement = elements[0];
        for (int i = 1; i < elements.length - 1; i++) {
          otherElement += "','" + elements[i];
        }
        val = val.substring(0, val.length() - 2);
        ret = createChain(otherElement + ",");
        ret.append(new Chain(Chain.unescape(val)));
      }

    } else {
      if (val.length() > 1) {
        String strToParse = val.substring(1, val.length() - 1);
        if (strToParse != null) {
          String[] keyValuePairs = strToParse.split("','");
          if (!keyValuePairs[0].equals("")) {
            if (keyValuePairs[0].charAt(0) == '\'') {
              keyValuePairs[0] = keyValuePairs[0].substring(1);
            }
          }
          for (String k : keyValuePairs) {
            String[] keyValue = k.split("'\\|'");

            if (ret == null) {
              ret = new Chain(Chain.unescape(keyValue[0]),
                  (keyValue.length == 2) ? Chain.unescape(keyValue[1]) : "");
            } else {
              ret.insert(Chain.unescape(keyValue[0]),
                  (keyValue.length == 2) ? Chain.unescape(keyValue[1]) : "");
            }
          }
        }
      }
    }
    return ret;
  }

  /**
   * Clears chain.
   */
  public void clearAll() {
    this.chain = null;
  }

  /**
   * Appends a new chain with only a key.
   *
   * @param key appends new chainelement without value
   */
  public void append(String key) {
    if (chain == null) {
      chain = new Chain(key);
    } else {
      chain.append(key);
    }
  }

  /**
   * Appends a new chain-element created out of key and value.
   *
   * @param key the key of the chain element.
   * @param value appends new chainelement
   */
  public void append(String key, Object value) {
    if (chain == null) {
      chain = new Chain(key, value);
    } else {
      chain.append(key, value);
    }
  }

  /**
   * Finds a chain-element inside the shared-chain.
   *
   * @param key the key of the chain element.
   * @return chainelement with matching key
   */
  public Chain find(String key) {
    if (chain == null) {
      return null;
    }
    return chain.find(key);
  }

}
