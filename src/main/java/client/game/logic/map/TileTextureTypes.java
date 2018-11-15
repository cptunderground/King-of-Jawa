package client.game.logic.map;


public class TileTextureTypes {

  private static int[] curvedInLeft1 = {0, 1, 1, 1, 1, 1, 1, 1, 1};

  private static int[] curvedInRight1 = {1, 1, 1, 1, 1, 0, 1, 1, 1};


  private static int[] curvedInTop2 = {1, 1, 1, 1, 1, 1, 1, 0, 1};

  private static int[] curvedInBottom1 = {1, 1, 0, 1, 1, 1, 1, 1, 1};

  private static int[] straightLeft1 = {1, 1, 1, 1, 0, 1, 1, 0, 1};
  private static int[] straightLeft2 = {0, 1, 1, 1, -1, 1, 1, 0, 1};
  private static int[] straightLeft3 = {0, 1, 1, 1, 0, 1, 1, 1, 1};
  private static int[] straightLeft4 = {1, 1, 1, 1, 0, 1, 1, 1, 1};

  private static int[] straightRight1 = {1, 1, 0, 0, 1, 0, 1, 1, 1};
  private static int[] straightRight2 = {1, 1, 0, 0, 1, 1, 1, 1, 1};
  private static int[] straightRight3 = {1, 1, 1, 0, 1, 0, 1, 1, 1};
  private static int[] straightRight4 = {1, 1, 1, 0, 1, 1, 1, 1, 1};
  private static int[] straightRight5 = {1, 1, 0, 1, 1, 0, 1, 1, 1};

  private static int[] straightTop1 = {1, 1, 1, 1, 1, 0, 0, 0, 1};
  private static int[] straightTop2 = {1, 1, 1, 1, 1, 0, 0, 1, 1};
  private static int[] straightTop3 = {1, 1, 1, 1, 1, 1, 0, 0, 1};
  private static int[] straightTop4 = {1, 1, 1, 1, 1, 0, 1, 0, 1};
  private static int[] straightTop5 = {1, 1, 1, 1, 1, 1, 0, 1, 1};

  private static int[] straightBottom1 = {0, 0, 0, 1, 1, 1, 1, -1, 1};
  private static int[] straightBottom2 = {1, 0, -1, 1, 1, 1, 1, 1, 1};
  private static int[] straightBottom3 = {0, 0, 1, 1, 1, 1, 1, 1, 1};
  private static int[] straightBottom4 = {0, 1, 0, 1, 1, 1, 1, 1, 1};


  private static int[] curvedOutLeft1 = {0, 0, 0, 1, 0, 1, 1, 0, 1};
  private static int[] curvedOutLeft2 = {0, 0, -1, 1, 0, 1, 1, 0, 1};
  private static int[] curvedOutLeft3 = {0, 0, 0, 1, 0, 1, 1, -1, 1};
  private static int[] curvedOutLeft4 = {0, 0, 1, 1, 0, 1, 1, 1, 1};
  private static int[] curvedOutLeft5 = {0, 0, 1, 1, 1, 1, 1, 0, 1};
  private static int[] curvedOutLeft6 = {0, 1, 0, 1, 0, 1, 1, -1, 1};
  private static int[] curvedOutLeft7 = {1, 1, 0, 1, 0, 1, 1, 0, 1};
  private static int[] curvedOutLeft8 = {1, 1, 0, 1, 0, 1, 1, 1, 1};

  private static int[] curvedOutRight1 = {1, 1, 0, 0, 1, 0, 0, 0, 1};
  private static int[] curvedOutRight2 = {1, 1, -1, 0, 1, 0, 0, 0, 1};
  private static int[] curvedOutRight3 = {1, 1, 0, 0, 1, 0, 0, -1, 1};
  private static int[] curvedOutRight4 = {1, 1, 1, 0, 1, 0, 0, 1, 1};
  private static int[] curvedOutRight5 = {1, 1, 0, 1, 1, 0, 0, -1, 1};
  private static int[] curvedOutRight6 = {1, 1, 0, 0, 1, 0, -1, -1, 1};
  private static int[] curvedOutRight7 = {1, 1, 1, 0, 1, 0, 1, 0, 1};
  private static int[] curvedOutRight8 = {1, 1, 1, 0, 1, 1, 1, 0, 1};
  private static int[] curvedOutRight9 = {1, 1, 0, 1, 1, 1, 0, 0, 1};
  private static int[] curvedOutRight10 = {1, 1, 0, 0, 1, 1, 1, 0, 1};

