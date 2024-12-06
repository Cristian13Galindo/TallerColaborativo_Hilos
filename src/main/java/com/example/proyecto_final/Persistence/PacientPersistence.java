package com.example.proyecto_final.Persistence;

import com.example.proyecto_final.Model.Pacient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PacientPersistence {
    private static final String FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\proyecto_final\\Files\\Pacients.json";
    private final Gson gson = new Gson();

    // Método para guardar un paciente nuevo
    public void savePacient(Pacient pacient) {
        List<Pacient> pacients = getAllPacients();
        pacients.add(pacient);
        writeToFile(pacients);
    }

    public List<Pacient> getAllPacients() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Pacient>>(){}.getType();
            List<Pacient> pacients = gson.fromJson(reader, listType);
            return pacients != null ? pacients : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Pacient getPacientByDni(String dni) {
        List<Pacient> pacients = getAllPacients();
        return pacients.stream()
                .filter(pacient -> pacient.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    public Pacient removePacientByPriority() {
        List<Pacient> pacients = getAllPacients();
        pacients.sort(Comparator.comparingInt(Pacient::getPriority));
        if (!pacients.isEmpty()) {
            Pacient pacientToRemove = pacients.remove(0);
            writeToFile(pacients);
            return pacientToRemove;
        }
        return null;
    }

    public boolean removePacient(Pacient pacient) {
        List<Pacient> pacients = getAllPacients();
        boolean removed = pacients.removeIf(p -> p.getDni().equals(pacient.getDni()));
        if (removed) {
            writeToFile(pacients);
        }
        return removed;
    }

    private void writeToFile(List<Pacient> pacients) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(pacients, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
