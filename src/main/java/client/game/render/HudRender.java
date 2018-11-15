package client.game.render;

import client.game.GameContainer;
import client.game.GameManager;
import client.game.logic.entity.EntityManager;
import client.game.logic.entity.building.Building;
import client.game.render.hud.Button;
import client.game.render.hud.HudElement;
import client.game.render.hud.Minimap;
import client.game.render.hud.ResourceLabel;
import client.ui.WindowManager;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import shared.game.building.BuildingType;
import shared.net.protocol.packages.GamePackage;
import shared.util.Area;



public class HudRender implements Renderable {

  private List<HudElement> hudElements;
  private Minimap minimap;
  private Button buttonAlchemist;
  private Button buttonBrothel;
  private Button buttonChurch;
  private Button buttonCourt;
  private Button buttonPark;
  private Button buttonRange;
  private Button buttonSmallTower;
  private Button buttonSmith;
  private Button buttonStable;
  private Button buttonTavern;
  private Button buttonBase;
  private Button buttonInhabitantFarm;
  private Button buttonStoneFarm;
  private Button buttonWoodFarm;
  private ResourceLabel pointsResource;
  private ResourceLabel woodResource;
  private ResourceLabel stoneResource;
  private ResourceLabel coinResource;
  private ResourceLabel fpsLabel;
  private ResourceLabel inhabitantResource;
  private Building currentBuilding = null;

