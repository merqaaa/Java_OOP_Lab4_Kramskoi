package ua.edu.sumdu.j2se.pr4;

/**
 * Перерахування для фіксованих розмірів одягу.
 */
public enum Size {
    XS, S, M, L, XL, XXL, UNIVERSAL;

    /**
     * Метод для безпечного перетворення введеного рядка у значення Enum.
     * @param input введений рядок розміру
     * @return відповідне значення Size, або кидає виняток, якщо такого немає
     */
    public static Size fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Розмір не може бути порожнім.");
        }
        try {
            return Size.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неіснуючий розмір. Допустимі: XS, S, M, L, XL, XXL, UNIVERSAL.");
        }
    }
}
