package com.example.proyecto_final.Persistence;

import com.example.proyecto_final.Model.Pacient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProcessPersistence {


    private static final String FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\proyecto_final\\Files\\pacients\\";
    public void createJsonFile(Pacient pacient) throws IOException {
        Gson gson = new Gson();
        File file = new File(FILE_PATH + pacient.getDni() + ".json");

        if (!file.exists()) {
            file.createNewFile();
            List<Pacient> pacients = new ArrayList<>();
            pacients.add(pacient);
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(pacients, writer);
            }
        } else {
            Type listType = new TypeToken<ArrayList<Pacient>>(){}.getType();
            List<Pacient> pacients = gson.fromJson(new FileReader(file), listType);
            pacients.add(pacient);
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(pacients, writer);
            }
        }
    }

    public List<Pacient> readJsonFile(String dni) throws IOException {
        Gson gson = new Gson();
        File file = new File(FILE_PATH + dni + ".json");

        if (file.exists()) {
            Type listType = new TypeToken<ArrayList<Pacient>>(){}.getType();
            return gson.fromJson(new FileReader(file), listType);
        } else {
            return new ArrayList<>();
        }
    }
}