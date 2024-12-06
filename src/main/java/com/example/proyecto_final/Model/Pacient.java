package com.example.proyecto_final.Model;

public class Pacient implements Comparable<Pacient> {
    // Atributos
    private String name;
    private String lastName;
    private String dateOfBirth;
    private String dni;
    private String phone;
    private String email;
    private String dateOfProcess;
    private String process;
    private int priority; // Atributo para manejar la prioridad

    // Constructor
    public Pacient(String name, String lastName, String dateOfBirth, String dni, String phone, String email, int priority) {
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dni = dni;
        this.phone = phone;
        this.email = email;
        this.priority = priority;
    }

    // Getters y Setters
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getDateOfProcess() {
        return dateOfProcess;
    }

    public void setDateOfProcess(String dateOfProcess) {
        this.dateOfProcess = dateOfProcess;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Método para comparar pacientes según su prioridad (para la cola de prioridad)
    @Override
    public int compareTo(Pacient otherPacient) {
        return Integer.compare(this.priority, otherPacient.priority); // Ordenar por prioridad
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + name + '\'' +
                ", apellido='" + lastName + '\'' +
                ", fecha de nacimiento='" + dateOfBirth + '\'' +
                ", DNI='" + dni + '\'' +
                ", teléfono='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", proceso='" + process + '\'' +
                ", prioridad=" + priority +
                '}';
    }

    public double getProgress() {
        return 50;
    }
}