  /**
   * Creates a HudRender object.
   */
  public HudRender() {
    PlayerCamera camera = GameContainer.getCamera();
    int width = camera.getResolution().getX();
    int height = camera.getResolution().getY();
    hudElements = new ArrayList<>();
    minimap = new Minimap(width - 200, 0, 200);
    addHudElement(minimap);
    buttonBase = new Button("Base", width - 200, 220, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.BASE);
        });
    addHudElement(buttonBase);
    buttonInhabitantFarm = new Button("Inhabitant [I]", width - 97, 220, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.INHABITANT);
        });
    addHudElement(buttonInhabitantFarm);
    buttonStoneFarm = new Button("StoneFarm [I]", width - 200, 270, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.STONE);
        });
    addHudElement(buttonStoneFarm);
    buttonWoodFarm = new Button("WoodFarm [I]", width - 97, 270, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.WOOD);
        });
    addHudElement(buttonWoodFarm);
    //CHURCH
    buttonChurch = new Button("Church [I]", width - 200, 330, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.CHURCH);
        });
    addHudElement(buttonChurch);
    //TOWER
    buttonSmallTower = new Button("Tower [I]", width - 97, 330, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.SMALLTOWER);
        });
    addHudElement(buttonSmallTower);
    //BROTHEL
    buttonBrothel = new Button("Brothel [II]", width - 200, 380, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.BROTHEL);
        });
    addHudElement(buttonBrothel);
    //STABLE
    buttonStable = new Button("Stable [II]", width - 97, 380, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.STABLE);
        });
    addHudElement(buttonStable);
    //SMITH
    buttonSmith = new Button("Smith [III]", width - 200, 430, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.SMITH);
        });
    addHudElement(buttonSmith);
    //TAVERN
    buttonTavern = new Button("Tavern [III]", width - 97, 430, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.TAVERN);
        });
    addHudElement(buttonTavern);
    //ALCHEMIST
    buttonAlchemist = new Button("Alchemist [IV]", width - 200, 480, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.ALCHEMIST);
        });
    addHudElement(buttonAlchemist);
    //COURT
    buttonCourt = new Button("Court [IV]", width - 97, 480, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.COURT);
        });
    addHudElement(buttonCourt);
    //RANGE
    buttonRange = new Button("Range [IV]", width - 200, 530, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.RANGE);
        });
    addHudElement(buttonRange);
    //PARK
    buttonPark = new Button("Park [IV]", width - 97, 530, 93, 40,
        (mouseButton, posX, posY) -> {
          GameContainer.setBuildingMode(BuildingType.PARK);
        });
    addHudElement(buttonPark);

    woodResource = new ResourceLabel("0", 100, 0, "log.png");
    addHudElement(woodResource);
    stoneResource = new ResourceLabel("0", 200, 0, "stone.png");
    addHudElement(stoneResource);
    coinResource = new ResourceLabel("0", 300, 0, "coin.png");
    addHudElement(coinResource);
    fpsLabel = new ResourceLabel("0", 0, 0, "fps.png");
    addHudElement(fpsLabel);
    inhabitantResource = new ResourceLabel("0", 400, 0, "inhabitants.png");
    addHudElement(inhabitantResource);
    pointsResource = new ResourceLabel("0/3000", 500, 0, "fps.png");
    addHudElement(pointsResource);
  }

  /**
   * Renders the specific GraphicsContext.
   *
   * @param gc GraphicsContext, which wants to be rendered.
   */
  public void render(GraphicsContext gc) {
    PlayerCamera camera = GameContainer.getCamera();
    int width = camera.getResolution().getX();
    int height = camera.getResolution().getY();
    double mouseX = WindowManager.getGameController().getMouseX();
    double mouseY = WindowManager.getGameController().getMouseY();

    gc.clearRect(0, 0, width, height);

    String text = String.format("%.3f", WindowManager.getFrameRate());
    fpsLabel.setText(text);
    for (HudElement hudElement : hudElements) {
      if (hudElement.getArea().isInArea(mouseX, mouseY)) {
        hudElement.renderHover(gc);
      } else {
        hudElement.render(gc);
      }
    }
  }

  private void drawText(GraphicsContext gc, String text, int x, int y, double r, double g, double b,
                        double a) {
    Paint old = gc.getFill();
    gc.setFill(new Color(r, g, b, a));
    gc.fillText(text, x, y);
    gc.setFill(old);
  }

  /**
   * Returns boolean, whether a position is in a HudArea.
   *
   * @param x x-position
   * @param y y-position
   */
  public boolean isInArea(double x, double y) {
    for (Area a : getHudAreas()) {
      if (a.isInArea(x, y)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sends a specific MouseClick at a position.
   *
   * @param b specific MouseClick.
   */
  public void sendClick(MouseButton b) {
    double mouseX = WindowManager.getGameController().getMouseX();
    double mouseY = WindowManager.getGameController().getMouseY();

    for (HudElement hudElement : hudElements) {
      if (hudElement.getArea().isInArea(mouseX, mouseY)) {
        hudElement.handleMouseInteraction(b, (int) mouseX, (int) mouseY);
      }
    }
  }

  /**
   * Adds a HudElement.
   *
   * @param hudElement specific HudElement.
   */
  public void addHudElement(HudElement hudElement) {
    if (!hudElements.contains(hudElement)) {
      hudElements.add(hudElement);
    }
  }

  public List<Area> getHudAreas() {
    return HudElement.getHudAreas();
  }

  /**
   * This method updates the HudElements for the specific windowSize.
   *
   * @param width  Width of the Window.
   * @param height Height of the Window.
   */
  public void updateWindowResize(double width, double height) {
    PlayerCamera camera = GameContainer.getCamera();
    for (HudElement hudElement : hudElements) {
      hudElement.updateWindowArea(width, height);
    }
    if (minimap.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      minimap.setPosition(w - 200, 0);
    }

    if (buttonBase.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonBase.setPosition(w - 200, 220);
    }
    if (buttonInhabitantFarm.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonInhabitantFarm.setPosition(w - 97, 220);
    }
    if (buttonStoneFarm.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonStoneFarm.setPosition(w - 200, 270);
    }
    if (buttonWoodFarm.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonWoodFarm.setPosition(w - 97, 270);
    }
    if (buttonChurch.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonChurch.setPosition(w - 200, 330);
    }
    if (buttonSmallTower.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonSmallTower.setPosition(w - 97, 330);
    }
    if (buttonBrothel.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonBrothel.setPosition(w - 200, 380);
    }
    if (buttonStable.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonStable.setPosition(w - 97, 380);
    }
    if (buttonSmith.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonSmith.setPosition(w - 200, 430);
    }
    if (buttonTavern.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonTavern.setPosition(w - 97, 430);
    }
    if (buttonAlchemist.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonAlchemist.setPosition(w - 200, 480);
    }
    if (buttonCourt.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonCourt.setPosition(w - 97, 480);
    }
    if (buttonRange.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonRange.setPosition(w - 200, 530);
    }
    if (buttonPark.isUpdateOnResize()) {
      int w = camera.getResolution().getX();
      buttonPark.setPosition(w - 97, 530);
    }

  }

  public void updateMinimap() {
    minimap.update();
  }

  /**
   * Updates Wood, so it is shown correctly in the UI.
   */
  public void updateWood(int resourceAmount) {
    woodResource.setText(resourceAmount + "");
  }

  /**
   * Updates Stone, so it is shown correctly in the UI.
   */
  public void updateStone(int resAmount) {
    stoneResource.setText(resAmount + "");
  }

  public void updateCoins(int resAmount) {
    coinResource.setText(resAmount + "");
  }

  /**
   * Updates Inhabitants, so it is shown correctly in the UI.
   */
  public void updateInhabitants(int resAmount) {
    inhabitantResource.setText(resAmount + "");
  }

  public void updatePoints(int points) {
    pointsResource.setText(points + "/3000");
  }

  /**
   * This method removes the building info.
   */
  public void removeBuildingInfo() {
    Pane toolTip = WindowManager.getGameController().getToolTip();
    toolTip.setDisable(true);
    toolTip.toBack();
    toolTip.setOpacity(0);

  }

  /**
   * This method sets the building info from a gamePackage.
   *
   * @param gamePackage the received gamePackage.
   */
  public void setRenderBuildingInfo(GamePackage gamePackage) {
    Platform.runLater(() -> {
      Building building = EntityManager.getBuilding(gamePackage.getEntityUUid());
      int currentLevel = gamePackage.getBuildingInfo("currentLevel");
      int coinUpgradeCost = gamePackage.getBuildingInfo("upgradeCostCoin");
      int stoneUpgradeCost = gamePackage.getBuildingInfo("upgradeCostStone");
      int woodUpgradeCost = gamePackage.getBuildingInfo("upgradeCostWood");
      int coinIncome = gamePackage.getBuildingInfo("nextLevelIncomeCoin");
      int nextCoinCost = gamePackage.getBuildingInfo("nextLevelCoinCostPM");
      coinIncome = coinIncome - nextCoinCost;
      int woodIncome = gamePackage.getBuildingInfo("nextLevelIncomeWood");
      int stoneIncome = gamePackage.getBuildingInfo("nextLevelIncomeStone");
      int currentStoneIncome = gamePackage.getBuildingInfo("currentIncomeStone");
      int currentWoodIncome = gamePackage.getBuildingInfo("currentIncomeWood");
      int currentCoinIncome = gamePackage.getBuildingInfo("currentIncomeCoin");
      int currentCoinCost = gamePackage.getBuildingInfo("currentCoinCostPM");
      currentCoinIncome = currentCoinIncome - currentCoinCost;
      int maxLevel = gamePackage.getBuildingInfo("maxLevel");

      if (building != null) {
        currentBuilding = building;
        Pane toolTip = WindowManager.getGameController().getToolTip();
        double mouseX1 = WindowManager.getGameController().getMouseX();
        double mouseY1 = WindowManager.getGameController().getMouseY();
        toolTip.setLayoutX(mouseX1 - toolTip.getWidth() / 2);
        toolTip.setLayoutY(mouseY1 - toolTip.getHeight() - 10);

        toolTip.setDisable(false);
        toolTip.toFront();
        toolTip.setOpacity(0.8);
        Label label = (Label) toolTip.lookup("#buildingType");
        label.setText(building.getType() + " (" + currentLevel + "/" + maxLevel + ")");
        label = (Label) toolTip.lookup("#nextLevel");
        label.setText("Level: " + Math.min(currentLevel + 1, maxLevel));

        label = (Label) toolTip.lookup("#upgradeCoinText");
        label.setText("" + coinUpgradeCost);
        label = (Label) toolTip.lookup("#upgradeWoodText");
        label.setText("" + woodUpgradeCost);
        label = (Label) toolTip.lookup("#upgradeStoneText");
        label.setText("" + stoneUpgradeCost);
        label = (Label) toolTip.lookup("#coinIncomeText");
        if (coinIncome < 0) {
          label.setTextFill(Color.RED);
        } else {
          label.setTextFill(Color.GREEN);
        }
        label.setText("" + coinIncome);
        label = (Label) toolTip.lookup("#woodIncomeText");
        label.setText("" + woodIncome);
        label = (Label) toolTip.lookup("#stoneIncomeText");
        label.setText("" + stoneIncome);
        label = (Label) toolTip.lookup("#currentGoldIncome");
        if (currentCoinIncome < 0) {
          label.setTextFill(Color.RED);
        } else {
          label.setTextFill(Color.GREEN);
        }
        label.setText("" + currentCoinIncome);
        label = (Label) toolTip.lookup("#currentWoodIncome");
        label.setText("" + currentWoodIncome);
        label = (Label) toolTip.lookup("#currentStoneIncome");
        label.setText("" + currentStoneIncome);
      }
    });
  }

  /**
   * This method shows the building info.
   *
   * @param building the building the info should be shown for.
   */
  public void requestRenderBuildingInfo(Building building) {
    if (building != null) {
      GameManager.getInstance().requestBuildingInfo(EntityManager.getBuildingId(building));
    }
  }

  public Building getCurrentBuilding() {
    return currentBuilding;
  }


}
