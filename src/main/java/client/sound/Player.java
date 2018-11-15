package client.sound;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.MediaPlayer;

import shared.util.Pair;

public class Player {

  private static Playlist songs = new Playlist();
  private static int currentSong = 0;
  private static MediaPlayer mediaPlayer;
  private static List<Pair<Double, MediaPlayer>> envNoise = new ArrayList<>();
  private static double volume = 1;
  private static boolean muted = false;

  public static Playlist getPlaylist() {
    return songs;
  }

  /**
   * This method adss a song to the playlist.
   *
   * @param sound the song to be added.
   */
  public static void addSong(Sound sound) {
    songs.add(sound);
  }

  /**
   * This method removes a song from a playlist.
   *
   * @param sound the song to be removed.
   */
  public static void removeSong(Sound sound) {
    if (songs.getSounds().indexOf(sound) == currentSong) {
      next();
    }
    songs.remove(sound);

  }

  /**
   * This method removes all songs from a playlist.
   */
  public static void removeAll() {
    List<Sound> sounds = new ArrayList<>(getPlaylist().getSounds());
    for (Sound sound : sounds) {
      songs.remove(sound);
    }
    currentSong = 0;
    stop();
  }

  /**
   * This method plays the next song.
   */
  public static void next() {
    currentSong++;
    if (currentSong > songs.getSounds().size() - 1) {
      currentSong = 0;
    }
    if (mediaPlayer != null) {
      mediaPlayer.setOnEndOfMedia(() -> {
      });
      mediaPlayer.setOnError(() -> {
      });
      mediaPlayer.stop();

    }
    play();
  }

  /**
   * This method plays the previous song.
   */
  public static void prev() {
    currentSong--;
    if (currentSong < 0) {
      currentSong = songs.getSounds().size() - 1;
    }
    stop();
    play();
  }

  /**
   * This method sets the volume of the playlist.
   *
   * @param vol the volume, which should be set.
   */
  public static void setVolume(double vol) {
    volume = vol;
    if (mediaPlayer != null) {
      mediaPlayer.setVolume(volume);
    }
  }

  /**
   * This method starts the playlist and plays the current song. It also checks if sounds were muted
   * or not.
   */
  public static void play() {
    Sound sound = songs.getSounds().get(currentSong);
    if (mediaPlayer == null) {
      mediaPlayer = new MediaPlayer(sound.getMedia());
    } else {
      if (!sound.getMedia().equals(mediaPlayer.getMedia())) {
        mediaPlayer = new MediaPlayer(sound.getMedia());
      }
    }
    mediaPlayer.setVolume(volume);
    mediaPlayer.play();
    if (muted) {
      mediaPlayer.setVolume(0);
    } else {
      mediaPlayer.setVolume(volume);
    }
    mediaPlayer.setOnEndOfMedia(Player::next);
    mediaPlayer.setOnError(Player::next);

  }

  /**
   * This method stops the playlist.
   */
  public static void stop() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
  }

  /**
   * This method pauses the playlist.
   */
  public static void pause() {
    if (mediaPlayer != null) {
      mediaPlayer.pause();
    }
  }


  /**
   * This method plays a sound by a given sound object. It automatically sets the volume and plays
   * it on repeat if repeat was set to true.
   *
   * @param sound the sound, which should be played.
   * @param vol its volume.
   * @param repeat a boolean representing whether the sound should be repeated or not.
   * @return the sound-id, to identify the sound.
   */
  public static int playSound(Sound sound, double vol, boolean repeat) {
    MediaPlayer myMediaPlayer = new MediaPlayer(sound.getMedia());
    Pair<Double, MediaPlayer> pair = new Pair<>(vol, myMediaPlayer);
    envNoise.add(pair);
    myMediaPlayer.setVolume(vol);
    if (repeat) {
      myMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }
    myMediaPlayer.play();
    if (muted) {
      myMediaPlayer.setVolume(0);
    } else {
      myMediaPlayer.setVolume(vol);
    }
    return envNoise.indexOf(pair);
  }

  /**
   * This method plays a sound by a given sound object. It automatically sets the volume.
   *
   * @param sound the sound, which should be played.
   * @param vol its volume.
   * @return the sound-id, to identify the sound.
   */
  public static int playSound(Sound sound, double vol) {
    return playSound(sound, vol, false);
  }

  /**
   * This method stops a sound by a given id.
   *
   * @param id the id of the sound, which should be stopped.
   */
  public static void stopSound(int id) {
    Pair<Double, MediaPlayer> pair = envNoise.get(id);
    if (pair != null) {
      MediaPlayer myMediaPlayer = pair.getElement();
      myMediaPlayer.stop();
      envNoise.remove(id);
    }
  }

  /**
   * This method mutes all sounds, which are playing or will be played.
   */
  public static void toggleMute() {
    muted = !muted;
    for (Pair<Double, MediaPlayer> pair : envNoise) {
      if (pair != null) {
        if (muted) {
          pair.getElement().setVolume(0);
        } else {
          pair.getElement().setVolume(pair.getKey());
        }
      }
    }
    if (muted) {
      mediaPlayer.setVolume(0);
    } else {
      mediaPlayer.setVolume(volume);
    }
  }

  public static boolean isMuted() {
    return muted;
  }
}
