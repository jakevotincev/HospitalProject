package ru.jakev.hospitalproject.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DoctorSpeciality {
    DENTIST("Дантист"), SURGEON("Хирург"), PEDIATRICIAN("Педиатр"),
    OPHTHALMOLOGIST("Офтальмолог"), UROLOGIST("Уролог"), GYNECOLOGIST("Гинеколог");

    DoctorSpeciality(String name) {
        this.name = name;
    }

    private final String name;

    @JsonValue
    public String getName() {
        return name;
    }

    public static DoctorSpeciality valueOfName(String name) {
        for (DoctorSpeciality s : values()) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        return null;
    }

}
