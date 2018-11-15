package client.sound;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.media.Media;

public class Sound {

  private Media mediaFile;
  private String src;
  private static final Map<String, Media> sounds = new HashMap<>();


  /**
   * This is the constructor of a sound object and checks if the requested source already has a
   * media object.
   *
   * @param src the source.
   */
  public Sound(String src) {
    this.src = src;
    if (sounds.containsKey(src)) {
      mediaFile = sounds.get(src);
    } else {
      String mediaUrl = getClass().getResource("/music/" + src).toExternalForm();
      mediaFile = new Media(mediaUrl);
      sounds.put(src, mediaFile);
    }
  }

  public Media getMedia() {
    return mediaFile;
  }

  public String getSource() {
    return src;
  }

}
