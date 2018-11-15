package client.game.logic.map;

public class WaterTextureTypes {

  private static int[] curvedInLeft1 = {0, 0, -1, 1, 0, 1, 1, 0, 0};
  private static int[] curvedInLeft2 = {0, 0, -1, 1, 0, 1, 1, -1, 0};

  private static int[] curvedInBottom1 = {1, 0, 0, 0, 1, 0, 1, 1, 0};
  private static int[] curvedInBottom2 = {0, 0, 1, 0, 1, 0, 1, 1, 0};
  private static int[] curvedInBottom3 = {0, 0, 1, 0, 1, 1, 1, 1, 0};
  private static int[] curvedInBottom4 = {0, 0, 0, 0, 1, 1, 1, 1, 0};
  private static int[] curvedInBottom5 = {0, 0, 0, 0, 1, 0, 1, 1, 0};
  private static int[] curvedInBottom6 = {1, 0, 0, 0, 1, 1, 1, 1, 0};
  private static int[] curvedInBottom7 = {0, 0, 0, 0, 1, 0, 1, 0, 0};
  private static int[] curvedInBottom8 = {1, 0, 0, 1, 1, 1, 1, 1, 0};
  private static int[] curvedInBottom9 = {1, 0, 0, 0, 1, 1, 1, 0, 0};
  private static int[] curvedInBottom10 = {1, 1, 0, 0, 1, 1, 1, 1, 0};

  private static int[] curvedInTop1 = {0, 1, 1, 1, 0, 1, 0, 0, 0};
  private static int[] curvedInTop2 = {1, 1, 1, 1, 0, 0, 0, 0, 0};
  private static int[] curvedInTop3 = {1, 1, 1, 1, 0, 1, 0, 0, 0};
  private static int[] curvedInTop4 = {1, 1, 0, 1, 0, 1, 0, 0, 0};
  private static int[] curvedInTop5 = {0, 1, 1, 1, 0, 0, 0, 0, 0};
  private static int[] curvedInTop6 = {0, 1, 0, 1, 0, 0, 0, 0, 0};

  private static int[] curvedInRight1 = {1, 1, 0, 0, 1, 0, 0, 0, 0};
  private static int[] curvedInRight2 = {1, 1, 0, 0, 1, 0, 0, 1, 0};
  private static int[] curvedInRight3 = {1, 1, 1, 0, 1, 0, 0, 1, 0};
  private static int[] curvedInRight4 = {1, 1, 1, 0, 1, 0, 0, 0, 0};


  private static int[] straightLeft1 = {0, 0, -1, 1, 0, 1, 0, 0, 0};
  private static int[] straightLeft2 = {0, 0, 1, 1, 0, 1, 0, 0, 0};
  private static int[] straightLeft3 = {0, 0, 0, 1, 0, 0, 0, 0, 0};
  private static int[] straightLeft4 = {0, 0, 1, 1, 0, 0, 0, 0, 0};
  private static int[] straightLeft5 = {1, 1, 1, 1, 0, 1, 1, 0, 0};

  private static int[] straightRight1 = {0, 0, 1, 0, 1, 0, 0, 1, 0};
  private static int[] straightRight2 = {1, 0, 0, 0, 1, 0, 0, 1, 0};
  private static int[] straightRight3 = {0, 0, 0, 0, 1, 0, 0, 1, 0};
  private static int[] straightRight4 = {0, 0, 1, 1, 1, 1, 0, 1, 0};
  private static int[] straightRight5 = {1, 0, 1, 1, 1, 1, 0, 0, 0};
  private static int[] straightRight6 = {1, 1, 0, 0, 1, 1, 0, 1, 0};
  private static int[] straightRight7 = {1, 0, 0, 1, 1, 1, 0, 0, 0};
  private static int[] straightRight8 = {1, 1, 0, 0, 1, 1, 0, 0, 0};
  private static int[] straightRight9 = {1, 0, 0, 1, 1, 1, 0, 1, 0};
  private static int[] straightRight10 = {1, 0, 0, 0, 1, 0, 0, 0, 0};
  private static int[] straightRight11 = {0, 0, 0, 0, 1, 0, 0, 0, 0};
  private static int[] straightRight12 = {1, 0, 0, 0, 1, 1, 0, 1, 0};
  private static int[] straightRight13 = {1, 0, 1, 1, 1, 1, 0, 1, 0};
  private static int[] straightRight14 = {1, 1, 0, 0, 1, 0, 1, 1, 0};
  private static int[] straightRight15 = {0, 0, 0, 0, 1, 1, 0, 1, 0};

