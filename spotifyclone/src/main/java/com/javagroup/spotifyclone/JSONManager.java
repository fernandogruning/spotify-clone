package com.javagroup.spotifyclone;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * JSONManager
 */
public class JSONManager implements IManager {
  private AppSystem appSystem = AppSystem.AppSystem();

  public static final String jsonFilePath = "songs.json";

  @SuppressWarnings("unchecked")
  public void Export() {
    System.out.println("Exportando " + appSystem.GetSongsSize() + " canciones a " + jsonFilePath + "...");
    JSONArray songsList = new JSONArray();

    for (Song song : appSystem.GetSongs()) {
      JSONObject songDetails = new JSONObject();
      songDetails.put("id", song.getID());
      songDetails.put("name", song.getName());
      songDetails.put("author", song.getAuthor());
      songDetails.put("album", song.getAlbum());
      songDetails.put("genre", song.getGenre());
      songDetails.put("duration", song.getDuration());
      songsList.add(songDetails);
    }

    try (FileWriter file = new FileWriter(jsonFilePath)) {
      file.write(songsList.toJSONString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public void Import(String fileName) {
    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader(fileName)) {
      Object obj = jsonParser.parse(reader);

      JSONArray songsList = (JSONArray) obj;
      System.out.println("Importando " + songsList.size() + " canciones desde " + fileName);
      songsList.forEach(song -> parseSongObject((JSONObject) song));
    } catch (FileNotFoundException e) {
      System.out.println("Archivo no encontrado");
    } catch (IOException e) {
      System.out.println("No se pudo leer o escribir el archivo.");
    } catch (ParseException e) {
      System.out.println("Error al parsear el archivo.");
    } catch (ClassCastException e) {
      System.out.println("El archivo no tiene el formato de campos requeridos.");
    }
  }

  private void parseSongObject(JSONObject songObj) {
    Integer songsSize = appSystem.GetSongsSize();

    Integer id = ++songsSize;
    String name = (String) songObj.get("name");
    String author = (String) songObj.get("author");
    String album = (String) songObj.get("album");
    String genre = (String) songObj.get("genre");
    String[] duration = ((String) songObj.get("duration")).split(":");

    Song song = new Song(id, name, author, album, genre, Byte.parseByte(duration[0]), Byte.parseByte(duration[1]));
    appSystem.AddSong(song);
  }

  // public JSONManager(ArrayList<Song> songs) {
  //   this.songs = songs;
  // }
}