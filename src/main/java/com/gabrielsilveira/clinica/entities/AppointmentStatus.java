package com.gabrielsilveira.clinica.entities;

public enum AppointmentStatus {

    SCHEDULED(1),
    CONFIRMED(2),
    COMPLETED(3),
    CANCELLED(4);

    private int code;

    AppointmentStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AppointmentStatus valueOf(int code) {
        for (AppointmentStatus value : AppointmentStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code");
    }
}
