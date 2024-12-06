package com.example.proyecto_final.Logic;

import com.example.proyecto_final.Model.Pacient;

import java.util.Random;

public class PatientHandler implements Runnable {
    private final Clinic clinic;

    public PatientHandler(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int priority = random.nextInt(3) + 1;
            Pacient patient = new Pacient("Name", "LastName", "DateOfBirth", "DNI", "Phone", "Email", priority);
            synchronized (clinic) {
                clinic.addPatient(patient);
                clinic.notify();
            }
            try {
                Thread.sleep(1000); // Simulate time between patient arrivals
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}