  private static int[] curvedOutTop1 = {0, 1, 1, 1, 0, 0, 0, 0, 1};
  private static int[] curvedOutTop2 = {-1, 1, 1, 1, 0, 0, 0, 0, 1};
  private static int[] curvedOutTop3 = {0, 1, 1, 1, 0, 0, 0, -1, 1};
  private static int[] curvedOutTop4 = {0, 1, 1, 1, 0, 1, 0, 0, 1};
  private static int[] curvedOutTop5 = {1, 1, 1, 1, 0, 1, 0, 0, 1};
  private static int[] curvedOutTop6 = {0, 1, 1, 1, 0, 0, 1, 0, 1};
  private static int[] curvedOutTop7 = {0, 1, 1, 1, 0, -1, 0, 0, 1};
  private static int[] curvedOutTop8 = {0, 1, 1, 1, 1, 1, 0, 0, 1};
  private static int[] curvedOutTop9 = {1, 1, 1, 1, 0, 0, 1, 0, 1};
  private static int[] curvedOutTop10 = {0, 1, 1, 1, 0, 0, 1, 1, 1};
  private static int[] curvedOutTop11 = {0, 1, 1, 1, 1, 0, 0, 1, 1};
  private static int[] curvedOutTop12 = {0, 1, 1, 1, 1, 0, 0, 0, 1};

  private static int[] curvedOutBottom1 = {0, 0, 0, 0, 1, 0, 1, 1, 1};
  private static int[] curvedOutBottom2 = {-1, 0, 0, 0, 1, 0, 1, 1, 1};
  private static int[] curvedOutBottom3 = {0, 0, 0, 0, 1, -1, 1, 1, 1};
  private static int[] curvedOutBottom4 = {1, 0, 0, 0, 1, 1, 1, 1, 1};
  private static int[] curvedOutBottom5 = {1, 0, 0, 1, 1, 0, 1, 1, 1};
  private static int[] curvedOutBottom6 = {0, 1, 0, 0, 1, -1, 1, 1, 1};
  private static int[] curvedOutBottom7 = {0, 0, 1, 1, 1, 0, 1, 1, 1};
  private static int[] curvedOutBottom8 = {0, 1, 1, 0, 1, 0, 1, 1, 1};
  private static int[] curvedOutBottom9 = {0, 0, 0, 1, 1, 0, 1, 1, 1};
  private static int[] curvedOutBottom10 = {0, 1, 1, 1, 1, 0, 1, 1, 1};

