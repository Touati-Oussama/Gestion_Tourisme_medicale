package com.example.tourisme_medicale.models;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RendezVousManager {
    private Map<String, Integer> listeClients;
    private final String CSV_FILE_PATH = "D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\clients_statistiques.csv";

    public RendezVousManager() {
        this.listeClients = new HashMap<>();
        loadFromCsv();
    }

    public void bookRendezVous(String patientName) {
        listeClients.put(patientName, listeClients.getOrDefault(patientName, 0) + 1);
        saveToCsv();
    }

    public void printBookedRendezVousCount() {
        System.out.println("Booked RendezVous Count:");
        for (Map.Entry<String, Integer> entry : listeClients.entrySet()) {
            System.out.println("Patient: " + entry.getKey() + ", Booked RendezVous Count: " + entry.getValue());
        }
    }

    private void saveToCsv() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : listeClients.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        this.listeClients.clear();
    }

    private void loadFromCsv() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String patientName = parts[0];
                    int bookedCount = Integer.parseInt(parts[1]);
                    listeClients.put(patientName, bookedCount);
                }
            }
        } catch (IOException | NumberFormatException e) {
            // File doesn't exist or failed to read, continue with an empty map
        }
    }

}
