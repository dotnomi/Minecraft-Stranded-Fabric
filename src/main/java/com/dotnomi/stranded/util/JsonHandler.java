package com.dotnomi.stranded.util;

import com.dotnomi.stranded.Stranded;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonHandler<T> {
  private final Gson gson;
  private final Path path;
  private final com.google.common.reflect.TypeToken<T> typeToken;

  public JsonHandler(Path path, Class<T> objectClass) {
    this.path = path;
    this.gson = new GsonBuilder().setPrettyPrinting().create();
    this.typeToken = com.google.common.reflect.TypeToken.of(objectClass);
  }

  public JsonHandler(Path path, com.google.common.reflect.TypeToken<T> typeToken) {
    this.path = path;
    this.gson = new GsonBuilder().setPrettyPrinting().create();
    this.typeToken = typeToken;
  }

  public T read() {
    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      return gson.fromJson(reader, typeToken.getType());
    } catch (IOException exception) {
      Stranded.LOGGER.error("Error while reading the JSON file: {}", path, exception);
      return null;
    } catch (JsonParseException exception) {
      Stranded.LOGGER.error("Error while parsing the JSON file: {}. File may be damaged or invalid.", path, exception);
      return null;
    }
  }

  public void write(T object) {
    try {
      if (!Files.exists(path)) {
        Files.createDirectories(this.path.getParent());
      }

      try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        gson.toJson(object, typeToken.getType(), writer);
      }
    } catch (IOException exception) {
      Stranded.LOGGER.error("Error while writing to the JSON file: {}", path, exception);
    }
  }
}
