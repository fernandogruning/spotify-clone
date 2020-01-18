package com.javagroup.spotifyclone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class App {
  private static AppSystem appSystem = AppSystem.AppSystem();

  public static void main(String[] args) throws IOException {
    Song c1 = new Song(1, "Seigfried", "Frank Ocean", "Blonde", "Pop", (byte) 5, (byte) 34);
    appSystem.AddSong(c1);
    Song c2 = new Song(2, "Staring At The Sun (feat. SZA)", "Post Malone", "Hollywood's Bleeding", "Pop", (byte) 2,
        (byte) 48);
    appSystem.AddSong(c2);
    Song c3 = new Song(3, "iMi", "Bon Iver", "i,i", "Experimental", (byte) 3, (byte) 16);
    appSystem.AddSong(c3);
    Song c4 = new Song(4, "HIGHEST IN THE ROOM", "Travis Scott", "HIGHEST IN THE ROOM (Single)", "Trap", (byte) 2,
        (byte) 55);
    appSystem.AddSong(c4);
    Song c5 = new Song(5, "Nikes", "Frank Ocean", "Blonde", "Pop", (byte) 5, (byte) 14);
    appSystem.AddSong(c5);
    appSystem.PersistToFile();

    Playlist p1 = new Playlist(1, "urban mix", "best hip-hop music");
    p1.AddSongs(c1);
    p1.AddSongs(c2);
    p1.AddSongs(c5);
    appSystem.AddPlaylist(p1);

    Playlist p2 = new Playlist(2, "top 50", "my top 50 best songs");
    p2.AddSongs(c1);
    p2.AddSongs(c3);
    p2.AddSongs(c4);
    p2.AddSongs(c5);
    appSystem.AddPlaylist(p2);

    MainMenu();
  }

  public static void MainMenu() throws IOException {
    byte selectedOption;

    do {
      System.out.println("Welcome to Spotify");
      System.out.println("--------------------");
      System.out.println("[1] Canciones");
      System.out.println("[2] Listas de reproduccion");
      // System.out.println("[3] Buscar Canciones [PENDIENTE]");
      System.out.println("[4] Generar Reporte de las Listas de Reproducción");
      System.out.println("[5] Importar Canciones");
      System.out.println("[6] Exportar Canciones");
      System.out.println("[0] Salir");

      selectedOption = Byte.parseByte(System.console().readLine());

      switch (selectedOption) {
      case 1:
        System.out.println("Canciones");
        SongsMenu();
        break;
      case 2:
        System.out.println("Listas de reproducción");
        PlaylistsMenu();
        break;
      case 3:
        System.out.println("Buscar Canciones [PENDIENTE]");
        break;
      case 4:
        System.out.println("Generar Reporte de las Listas de Reproducción");
        if (appSystem.GetPlaylists().size() > 0) {
          PlaylistReportMenu();
        } else {
          System.out.println("No existen Listas de Reproducción. Cree una Lista de Reproducción.");
        }
        break;
      case 5:
        ImportSongsMenu();
        break;
      case 6:
        System.out.println("Exportar Canciones");
        if (appSystem.GetSongs().size() > 0) {
          ExportSongsMenu();
        } else {
          System.out.println("No existen Canciones. Cree o importe una canción.");
        }
        break;
      case 0:
        System.out.println("Gracias por usar esta app");
        break;
      default:
        System.out.println("Opción inválida, intente de nuevo.");
        break;
      }
    } while (selectedOption != 0);
  }

  public static void SongsMenu() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    byte selectedOption = 0;

    do {
      System.out.println("----- Menú de canciones -----");
      System.out.println("-----------------------------");
      System.out.println("[1] Nueva Canción");
      System.out.println("[2] Buscar Canción");
      System.out.println("[3] Ver todas las Canciones");
      System.out.println("[4] Modificar Canción");
      System.out.println("[5] Eliminar Canción");
      System.out.println("[0] Volver al menú principal");
      System.out.println("---------------------------");
      System.out.print("Seleccione: ");

      selectedOption = Byte.parseByte(System.console().readLine());
      switch (selectedOption) {
      case 1:
        String nombre, autor, album, genero;
        byte minuto, segundo;
        Song nuevaCancion;

        System.out.print("Nombre de canción: ");
        nombre = System.console().readLine();
        System.out.print("Autor de la canción: ");
        autor = System.console().readLine();
        System.out.print("Genere de canción: ");
        genero = System.console().readLine();
        System.out.print("Album de canción: ");
        album = System.console().readLine();
        System.out.print("Tiempo minuto: ");
        minuto = Byte.parseByte(System.console().readLine());
        System.out.print("Tiempo segundo: ");
        segundo = Byte.parseByte(System.console().readLine());

        nuevaCancion = new Song(appSystem.GetSongsSize() + 1, nombre, autor, album, genero, minuto, segundo);
        appSystem.AddSong(nuevaCancion);
        appSystem.PersistToFile();
        break;

      case 2:
        String searchTerm;
        Song foundCancion;

        System.out.print("Escriba el nombre de la canción: ");
        searchTerm = System.console().readLine();

        foundCancion = appSystem.SearchSong(searchTerm);

        if (foundCancion != null) {
          System.out.format("ID: %s", foundCancion.getID());
          System.out.format("Nombre: %s", foundCancion.getName());
          System.out.format("Autor: %s", foundCancion.getAuthor());
          System.out.format("Autor: %s", foundCancion.getAuthor());
          System.out.format("Album: %s", foundCancion.getAlbum());
          System.out.format("Album: %s", foundCancion.getAlbum());
          System.out.format("Duración: %s", foundCancion.getDuration());
        } else {
          System.out.println("Canción no encontrada.");
        }

        br.readLine();
        break;
      case 3:
        ArrayList<Song> allSongs = appSystem.GetSongs();
        for (Song song : allSongs) {
          System.out.format("ID: %s\n", song.getID());
          System.out.format("Nombre: %s\n", song.getName());
          System.out.format("Autor: %s\n", song.getAuthor());
          System.out.format("Autor: %s\n", song.getAuthor());
          System.out.format("Album: %s\n", song.getAlbum());
          System.out.format("Género: %s\n", song.getGenre());
          System.out.format("Duración: %s\n", song.getDuration());
        }
        System.out.println("Presione cualquier tecla para volver al menú anterior...");
        br.readLine();
        break;
      case 4:
        int idToBeUpdated;
        String updatedName, updatedAuthor, updatedAlbum, updatedGenre;
        byte updateMinutes, updatedSeconds;
        Song cancionToBeUpdated;

        System.out.println("Digite el id para modificar: ");
        idToBeUpdated = Integer.parseInt(System.console().readLine());

        cancionToBeUpdated = appSystem.GetSong(idToBeUpdated);

        if (cancionToBeUpdated != null) {
          System.out.println("Nombre de la canción: ");
          updatedName = System.console().readLine();
          System.out.println("Autor de la canción: ");
          updatedAuthor = System.console().readLine();
          System.out.println("Género de la canción: ");
          updatedGenre = System.console().readLine();
          System.out.println("Albúm de la canción: ");
          updatedAlbum = System.console().readLine();
          System.out.println("Minutos: ");
          updateMinutes = Byte.parseByte(System.console().readLine());
          System.out.println("Segundos: ");
          updatedSeconds = Byte.parseByte(System.console().readLine());
          ;
          cancionToBeUpdated.setName(updatedName);
          cancionToBeUpdated.setAuthor(updatedAuthor);
          cancionToBeUpdated.setGenre(updatedGenre);
          cancionToBeUpdated.setAlbum(updatedAlbum);
          cancionToBeUpdated.setMinute(updateMinutes);
          cancionToBeUpdated.setSecond(updatedSeconds);
          appSystem.PersistToFile();
        } else {
          System.out.println("No existe!!!");
        }
        break;
      case 5:
        int idToBeDeleted;
        System.out.print("Digite el id: ");
        idToBeDeleted = Integer.parseInt(System.console().readLine());

        ArrayList<Playlist> playlistsWithSong = appSystem.GetPlaylists().stream().filter(playlist -> playlist.GetSongs().stream().filter(song -> song.getID() == idToBeDeleted).findAny().orElse(null) != null).collect(Collectors.toCollection(ArrayList::new));

        if (playlistsWithSong != null) {
          System.out.println("La canción existe en las siguientes listas de reproducción:");
          for (Playlist playlist : playlistsWithSong) {
            System.out.println(" -> " + playlist.GetName());
          }

          System.out.println("\nDesea proceder a borrar la canción?");
          System.out.println("[0] Sí");
          System.out.println("[1] No");

          if (Byte.parseByte(System.console().readLine()) == 0) {
            appSystem.RemoveSong(idToBeDeleted);
            appSystem.PersistToFile();
            System.out.println("Canción eliminada exitosamente.");
          } else {
            System.out.println("Canción no eliminada.");
          }
        } else {
          appSystem.RemoveSong(idToBeDeleted);
          appSystem.PersistToFile();
          System.out.println("Canción eliminada exitosamente.");
        }

        System.out.println("Presione cualquier tecla para volver al menú anterior...");
        br.readLine();
        break;
      case 0:
        break;

      default:
        System.out.println("Por favor digite corrrectamente el numero");
        break;
      }
    } while (selectedOption != 0);
  }

  public static void PlaylistsMenu() throws IOException {
    byte selectedOption;

    do {
      System.out.println("----- Menú de Listas de Reproducción -----");
      System.out.println("------------------------------------------");
      System.out.println("[1] Nueva Lista de Reproducción");
      System.out.println("[2] Ver Listas de Reproducción");
      System.out.println("[3] Buscar Lista de Reproducción");
      System.out.println("[4] Editar Lista de Reproducción");
      System.out.println("[5] Eliminar Lista de Reproducción");
      System.out.println("[0] Volver al menú principal");

      selectedOption = Byte.parseByte(System.console().readLine());

      switch (selectedOption) {
      case 1:
        NewPlaylist();
        break;
      case 2:
        ViewPlaylists();
        break;
      case 3:
        SearchPlaylist();
        break;
      case 4:
        EditPlaylistMenu();
        break;
      case 5:
        RemovePlaylist();
        break;
      case 0:
        break;
      default:
        System.out.println("Opción inválida, intente de nuevo.");
        break;
      }
    } while (selectedOption != 0);
  }

  public static void NewPlaylist() throws IOException {
    Playlist newPlaylist;
    String name;
    String description;
    ArrayList<Song> songsToAdd;

    System.out.println("----- Nueva Lista de Reproducción -----");
    System.out.println("---------------------------------------");
    System.out.println("Nombre:");
    name = System.console().readLine();
    System.out.println("Descripción:");
    description = System.console().readLine();
    songsToAdd = SelectMultipleSongs(appSystem.GetSongs());

    newPlaylist = new Playlist(appSystem.GetPlaylists().size() + 1, name, description);
    newPlaylist.AddSongs(songsToAdd);
    appSystem.AddPlaylist(newPlaylist);
    appSystem.PersistToFile();

    System.out.printf("Lista de Reproducción '%s' creada\n", newPlaylist.GetName());
    System.out.printf("%s Canciones\n", newPlaylist.GetSongs().size());
  }

  public static void ViewPlaylists() {
    ArrayList<Playlist> allPlaylists = appSystem.GetPlaylists();

    System.out.println("----- Ver Listas de Reproducción -----");
    System.out.println("--------------------------------------");

    for (Playlist playlist : allPlaylists) {
      int playlistSongsSize = playlist.GetSongs().size();
      System.out.printf("%s (%s cancion%s) — %s \n", playlist.GetName(), playlistSongsSize,
          playlistSongsSize > 1 ? "es" : "", playlist.GetDescription());
      for (Song song : playlist.GetSongs()) {
        System.out.printf(" -> %s - %s\n", song.getName(), song.getAuthor());
      }
      System.out.println();
    }

    System.out.println("Presione ENTER para volver al menú anterior...");
    System.console().readLine();
  }

  public static void SearchPlaylist() {
    String searchTerm;
    ArrayList<Playlist> foundPlaylists;

    System.out.println("----- Buscar Lista de Reproducción ------");
    System.out.println("-----------------------------------------");
    System.out.println("Buscar por nombre:");
    searchTerm = System.console().readLine();

    foundPlaylists = appSystem.SearchPlaylist(searchTerm);

    if (foundPlaylists.size() > 0) {
      System.out.println("Listas de Reproducción encontradas:");

      for (Playlist playlist : foundPlaylists) {
        int playlistSongsSize = playlist.GetSongs().size();
        System.out.printf("%s (%s cancion%s) [DURACION] — %s \n", playlist.GetName(), playlistSongsSize,
            playlistSongsSize > 1 ? "es" : "", playlist.GetDescription());
      }
    } else {
      System.out.println("Ninguna Lista de Reproducción encontrada.");
    }

    System.out.println("Presione ENTER para volver al menú anterior...");
    System.console().readLine();
  }

  public static void EditPlaylistMenu() throws IOException {
    ArrayList<Playlist> allPlaylists = appSystem.GetPlaylists();
    int selectedIndex;
    Playlist playlistToUpdate;
    byte selectedOption;
    String updatedName;
    String updatedDescription;

    System.out.println("----- Editar Lista de Reproducción -----");
    System.out.println("----------------------------------------");
    System.out.println("Seleccione una Lista de Reproducción:");

    for (int i = 0; i < allPlaylists.size(); i++) {
      Playlist playlist = allPlaylists.get(i);
      Integer playlistMenuPosition = i + 1;

      System.out.printf("[%s] %s (%s cancion%s)\n", playlistMenuPosition, playlist.GetName(),
          playlist.GetSongs().size(), playlist.GetSongs().size() > 1 ? "es" : "");
    }
    selectedIndex = Integer.parseInt(System.console().readLine());
    playlistToUpdate = allPlaylists.get(selectedIndex - 1);

    do {
      System.out.printf("----- Editar '%s' -----\n", playlistToUpdate.GetName());
      System.out.println("----------------------------------------");
      System.out.println("[1] Modificar nombre");
      System.out.println("[2] Modificar descripción");
      System.out.println("[3] Añadir/Remover Canciones Canciones");
      System.out.println("[0] Volver al menú de Listas de Reproducción");

      selectedOption = Byte.parseByte(System.console().readLine());

      switch (selectedOption) {
      case 1:
        System.out.println("Modificar nombre");
        System.out.println("Nuevo nombre:");
        updatedName = System.console().readLine();
        playlistToUpdate.SetName(updatedName);
        break;
      case 2:
        System.out.println("Modificar descripción");
        System.out.println("Nuevo descripción:");
        updatedDescription = System.console().readLine();
        playlistToUpdate.SetDescription(updatedDescription);
        break;
      case 3:
        System.out.println("Añadir/Remover Canciones");
        SelectMultipleSongs(appSystem.GetSongs(), playlistToUpdate.GetSongs());
        break;
      case 0:
        break;
      default:
        System.out.println("Opción inválida, intente de nuevo.");
        break;
      }
    } while (selectedOption != 0);

    appSystem.PersistToFile();
  }

  public static void RemovePlaylist() throws IOException {
    ArrayList<Playlist> allPlaylists = appSystem.GetPlaylists();
    Integer selectedIndex;
    Playlist playlistToRemove;

    System.out.println("----- Remover Lista de Reproducción -----");
    System.out.println("-----------------------------------------");
    System.out.println("Seleccione una Lista de Reproducción para eliminar:");

    for (int i = 0; i < allPlaylists.size(); i++) {
      Playlist playlist = allPlaylists.get(i);
      Integer playlistMenuPosition = i + 1;

      System.out.printf("[%s] %s (%s cancion%s)\n", playlistMenuPosition, playlist.GetName(), allPlaylists.size(),
          allPlaylists.size() > 1 ? "es" : "");
    }
    selectedIndex = Integer.parseInt(System.console().readLine());
    playlistToRemove = allPlaylists.get(selectedIndex - 1);

    if (playlistToRemove.GetSongs().size() > 0) {
      System.out.println("Lista de Reproducción \"" + playlistToRemove.GetName() + "\" contiene " + playlistToRemove.GetSongs().size() + " canciones.");

      System.out.printf("¿Remover '%s'?\n", playlistToRemove.GetName());
      System.out.println("[0] Sí");
      System.out.println("[1] No");

      if (Byte.parseByte(System.console().readLine()) == 0) {
        appSystem.RemovePlaylist(playlistToRemove);
        System.out.printf("'%s' eliminado.\n", playlistToRemove.GetName());
      } else {
        System.out.printf("'%s' no eliminado.\n", playlistToRemove.GetName());
      }
    } else {
      appSystem.RemovePlaylist(playlistToRemove);
      System.out.printf("'%s' eliminado.\n", playlistToRemove.GetName());
    }

    System.out.println("Presione ENTER para volver al menú anterior...");
    System.console().readLine();
    appSystem.PersistToFile();
  }

  public static ArrayList<Song> SelectMultipleSongs(ArrayList<Song> songsList) {
    ArrayList<Song> selectedSongs = new ArrayList<Song>();
    int selectedSongIndex;
    Boolean userHasSelected = false;

    do {
      System.out.println("Seleccionar Canciones:\n");
      // System.out.println("[-1] Volver NO POSIBLE");
      System.out.println("[0] Confirmar selección\n");

      for (int i = 0; i < songsList.size(); i++) {
        Song song = songsList.get(i);
        Boolean isSongSelected = selectedSongs.contains(song);
        byte songMenuPosition = (byte) (i + 1);

        System.out.printf("[%s] %s - %s %n", isSongSelected ? "✓" : songMenuPosition, song.getName(), song.getAuthor());
      }
      selectedSongIndex = Integer.parseInt(System.console().readLine());

      if (selectedSongIndex == 0) {
        if (selectedSongs.size() > 0) {
          userHasSelected = true;
          return selectedSongs;
        } else {
          System.out.println("Tiene que elegir una canción");
        }
      }

      if (selectedSongIndex > 0 && selectedSongIndex <= songsList.size()) {
        if (!selectedSongs.contains(songsList.get(selectedSongIndex - 1))) {
          selectedSongs.add(songsList.get(selectedSongIndex - 1));
        } else {
          selectedSongs.remove(songsList.get(selectedSongIndex - 1));
        }
      }
    } while (!userHasSelected);

    return selectedSongs;
  }

  public static ArrayList<Song> SelectMultipleSongs(ArrayList<Song> songsList, ArrayList<Song> selectedSongs) {
    int selectedSongIndex;
    Boolean userHasSelected = false;

    do {
      System.out.println("Seleccionar Canciones:\n");
      // System.out.println("[-1] Volver NO POSIBLE");
      System.out.println("[0] Confirmar selección\n");

      for (int i = 0; i < songsList.size(); i++) {
        Song song = songsList.get(i);
        Boolean isSongSelected = selectedSongs.contains(song);
        byte songMenuPosition = (byte) (i + 1);

        System.out.printf("[%s] %s - %s %n", isSongSelected ? "✓" : songMenuPosition, song.getName(), song.getAuthor());
      }
      selectedSongIndex = Integer.parseInt(System.console().readLine());

      if (selectedSongIndex == 0) {
        if (selectedSongs.size() > 0) {
          userHasSelected = true;
          return selectedSongs;
        } else {
          System.out.println("Tiene que elegir una canción");
        }
      }

      if (selectedSongIndex > 0 && selectedSongIndex <= songsList.size()) {
        if (!selectedSongs.contains(songsList.get(selectedSongIndex - 1))) {
          selectedSongs.add(songsList.get(selectedSongIndex - 1));
        } else {
          selectedSongs.remove(songsList.get(selectedSongIndex - 1));
        }
      }
    } while (!userHasSelected);

    return selectedSongs;
  }

  public static void PlaylistReportMenu() {
    byte selectedOption;

    do {
      System.out.println("[1] Generer Reporte en Excel");
      System.out.println("[2] Generar Reporte en PDF");
      System.out.println("[0] Volver al menú principal");

      selectedOption = Byte.parseByte(System.console().readLine());

      switch (selectedOption) {
      case 1:
        GenerateExcelReport();
        break;
      case 2:
        GeneratePDFDocument();
        break;
      case 0:
        break;
      default:
        System.out.println("Opción inválida, intente de nuevo.");
        break;
      }
    } while (selectedOption != 0);
  }

  public static void GenerateExcelReport() {
    ExcelReport excelReport = new ExcelReport(appSystem.GetPlaylists());
    excelReport.SaveExcelReportFile();
  }

  public static void GeneratePDFDocument() {
    PDFReport pdfReport = PDFReport.getInstance();
    pdfReport.GenerateReport(appSystem.GetPlaylists());
  }

  public static void ImportSongsMenu() {
    byte selectedOption;

    do {
      System.out.println("[1] Importar Canciones desde XML");
      System.out.println("[2] Importar Canciones desde JSON");
      System.out.println("[0] Volver al menú anterior");

      selectedOption = Byte.parseByte(System.console().readLine());

      switch (selectedOption) {
        case 1:
          System.out.println("Importar desde XML");
          ImportSongsXML();
          break;
        case 2:
          System.out.println("Importar desde JSON");
          ImportSongsJSON();
        break;
        case 0:
          break;
        default:
          System.out.println("Opción inválida, intente de nuevo.");
          break;
      }
    } while (selectedOption != 0);
  }

  private static void ImportSongsXML() {
    XMLManager xmlManager = new XMLManager();

    System.out.println("Escriba la ruta del archivo XML:");
    xmlManager.Import(System.console().readLine());
  }

  private static void ImportSongsJSON() {
    JSONManager jsonManager = new JSONManager();

    System.out.println("Escriba la ruta del archivo JSON:");
    jsonManager.Import(System.console().readLine());
  }

  public static void ExportSongsMenu() {
    byte selectedOption;

    do {
      System.out.println("[1] Exportar Canciones a XML");
      System.out.println("[2] Exportar Canciones a JSON");
      System.out.println("[0] Volver al menú principal");

      selectedOption = Byte.parseByte(System.console().readLine());

      switch (selectedOption) {
      case 1:
        System.out.println("Exportar a XML");
        ExportSongsXML();
        break;
      case 2:
        System.out.println("Exportar a JSON");
        ExportSongsJSON();
        break;
      case 0:
        break;
      default:
        System.out.println("Opción inválida, intente de nuevo.");
        break;
      }
    } while (selectedOption != 0);
  }

  public static void ExportSongsXML() {
    XMLManager xmlManager = new XMLManager();
    xmlManager.Export();
  }

  public static void ExportSongsJSON() {
    JSONManager jsonManager = new JSONManager();
    jsonManager.Export();
  }
}