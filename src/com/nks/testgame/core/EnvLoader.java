package com.nks.testgame.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
  public static void loadEnv(String filePath) throws IOException {
    Map<String, String> envVars = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.contains("=")) {
          String[] parts = line.split("=", 2);
          if (parts.length == 2) {
            envVars.put(parts[0].trim(), parts[1].trim());
          }
        }
      }
    }
    for (Map.Entry<String, String> entry : envVars.entrySet()) {
      System.setProperty(entry.getKey(), entry.getValue());
    }
  }
}
