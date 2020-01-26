package com.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class Walk {
  private static final Integer FNV_32_PRIME = 0x01000193;
  private static final Integer FNV_32_INIT = 0x811c9dc5;
  private static final String ERROR_HASH = "00000000";

  static void walk(String inputFileName, String outputFileName) throws IOException {

    try (
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            FileWriter fileWriter = new FileWriter(outputFileName)
    ) {
      String line = bufferedReader.readLine();
      while (line != null) {
        List<Path> paths = Files.walk(Paths.get(line)).collect(Collectors.toList());
        line = bufferedReader.readLine();

        for (Path path : paths) {
          File file = path.toFile();

          if (file.exists()) {
            if (file.isFile()) {
              fileWriter.write(Integer.toHexString(FNVHash(file)) + " " + path + "\n");
            }
          } else {
            fileWriter.write(ERROR_HASH + " " + path + "\n");
          }

          fileWriter.flush();
        }
      }
    }
  }

  private static Integer FNVHash(File file) throws IOException {
    int hashValue = FNV_32_INIT;

    try (InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file))) {
      while (streamReader.ready()) {
        hashValue ^= streamReader.read();
        hashValue *= FNV_32_PRIME;
      }
    }

    return hashValue;
  }
}
