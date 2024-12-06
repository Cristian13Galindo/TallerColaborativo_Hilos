package com.example.proyecto_final.Logic;

import com.example.proyecto_final.Model.Pacient;

public class PatientWaitHandler implements Runnable {
    private final Pacient patient;
    private final Clinic clinic;

    public PatientWaitHandler(Pacient patient, Clinic clinic) {
        this.patient = patient;
        this.clinic = clinic;
    }

    @Override
    public void run() {
        try {
            int waitTime;
            switch (patient.getPriority()) {
                case 1:
                    waitTime = 30 * 60 * 1000; // 30 minutes
                    break;
                case 2:
                    waitTime = 2 * 60 * 60 * 1000; // 2 hours
                    break;
                case 3:
                    waitTime = 4 * 60 * 60 * 1000; // 4 hours
                    break;
                default:
                    throw new IllegalArgumentException("Invalid priority: " + patient.getPriority());
            }

            for (int i = waitTime; i > 0; i -= 1000) {
                System.out.println("Patient " + patient.getDni() + " will be attended in " + (i / 1000 / 60) + " minutes.");
                Thread.sleep(1000);
            }

            synchronized (clinic) {
                //clinic.attendPatient(patient);
                clinic.notify();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}