package com.example.proyecto_final.Constants;

public enum PriorityLevel {
    LOW(1), MEDIUM(2), HIGH(3);

    private final int level;

    PriorityLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}