  private static int[] straightBottom1 = {0, 0, 0, -1, 0, 1, 1, 0, 0};
  private static int[] straightBottom2 = {0, 0, 0, 0, 0, 1, 1, 1, 0};
  private static int[] straightBottom3 = {0, 0, 0, 0, 0, 0, 1, 1, 0};
  private static int[] straightBottom4 = {0, 1, 1, 0, 0, 0, 1, 1, 0};
  private static int[] straightBottom5 = {0, 1, 1, 0, 0, 1, 1, 1, 0};
  private static int[] straightBottom6 = {1, 1, 1, 0, 0, 1, 1, 1, 0};
  private static int[] straightBottom7 = {1, 1, 1, 0, 0, 0, 1, 1, 0};
  private static int[] straightBottom8 = {1, 1, 0, 0, 0, 1, 1, 0, 0};
  private static int[] straightBottom9 = {1, 0, 0, 0, 0, 1, 1, 1, 0};
  private static int[] straightBottom10 = {1, 0, 0, 0, 0, 1, 1, 0, 0};
  private static int[] straightBottom11 = {0, 0, 0, 0, 0, 0, 1, 0, 0};
  private static int[] straightBottom12 = {0, 1, 1, 0, 0, 0, 1, 0, 0};
  private static int[] straightBottom13 = {1, 1, 0, 0, 0, 1, 1, 1, 0};
  private static int[] straightBottom14 = {0, 0, 1, 0, 0, 1, 1, 1, 0};
  private static int[] straightBottom15 = {1, 0, 0, 0, 0, 0, 1, 0, 0};
  private static int[] straightBottom16 = {1, 0, 0, 0, 0, 0, 1, 1, 0};

  private static int[] straightTop1 = {1, 1, 0, 0, 0, 0, 0, 1, 0};
  private static int[] straightTop2 = {1, 1, 0, 0, 0, 0, 0, 0, 0};
  private static int[] straightTop3 = {1, 1, 1, 0, 0, 0, 0, 0, 0};
  private static int[] straightTop4 = {0, 1, 1, 0, 0, 0, 0, 0, 0};
  private static int[] straightTop5 = {0, 1, 0, 0, 0, 0, 0, 0, 0};
  private static int[] straightTop6 = {1, 1, 1, 1, 1, 0, 0, 0, 0};


  private static int[] curvedOutLeft1 = {0, 0, 0, 0, 0, 1, 0, 0, 0};
  private static int[] curvedOutLeft2 = {1, 1, 1, 0, 0, 1, 0, 0, 0};
  private static int[] curvedOutLeft3 = {1, 0, 0, 1, 0, 1, 0, 0, 0};
  private static int[] curvedOutLeft4 = {1, 1, 0, 0, 0, 1, 0, 0, 0};

  private static int[] curvedOutBottom1 = {0, 0, 0, 0, 0, 0, 0, 1, 0};
  private static int[] curvedOutBottom2 = {0, 1, 1, 1, 0, 1, 0, 1, 0};

  private static int[] curvedOutRight1 = {1, 1, 1, 0, 1, 1, 0, 0, 0};
  private static int[] curvedOutRight2 = {1, 0, 0, 0, 0, 0, 0, 0, 0};
  private static int[] curvedOutRight3 = {0, 1, 1, 0, 0, 1, 0, 0, 0};

  private static int[] curvedOutTop1 = {0, 0, 1, 0, 0, 0, 0, 0, 0};
  private static int[] curvedOutTop2 = {0, 1, 1, 1, 0, 0, 1, 1, 1};

  private static int[] specialTightCase1 = {0, 0, 0, 1, 0, 0, 0, 1, 0};
  private static int[] specialTightCase2 = {0, 0, 1, 1, 0, 0, 0, 1, 0};
  private static int[] specialTightCase3 = {0, 1, 1, 1, 0, 0, 0, 1, 0};
  private static int[] specialTightCase4 = {1, 1, 1, 1, 0, 0, 0, 1, 0};
  private static int[] specialTightCase5 = {0, 0, 0, 1, 0, 1, 0, 1, 0};

  private static int[] specialTightCaseCorner1 = {1, 0, 1, 1, 0, 1, 1, 0, 0};
  private static int[] specialTightCaseCorner2 = {1, 0, 0, 1, 0, 1, 1, 0, 0};
  private static int[] specialTightCaseCorner3 = {1, 0, 1, 1, 0, 1, 1, 1, 0};
  private static int[] specialTightCaseCorner4 = {1, 0, 0, 1, 0, 1, 1, 1, 0};

  private static int[] specialTightCaseCornerOther1 = {1, 1, 1, 0, 1, 0, 1, 1, 0};

