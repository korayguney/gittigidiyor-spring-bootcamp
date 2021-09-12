package dev.patika.patika0601.exception;

public class EmployeeIsAlreadyExistException extends RuntimeException{
    public EmployeeIsAlreadyExistException(String message) {
        super(message);
    }
}
