package com.javagroup.spotifyclone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppSystem {
  private static AppSystem single_instance = null;

  private ArrayList<Song> Songs;
  private FileWriter songsFileWriter = new FileWriter("songs.txt");
  private ArrayList<Playlist> Playlists;
  private FileWriter playlistsFileWriter = new FileWriter("playlists.txt");

  public ArrayList<Song> GetSongs() {
    return this.Songs;
  }

  public int GetSongsSize() {
    return this.Songs.size();
  }

  public Song GetSong(int id) {
    return this.Songs.stream().filter(song -> song.getID() == id).findFirst().orElse(null);
  }

  public Song SearchSong(String searchTerm) {
    return this.Songs.stream().filter(song -> song.getName().contains(searchTerm)).findFirst().orElse(null);
  }

  public Boolean AddSong(Song song) {
    return this.Songs.add(song);
  }

  public boolean RemoveSong(int id) {
    Song foundSong = this.Songs.stream().filter(song -> song.getID() == id).findAny().orElse(null);

    return this.Songs.remove(foundSong);
  }

  public ArrayList<Playlist> GetPlaylists() {
    return this.Playlists;
  }

  public Boolean AddPlaylist(Playlist playlist) {
    return this.Playlists.add(playlist);
  }

  public ArrayList<Playlist> SearchPlaylist(String searchTerm) {
    return new ArrayList<Playlist>(this.Playlists.stream().filter(playlist -> playlist.GetName().contains(searchTerm))
        .collect(Collectors.toList()));
  }

  public boolean RemovePlaylist(Playlist playlistToRemove) {
    return this.Playlists.remove(playlistToRemove);
  }

  public void PersistToFile() throws IOException {
    List<String> songsLines = this.GetSongs().stream().map(song -> String.format("[%s] %s - %s (%s, %s)", song.getID(),
        song.getName(), song.getAuthor(), song.getAlbum(), song.getGenre())).collect(Collectors.toList());
    songsFileWriter.Write(songsLines);

    ArrayList<String> playlistsLines = new ArrayList<String>();

    for (Playlist playlist : this.GetPlaylists()) {
      playlistsLines.add(String.format("%s - \"%s\"", playlist.GetName(), playlist.GetDescription()));
      for (Song song : playlist.GetSongs()) {
        playlistsLines.add(String.format("    [%s] %s - %s (%s, %s)", song.getID(), song.getName(), song.getAuthor(),
            song.getAlbum(), song.getGenre()));
      }
    }
    playlistsFileWriter.Write(playlistsLines);
  }

  private AppSystem() {
    this.Songs = new ArrayList<Song>();
    this.Playlists = new ArrayList<Playlist>();
  }

  public static AppSystem AppSystem() {
    if (single_instance == null) {
      single_instance = new AppSystem();
    }
    return single_instance;
  }
}