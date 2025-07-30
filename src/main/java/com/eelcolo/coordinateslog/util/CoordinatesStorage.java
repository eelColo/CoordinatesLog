package com.eelcolo.coordinateslog.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesStorage {

    private static final Path SAVE_PATH = Path.of("config", "coordslog.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Coordinates>>() {}.getType();

    public static void save(List<Coordinates> coords) {
        try {
            Files.createDirectories(SAVE_PATH.getParent());
            Files.writeString(SAVE_PATH, GSON.toJson(coords));
            System.out.println("Guardado en: " + SAVE_PATH.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Coordinates> load() {
        if (Files.exists(SAVE_PATH)) {
            try {
                String json = Files.readString(SAVE_PATH);
                List<Coordinates> coords = GSON.fromJson(json, LIST_TYPE);
                if (coords != null) {
                    return coords;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