  private static int[] sandLOL1 = {-1, 0, -1, -1, -1, -1, 0, -1, 1};
  private static int[] sandLOL2 = {1, 1, 1, 0, 0, 0, -1, 0, 1};
  private static int[] sandLOL3 = {1, 1, 0, 0, 0, 0, 0, 0, 1};
  private static int[] sandLOL4 = {0, 1, 1, 0, 0, 0, 0, 0, 1};
  private static int[] sandLOL5 = {0, 1, 0, 0, 0, 0, -1, 0, 1};
  private static int[] sandLOL6 = {0, 0, 0, 1, 0, 0, 0, 0, 1};
  private static int[] sandLOL7 = {0, 0, 0, 0, 1, 0, 0, 0, 1};
  private static int[] sandLOL8 = {0, -1, 0, 0, 0, 0, 1, 0, 1};
  private static int[] sandLOL9 = {0, 1, 1, 0, 0, 0, -1, 0, 1};
  private static int[] sandLOL10 = {0, 1, 0, 0, 0, -1, 1, 1, 1};
  private static int[] sandLOL11 = {0, -1, 0, 0, 0, 1, 1, 0, 1};
  private static int[] sandLOL12 = {1, 1, 0, 1, 0, 0, 0, 0, 1};
  private static int[] sandLOL13 = {1, -1, 0, 0, 0, 0, 1, 0, 1};
  private static int[] sandLOL14 = {0, 0, 0, 0, 0, 0, 1, 1, 1};
  private static int[] sandLOL15 = {0, 0, 0, 0, 0, 0, 0, 0, 1};
  private static int[] sandLOL16 = {0, 0, 0, 0, 1, 0, 1, 0, 1};
  private static int[] sandLOL17 = {0, 0, 0, 0, 1, 1, 1, 0, 1};
  private static int[] sandLOL18 = {1, 0, 0, 0, 1, 1, 1, 0, 1};
  private static int[] sandLOL19 = {1, 1, 0, 1, 0, 1, 0, 0, 1};
  private static int[] sandLOL20 = {0, 1, 1, 0, 1, 0, 0, 0, 1};
  private static int[] sandLOL21 = {0, 0, 0, 0, 0, 1, 1, 1, 1};
  private static int[] sandLOL22 = {0, 0, 0, 1, 0, 0, 1, 0, 1};
  private static int[] sandLOL23 = {0, 1, 1, 0, 0, 0, 1, 1, 1};
  private static int[] sandLOL24 = {0, 1, 0, 0, 1, 0, 0, 1, 1};
  private static int[] sandLOL25 = {0, 0, 1, 1, 0, 0, 1, 0, 1};
  private static int[] sandLOL26 = {0, 0, 0, 1, 0, 0, 1, 1, 1};
  private static int[] sandLOL27 = {0, 1, 1, 0, 1, 0, 0, 1, 1};
  private static int[] sandLOL28 = {0, 0, 1, 1, 0, 0, 1, 1, 1};
  private static int[] sandLOL29 = {1, 0, 0, 0, 1, 0, 1, 0, 1};
  private static int[] sandLOL30 = {1, 1, 0, 0, 0, 1, 1, 0, 1};
  private static int[] sandLOL31 = {0, 1, 0, 1, 0, 1, 0, 0, 1};
  private static int[] sandLOL32 = {0, 1, 1, 0, 0, 1, 1, 0, 1};
  private static int[] sandLOL33 = {0, 1, 0, 0, 0, 1, 0, 0, 1};
  private static int[] sandLOL34 = {1, 1, 0, 0, 0, 1, 1, 1, 1};
  private static int[] sandLOL35 = {0, 1, 0, 0, 0, 0, 1, 0, 1};

  private static int[] middleBridgeTest = {1, 1, 0, 1, 1, 1, 1, 0, 1};

  private static int testCount = 0;