  /**
   * This method gets the textureType of an specific tile surrounded by
   * an area of tiles and compares them with the presets above.
   *
   * @param compareTo the area.
   * @return the type.
   */
  public static int compareTo(int[][] compareTo) {
    boolean found = false;
    found = compare(curvedInLeft1, compareTo) || compare(curvedInLeft2, compareTo) || compare(
        curvedInLeft1, compareTo);
    if (found) {
      return 0;
    }
    found = compare(curvedInBottom1, compareTo) || compare(curvedInBottom2, compareTo) || compare(
        curvedInBottom3, compareTo) || compare(curvedInBottom4, compareTo) || compare(
        curvedInBottom5, compareTo) || compare(curvedInBottom6, compareTo) || compare(
        curvedInBottom7, compareTo) || compare(curvedInBottom8, compareTo) || compare(
        curvedInBottom9, compareTo) || compare(curvedInBottom10, compareTo);
    if (found) {
      return 1;
    }
    found = compare(curvedInRight1, compareTo) || compare(curvedInRight2, compareTo) || compare(
        curvedInRight3, compareTo) || compare(curvedInRight4, compareTo);
    if (found) {
      return 2;
    }
    found = compare(curvedInTop1, compareTo) || compare(curvedInTop2, compareTo) || compare(
        curvedInTop3, compareTo) || compare(curvedInTop4, compareTo) || compare(
        curvedInTop5, compareTo) || compare(curvedInTop6, compareTo);
    if (found) {
      return 3;
    }
    found = compare(straightBottom1, compareTo) || compare(straightBottom2, compareTo) || compare(
        straightBottom3, compareTo) || compare(straightBottom4, compareTo) || compare(
        straightBottom5, compareTo) || compare(straightBottom6, compareTo) || compare(
        straightBottom7, compareTo) || compare(straightBottom8, compareTo) || compare(
        straightBottom9, compareTo) || compare(straightBottom10, compareTo) || compare(
        straightBottom11, compareTo) || compare(straightBottom12, compareTo) || compare(
        straightBottom13, compareTo) || compare(straightBottom14, compareTo) || compare(
        straightBottom15, compareTo) || compare(straightBottom16, compareTo);
    if (found) {
      return 4;
    }
    found = compare(straightRight1, compareTo) || compare(straightRight2, compareTo) || compare(
        straightRight3, compareTo) || compare(straightRight4, compareTo) || compare(
        straightRight5, compareTo) || compare(straightRight6, compareTo) || compare(
        straightRight7, compareTo) || compare(straightRight8, compareTo) || compare(
        straightRight9, compareTo) || compare(straightRight10, compareTo) || compare(
        straightRight11, compareTo) || compare(straightRight12, compareTo) || compare(
        straightRight13, compareTo) || compare(straightRight14, compareTo) || compare(
            straightRight15, compareTo);
    if (found) {
      return 5;
    }
    found = compare(straightTop1, compareTo) || compare(straightTop2, compareTo) || compare(
        straightTop3, compareTo) || compare(straightTop4, compareTo) || compare(
        straightTop5, compareTo) || compare(straightTop6, compareTo);
    if (found) {
      return 6;
    }
    found = compare(straightLeft1, compareTo) || compare(straightLeft2, compareTo) || compare(
        straightLeft3, compareTo) || compare(straightLeft4, compareTo) || compare(straightLeft5,
        compareTo);
    if (found) {
      return 7;
    }
    found = compare(curvedOutLeft1, compareTo) || compare(curvedOutLeft2, compareTo) || compare(
        curvedOutLeft3, compareTo) || compare(curvedOutLeft4, compareTo);
    if (found) {
      return 8;
    }
    found = compare(curvedOutBottom1, compareTo) || compare(curvedOutBottom2, compareTo) || compare(
        curvedOutBottom1, compareTo);
    if (found) {
      return 9;
    }
    found = compare(curvedOutRight1, compareTo) || compare(curvedOutRight2, compareTo) || compare(
        curvedOutRight3, compareTo);
    if (found) {
      return 10;
    }
    found = compare(curvedOutTop1, compareTo) || compare(curvedOutTop2, compareTo);
    if (found) {
      return 11;
    }
    found =
        compare(specialTightCase1, compareTo) || compare(specialTightCase2, compareTo) || compare(
            specialTightCase3, compareTo) || compare(specialTightCase4, compareTo) || compare(
            specialTightCase5, compareTo);
    if (found) {
      return 15;
    }
    found =
        compare(specialTightCaseCorner1, compareTo) || compare(specialTightCaseCorner2,
            compareTo) || compare(specialTightCaseCorner3, compareTo) || compare(
            specialTightCaseCorner4, compareTo);
    if (found) {
      return 16;
    }
    found =
        compare(specialTightCaseCornerOther1, compareTo);
    if (found) {
      return 17;
    }
    return 12;
  }

  private static boolean compare(int[] current, int[][] compareTo) {

    if (current.length != compareTo.length) {
      return false;
    }
    for (int i = 0; i < current.length; i++) {
      if (current[i] != compareTo[i][2] && current[i] != -1) {
        return false;
      }
    }

    return true;


  }

}
