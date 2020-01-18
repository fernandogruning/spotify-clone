package com.javagroup.spotifyclone;

import java.util.Map;
// import java.util.Iterator;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Hello world!
 */
public final class ExcelReport {
    private XSSFWorkbook workbook;
    private Sheet sheet;
    private Map<String, Object[]> sheetData;
    private ArrayList<Playlist> Playlists;

    private void PopulateSheet() {
        byte sheetRow = 1;

        this.sheetData.put(Byte.toString(sheetRow++), new Object[] { "id", "nombre_lista", "descripcion", "id_cancion",
                "nombre_cancion", "autor_cancion", "album_cancion", "genero_cancion", "duracion_cancion" });
        for (Playlist playlist : Playlists) {
            for (Song song : playlist.GetSongs()) {
                this.sheetData.put(Byte.toString(sheetRow++),
                        new Object[] { playlist.GetID(), playlist.GetName(), playlist.GetDescription(), song.getID(),
                                song.getName(), song.getAuthor(), song.getAlbum(), song.getGenre(),
                                song.getDuration() });
            }
        }

        Set<String> keyset = this.sheetData.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = this.sheet.createRow(rownum++);
            Object[] objArr = this.sheetData.get(key);
            int cellnum = 0;

            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);

                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                } else if (obj instanceof Byte) {
                    cell.setCellValue((Byte) obj);
                }
            }
        }
    }

    public void SaveExcelReportFile() {
        System.out.println("Generando Reporte en Excel de " + Playlists.size() + " Listas de Reproducción...");
        FileOutputStream out;

        try {
            out = new FileOutputStream(new File("playlists-report.xlsx"));
            this.workbook.write(out);
            out.close();
            this.workbook.close();
            System.out.println("Reporte en Excel generado. Acceda a la raíz del proyecto para visualizar el archivo.");
        } catch (FileNotFoundException fnfe) {
            System.out.println("Excepción: Archivo no encontrado");
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("Excepción: Error al generar Reporte en Excel");
            ioe.printStackTrace();
        }
    }

    public ExcelReport(ArrayList<Playlist> playlists) {
        this.workbook = new XSSFWorkbook();
        this.sheet = this.workbook.createSheet("Listas de Reproducción");
        this.sheetData = new HashMap<String, Object[]>();
        this.Playlists = playlists;

        PopulateSheet();
    }
}
