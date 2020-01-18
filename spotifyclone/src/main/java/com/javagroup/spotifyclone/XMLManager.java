package com.javagroup.spotifyclone;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLManager implements IManager {
  private AppSystem appSystem = AppSystem.AppSystem();

  public static final String xmlFilePath = "songs.xml";

  public void Export() {
    System.out.println("Exportando " + appSystem.GetSongsSize() + " canciones a " + xmlFilePath + "...");
    try {
      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
      Document document = documentBuilder.newDocument();

      Element root = document.createElement("songs");
      document.appendChild(root);

      for (Song song : appSystem.GetSongs()) {
        Element songTag = document.createElement("song");
        root.appendChild(songTag);
        songTag.setAttribute("id", Integer.toString(song.getID()));

        Element songName = document.createElement("name");
        songName.appendChild(document.createTextNode(song.getName()));
        songTag.appendChild(songName);

        Element songAuthor = document.createElement("author");
        songAuthor.appendChild(document.createTextNode(song.getAuthor()));
        songTag.appendChild(songAuthor);

        Element songAlbum = document.createElement("album");
        songAlbum.appendChild(document.createTextNode(song.getAlbum()));
        songTag.appendChild(songAlbum);

        Element songGenre = document.createElement("genre");
        songGenre.appendChild(document.createTextNode(song.getGenre()));
        songTag.appendChild(songGenre);

        Element songDuration = document.createElement("duration");
        songDuration.appendChild(document.createTextNode(song.getDuration()));
        songTag.appendChild(songDuration);
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(document);
      StreamResult result = new StreamResult(new File(xmlFilePath));
      // StreamResult result = new StreamResult(System.out);

      transformer.transform(source, result);
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (TransformerException tfe) {
      tfe.printStackTrace();
    }
  }

  public void Import(String fileName) {
    try {
      File file = new File(fileName);

      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document document = documentBuilder.parse(file);
      document.getDocumentElement().normalize();
      Element rootElement = document.getDocumentElement();

      if (rootElement.getTagName() != "songs") {
        System.out.println("El archivo XML no contiene una etiqueta raíz <songs>.");
        return;
      }

      if (rootElement.hasChildNodes()) {
        NodeList songNodes = rootElement.getElementsByTagName("song");

        for (int count = 0; count < songNodes.getLength(); count++) {
          Node songNode = songNodes.item(count);

          if (songNode.getNodeType() == Node.ELEMENT_NODE) {
            Integer songsSize = appSystem.GetSongsSize();
            Element songElement = (Element) songNode;


            Integer id = ++songsSize;
            String name = songElement.getElementsByTagName("name").item(0).getTextContent();
            String author = songElement.getElementsByTagName("author").item(0).getTextContent();
            String album = songElement.getElementsByTagName("album").item(0).getTextContent();
            String genre = songElement.getElementsByTagName("genre").item(0).getTextContent();
            String[] duration = songElement.getElementsByTagName("duration").item(0).getTextContent().split(":");

            Song song = new Song(id, name, author, album, genre, Byte.parseByte(duration[0]), Byte.parseByte(duration[1]));
            appSystem.AddSong(song);
          }
        }
        // printNode(rootElement.getChildNodes());
      } else {
        System.out.println("El archivo XML no contiene canciones.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}