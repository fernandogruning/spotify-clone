package com.javagroup.spotifyclone;

import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class FileWriter {
  private Path file;

  public void Write(List<String> lines) throws IOException {
    Files.write(this.file, lines, StandardCharsets.UTF_8);
  }

  public FileWriter(String path) {
    this.file = Paths.get(path);
  }
}