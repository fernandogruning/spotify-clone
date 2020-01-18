package com.javagroup.spotifyclone;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.*;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFReport {
  private static PDFReport single_instance;
  private static Document document;

  public void GenerateReport(ArrayList<Playlist> playlists) {
    System.out.println("Generando Reporte en PDF de " + playlists.size() + " Listas de Reproducción...");
    try {
      PdfWriter.getInstance(document, new FileOutputStream("playlists-report.pdf"));

      document.open();
      Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 24, new BaseColor(255, 99, 71));
      Paragraph paragraph1 = new Paragraph("Listas de Reproducción", font1);
      paragraph1.setSpacingAfter(0);
      document.add(paragraph1);

      Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.BLACK);
      Paragraph paragraph2 = new Paragraph(playlists.size() + " Listas de Reproducción creadas", font2);
      paragraph2.setSpacingBefore(0);
      paragraph2.setSpacingAfter(24);
      document.add(paragraph2);

      for (Playlist playlist : playlists) {
        Paragraph plParagraph1 = new Paragraph("Nombre: " + playlist.GetName(), font2);
        document.add(plParagraph1);
        Paragraph plParagraph2 = new Paragraph("Descripción: " + playlist.GetDescription(), font2);
        document.add(plParagraph2);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(24);
        table.setSpacingAfter(64);
        AddTableHeader(table);
        AddTableRows(table, playlist.GetSongs());
        document.add(table);
      }
      document.close();
      System.out.println("Reporte en PDF generado. Acceda a la raíz del proyecto para visualizar el archivo.");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (DocumentException e) {
      e.printStackTrace();
    }
  }

  private static void AddTableHeader(PdfPTable table) {
    Stream.of("ID", "Nombre", "Autor", "Albúm",
        "Género", "Duración")
      .forEach(columnTitle -> {
        columnTitle = columnTitle.toUpperCase();
        PdfPCell header = new PdfPCell();
        header.setPhrase(new Phrase(columnTitle));
        header.setVerticalAlignment(Element.ALIGN_CENTER);
        header.setBackgroundColor(new BaseColor(241, 245, 248));
        header.setBorderColor(new BaseColor(205, 207, 207));
        table.addCell(header);
      });
  }

  private static void AddTableRows(PdfPTable table, ArrayList<Song> songs) {
      for (Song song : songs) {
        table.addCell(Integer.toString(song.getID()));
        table.addCell(song.getName());
        table.addCell(song.getAuthor());
        table.addCell(song.getAlbum());
        table.addCell(song.getGenre());
        table.addCell(song.getDuration());
      }

      ArrayList<PdfPRow> rows = table.getRows();
      for (int count = 0; count < rows.size(); count++) {
        PdfPRow row = rows.get(count);
        for (PdfPCell cell : row.getCells()) {
          if (count % 2 == 0 && count > 0) {
            cell.setBackgroundColor(new BaseColor(255, 239, 235));
          }
          cell.setPadding(8);
          cell.setBorderColor(new BaseColor(228, 228, 228));
          cell.setBorderWidthTop(1);
          cell.setBorderWidthBottom(1);
          cell.setBorderWidthLeft(0);
          cell.setBorderWidthRight(0);
        }
      }
  }

  private PDFReport() {
    document = new Document(new Rectangle(1920, 1080), 32, 32, 48, 48);
  }

  public static PDFReport getInstance() {
    if (single_instance == null) {
      single_instance = new PDFReport();
    }
    return single_instance;
  }
}