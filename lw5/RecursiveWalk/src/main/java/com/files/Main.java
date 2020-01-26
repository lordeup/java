package com.files;

public class Main {
  public static void main(String[] args) {
    try {

      if (args.length < 2) {
        throw new IllegalArgumentException("Invalid arguments count. Must be: Main.exe <input.txt> <output.txt>");
      }

      Walk.walk(args[0], args[1]);

    } catch (Exception error) {
      System.out.println(error.getMessage());
    }
  }
}
