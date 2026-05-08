package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Драйвер-клас для керування списком одягу через консольне меню.
 */
public class Main {
    private static final List<Clothes> inventory = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("Практична робота №5. Студент: Крамськой Іван | Варіант: 5");

        while (running) {
            System.out.println("\n--- МЕНЮ КЕРУВАННЯ ---");
            System.out.println("1. Додати новий одяг");
            System.out.println("2. Вивести весь список");
            System.out.println("3. Завершити роботу");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addNewItem();
                case "2" -> showInventory();
                case "3" -> {
                    running = false;
                    System.out.println("Програму завершено.");
                }
                default -> System.out.println("Помилка: Оберіть пункт від 1 до 3.");
            }
        }
    }

    private static void addNewItem() {
        try {
            System.out.print("Введіть тип одягу: ");
            String type = scanner.nextLine();

            System.out.print("Введіть бренд: ");
            String brand = scanner.nextLine();

            System.out.print("Введіть розмір: ");
            String size = scanner.nextLine();

            System.out.print("Введіть ціну: ");
            String priceStr = scanner.nextLine();
            double price = Double.parseDouble(priceStr);

            Clothes newItem = new Clothes(type, brand, size, price);
            inventory.add(newItem);
            System.out.println("Успішно додано!");

        } catch (NumberFormatException e) {
            System.out.println("Помилка: Ціна повинна бути числом (використовуйте крапку).");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Виникла непередбачувана помилка: " + e.getMessage());
        }
    }

    private static void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            System.out.println("\n--- СПИСОК ОДЯГУ ---");
            for (Clothes item : inventory) {
                System.out.println(item);
            }
        }
    }
}
