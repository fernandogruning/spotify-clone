package com.javagroup.spotifyclone;

public class Song {
  private int id;
  private String name;
  private String author;
  private byte minute;
  private byte second;
  private String album;
  private String genre;

  public Song(int id, String name, String author, String album, String genre, byte minute, byte second) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.album = album;
    this.minute = minute;
    this.second = second;
    this.genre = genre;
  }

  public short getSecond() {
    return second;
  }

  public void setSecond(byte second) {
    this.second = second;
  }

  public byte getMinute() {
    return minute;
  }

  public void setMinute(byte minute) {
    this.minute = minute;
  }

  public String getDuration() {
    return String.format("%d:%02d", this.minute, this.second);
  }

  public int getID() {
    return id;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setID(int id) {
    this.id = id;
  }
}