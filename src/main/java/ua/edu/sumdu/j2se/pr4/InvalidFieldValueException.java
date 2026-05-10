package ua.edu.sumdu.j2se.pr4;

/**
 * Власний виняток для некоректних значень полів (наприклад, від'ємна ціна).
 */
public class InvalidFieldValueException extends RuntimeException {
    public InvalidFieldValueException(String message) {
        super(message);
    }
}
