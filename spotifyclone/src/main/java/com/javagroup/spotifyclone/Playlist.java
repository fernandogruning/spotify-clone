package com.javagroup.spotifyclone;

import java.util.ArrayList;

public class Playlist {
  private int id;
  private String name;
  private String description;
  // duracion
  private ArrayList<Song> Songs;

  public int GetID() {
    return this.id;
  }

  public void SetID(int id) {
    this.id = id;
  }

  public String GetName() {
    return this.name;
  }

  public void SetName(String name) {
    this.name = name;
  }

  public String GetDescription() {
    return this.description;
  }

  public void SetDescription(String description) {
    this.description = description;
  }

  public ArrayList<Song> GetSongs() {
    return this.Songs;
  }

  public Boolean AddSongs(Song song) {
    return this.Songs.add(song);
  }

  public Boolean AddSongs(ArrayList<Song> songs) {
    return this.Songs.addAll(songs);
  }

  public boolean RemoveSongs(int id) {
    Song foundSong = this.Songs.stream().filter(song -> song.getID() == id).findAny().orElse(null);

    return this.Songs.remove(foundSong);
  }

  public Playlist(int id, String name, String description) {
    this.Songs = new ArrayList<Song>();
    this.id = id;
    this.name = name;
    this.description = description;
  }
}