  /**
   * compares a Tile pattern to given pattern and returns the TileType as an Integer.
   *
   * @param compareTo pattern that has to be compared.
   * @return returns the TileTypePattern.
   */
  public static int compareTo(int[][] compareTo) {
    boolean found = false;
    found = compare(straightLeft1, compareTo) || compare(straightLeft2, compareTo) || compare(
        straightLeft3, compareTo) || compare(straightLeft4, compareTo);
    if (found) {
      return 7;
    }
    found = compare(straightRight1, compareTo) || compare(straightRight2, compareTo) || compare(
        straightRight3, compareTo) || compare(straightRight4, compareTo) || compare(straightRight5,
        compareTo);
    if (found) {
      return 5;
    }
    found = compare(straightTop1, compareTo) || compare(straightTop2, compareTo) || compare(
        straightTop3, compareTo) || compare(straightTop4, compareTo) || compare(straightTop5,
        compareTo);
    if (found) {
      return 6;
    }
    found = compare(straightBottom1, compareTo) || compare(straightBottom2, compareTo) || compare(
        straightBottom3, compareTo) || compare(straightBottom4, compareTo);
    if (found) {
      return 4;
    }
    found = compare(curvedInLeft1, compareTo) || compare(curvedInLeft1, compareTo);
    if (found) {
      return 0;
    }
    found = compare(curvedInRight1, compareTo) || compare(curvedInRight1, compareTo);
    if (found) {
      return 2;
    }
    found = compare(curvedInTop2, compareTo) || compare(curvedInTop2, compareTo);
    if (found) {
      return 3;
    }
    found = compare(curvedInBottom1, compareTo) || compare(curvedInBottom1, compareTo);
    if (found) {
      return 1;
    }
    found = compare(curvedOutLeft1, compareTo) || compare(curvedOutLeft2, compareTo) || compare(
        curvedOutLeft3, compareTo) || compare(curvedOutLeft4, compareTo) || compare(curvedOutLeft5,
        compareTo) || compare(curvedOutLeft6, compareTo) || compare(curvedOutLeft7, compareTo)
        || compare(curvedOutLeft8, compareTo);
    if (found) {
      return 8;
    }
    found = compare(curvedOutRight1, compareTo) || compare(curvedOutRight2, compareTo) || compare(
        curvedOutRight3, compareTo) || compare(curvedOutRight4, compareTo) || compare(
        curvedOutRight5, compareTo) || compare(curvedOutRight6, compareTo) || compare(
        curvedOutRight7, compareTo) || compare(curvedOutRight8, compareTo) || compare(
        curvedOutRight9, compareTo) || compare(curvedOutRight10, compareTo);
    if (found) {
      return 10;
    }
    found = compare(curvedOutTop1, compareTo) || compare(curvedOutTop2, compareTo) || compare(
        curvedOutTop3, compareTo) || compare(curvedOutTop4, compareTo) || compare(curvedOutTop5,
        compareTo) || compare(curvedOutTop6, compareTo) || compare(curvedOutTop7, compareTo)
        || compare(curvedOutTop8, compareTo) || compare(curvedOutTop9, compareTo) || compare(
        curvedOutTop10, compareTo) || compare(curvedOutTop11, compareTo) || compare(curvedOutTop12,
        compareTo);
    if (found) {
      return 11;
    }

    found = compare(curvedOutBottom1, compareTo) || compare(curvedOutBottom2, compareTo) || compare(
        curvedOutBottom3, compareTo) || compare(curvedOutBottom4, compareTo) || compare(
        curvedOutBottom5, compareTo) || compare(curvedOutBottom6, compareTo) || compare(
        curvedOutBottom7, compareTo) || compare(curvedOutBottom8, compareTo) || compare(
        curvedOutBottom9, compareTo) || compare(curvedOutBottom10, compareTo);
    if (found) {
      return 9;
    }
    found = compare(sandLOL1, compareTo) || compare(sandLOL2, compareTo) || compare(
        sandLOL3, compareTo) || compare(sandLOL4, compareTo) || compare(
        sandLOL5, compareTo) || compare(sandLOL6, compareTo) || compare(
        sandLOL7, compareTo) || compare(sandLOL8, compareTo) || compare(
        sandLOL9, compareTo) || compare(sandLOL10, compareTo) || compare(
        sandLOL11, compareTo) || compare(sandLOL12, compareTo) || compare(
        sandLOL13, compareTo) || compare(sandLOL14, compareTo) || compare(
        sandLOL15, compareTo) || compare(sandLOL16, compareTo) || compare(
        sandLOL17, compareTo) || compare(sandLOL18, compareTo) || compare(
        sandLOL19, compareTo) || compare(sandLOL20, compareTo) || compare(
        sandLOL21, compareTo) || compare(sandLOL22, compareTo) || compare(
        sandLOL23, compareTo) || compare(sandLOL24, compareTo) || compare(
        sandLOL25, compareTo) || compare(sandLOL26, compareTo) || compare(
        sandLOL27, compareTo) || compare(sandLOL28, compareTo) || compare(
        sandLOL29, compareTo) || compare(sandLOL30, compareTo) || compare(
        sandLOL31, compareTo) || compare(sandLOL32, compareTo) || compare(
        sandLOL33, compareTo) || compare(sandLOL34, compareTo) || compare(
        sandLOL35, compareTo);
    if (found) {
      return 13;
    }
    found =
        compare(middleBridgeTest, compareTo);
    if (found) {
      return 14;
    }
    return 12;
  }

  private static boolean compare(int[] current, int[][] compareTo) {

    if (current.length != compareTo.length) {
      return false;
    }
    if (compareTo[8][2] == 1) {
      for (int i = 0; i < current.length; i++) {
        if (current[i] != compareTo[i][2] && current[i] != -1) {
          return false;
        }
      }
      return true;
    } else {
      for (int i = 0; i < current.length - 1; i++) {

        if (current[i] != compareTo[i][2] && current[i] != -1) {
          return false;
        }
      }
      return compareTo[8][2] == 0;
    }

  }

}
