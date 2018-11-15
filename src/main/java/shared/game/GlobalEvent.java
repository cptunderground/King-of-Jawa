package shared.game;

public class GlobalEvent {

  private Runnable run;
  private int lobbyId;

  /**
   * Creates a GlobalEvent with a specific lobbyId and Runnable.
   *
   * @param lobbyId specific id of a lobby.
   * @param run     Runnable.
   */
  public GlobalEvent(int lobbyId, Runnable run) {
    this.run = run;
    this.lobbyId = lobbyId;
  }

  public void happen() {
    run.run();
  }
}
