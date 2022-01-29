package ru.jakev.hospitalproject.exceptions;

public class EntityNotFoundException extends IllegalArgumentException {
    private static final long serialVersionUID = -5619832333414758031L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
