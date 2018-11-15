package client.sound;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

  private List<Sound> sounds;

  public Playlist() {
    sounds = new ArrayList<>();
  }

  /**
   * This method adds a sound to the playlist.
   *
   * @param sound the sound to be added.
   */
  public void add(Sound sound) {
    if (!sounds.contains(sound)) {
      sounds.add(sound);
    }
  }

  /**
   * This method removes a sound from the playlist.
   *
   * @param sound the sound to be removed.
   */
  public void remove(Sound sound) {
    if (sounds.contains(sound)) {
      sounds.remove(sound);
    }
  }

  public List<Sound> getSounds() {
    return sounds;
  }


}
