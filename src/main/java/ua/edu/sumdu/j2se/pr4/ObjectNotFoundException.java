package ua.edu.sumdu.j2se.pr4;

/**
 * Власний виняток для ситуацій, коли об'єкт не знайдено в колекції (при update/delete).
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
