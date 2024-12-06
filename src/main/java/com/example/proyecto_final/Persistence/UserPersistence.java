package com.example.proyecto_final.Persistence;

import com.example.proyecto_final.Model.Dentist;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserPersistence {
    private static final String FILE_DENTIST = System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\proyecto_final\\Files\\dentists.json";

    public void writeToJson(ArrayList<Dentist> dentists) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FILE_DENTIST)) {
            gson.toJson(dentists, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Dentist getDentistFromJson(String name) {
        List<Dentist> dentists = readDentistFile();
        for (Dentist dentist : dentists) {
            if (dentist.getName().equals(name)) {
                return dentist;
            }
        }
        return null;
    }

    public ArrayList<Dentist> readDentistFile() {
        Gson gson = new Gson();
        File file = new File(FILE_DENTIST);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                writeToJson(new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileReader fr = new FileReader(FILE_DENTIST)) {
            Type type = new TypeToken<ArrayList<Dentist>>() {}.getType();
            return gson.fromJson(fr, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}