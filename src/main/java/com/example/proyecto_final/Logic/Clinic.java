package com.example.proyecto_final.Logic;

import com.example.proyecto_final.Model.Pacient;
import com.example.proyecto_final.Persistence.PacientPersistence;

import java.util.ArrayList;
import java.util.List;

public class Clinic {
    private PriorityQueueHeap<Pacient> patientQueue;
    private PacientPersistence pacientPersistence;
    private List<Pacient> attendedPatients; // List to keep track of attended patients

    /**
     * Constructor de la clase Clinic.
     * Inicializa la cola de prioridad de pacientes y la persistencia.
     */
    public Clinic() {
        patientQueue = new PriorityQueueHeap<>();
        pacientPersistence = new PacientPersistence();
        attendedPatients = new ArrayList<>();
    }

    public synchronized void addPatient(Pacient patient) {
        patientQueue.put(patient);
        pacientPersistence.savePacient(patient);
        System.out.println("Paciente agregado: " + patient);
        new Thread(new PatientWaitHandler(patient, this)).start();
        notify();
    }

    public synchronized Pacient getNextPatient() throws InterruptedException {
        while (patientQueue.isEmpty()) {
            wait();
        }
        return patientQueue.poll();
    }

    /**
     * Atiende al siguiente paciente en la cola de prioridad.
     * Si la cola está vacía, muestra un mensaje indicando que no hay pacientes para atender.
     */
    public void attendPatient(Pacient patient) {
        // Simulate attending the patient
        System.out.println("Attending patient: " + patient);
        attendedPatients.add(patient);
    }

    public List<Pacient> getAttendedPatients() {
        return attendedPatients;
    }

    /**
     * Obtiene un paciente por su DNI.
     * @param id El DNI del paciente.
     * @return El paciente con el DNI especificado, o null si no se encuentra.
     */
    public Pacient getPacientByDni(String id) {
        Pacient patient = pacientPersistence.getPacientByDni(id);
        if (patient != null) {
            System.out.println("Paciente encontrado: " + patient);
        } else {
            System.out.println("Paciente con ID " + id + "no encontrado.");
        }
        return patient;
    }

    /**
     * Muestra el número de pacientes en la cola de prioridad.
     */
    public void showQueue() {
        System.out.println("Número de pacientes en la cola de prioridad: " + patientQueue.size());
    }

    /**
     * Elimina un paciente de la cola de prioridad y de la persistencia.
     * @param patient El paciente a eliminar.
     * @return true si el paciente fue eliminado, false en caso contrario.
     */
    public boolean removePatient(Pacient patient) {
        boolean removed = patientQueue.remove(patient);
        if (removed) {
            pacientPersistence.removePacient(patient);
            System.out.println("Paciente eliminado: " + patient);
        } else {
            System.out.println("Paciente no encontrado: " + patient);
        }
        return removed;
    }

    /**
     * Elimina y devuelve el paciente con la mayor prioridad.
     * @return El paciente con la mayor prioridad, o null si no hay pacientes.
     */
    public Pacient removePacientByPriority() {
        if (patientQueue.isEmpty()) {
            return null;
        }
        Pacient pacientToRemove = patientQueue.poll();
        pacientPersistence.removePacient(pacientToRemove);
        return pacientToRemove;
    }

    /**
     * Obtiene una lista de todos los pacientes en la cola de prioridad.
     * @return Una lista de todos los pacientes.
     */
    public List<Pacient> getAllPacients() {
        return new ArrayList<>(patientQueue.getHeap());
    }

}

