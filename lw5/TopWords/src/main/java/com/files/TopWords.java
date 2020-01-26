package com.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TopWords {
  private static Integer topSize;
  private static BufferedReader bufferedReader;
  private static List<HashSet<String>> collection = new ArrayList<>();

  public static void main(String[] args) {
    try {

      if (args.length < 2) {
        throw new IllegalArgumentException("Invalid arguments count. Must be: TopWords.exe <input.txt> <top size>");
      }

      try {
        FileReader fileReader = new FileReader(args[0]);
        bufferedReader = new BufferedReader(fileReader);
      } catch (Exception error) {
        throw new IllegalArgumentException("Can not open file");
      }

      topSize = Integer.parseInt(args[1]);

      if (topSize < 1) {
        throw new IllegalArgumentException("Invalid top size. Size must be greater than 0");
      }

      readWords();
      printResult();

    } catch (Exception error) {
      System.out.println(error.getMessage());
    }
  }

  private static void readWords() throws IOException {
    Map<String, Integer> counters = new HashMap<>();

    String line = bufferedReader.readLine();
    while (line != null) {
      counters.put(line, counters.containsKey(line) ? counters.get(line) + 1 : 1);
      line = bufferedReader.readLine();
    }

    for (int i = 0; i <= counters.size(); ++i) {
      collection.add(new HashSet<>());
    }

    counters.forEach((key, value) -> collection.get(value).add(key));
  }

  private static void printResult() {
    for (int i = collection.size(), size = 1; i != 0; --i, ++size) {
      if (size <= topSize) {
        int counter = i - 1;
        collection.get(counter).forEach(str -> System.out.println(str + ": " + counter));
      }
    }
  }
}
