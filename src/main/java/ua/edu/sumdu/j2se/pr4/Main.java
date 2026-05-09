package ua.edu.sumdu.j2se.pr4;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Store store = new Store("Модний Бутик");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №6. Студент: Крамськой Іван | Варіант: 5");
        boolean running = true;

        while (running) {
            System.out.println("\n--- МЕНЮ МАГАЗИНУ '" + store.getStoreName() + "' ---");
            System.out.println("1. Додати новий одяг");
            System.out.println("2. Скопіювати перший товар у списку (Демонстрація копіювання)");
            System.out.println("3. Показати всі товари");
            System.out.println("4. Статистика (Демонстрація статичного поля)");
            System.out.println("5. Вихід");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> addItem();
                case "2" -> copyItem();
                case "3" -> showItems();
                case "4" -> showStats();
                case "5" -> running = false;
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private static void addItem() {
        try {
            System.out.print("Тип: "); String type = scanner.nextLine();
            System.out.print("Бренд: "); String brand = scanner.nextLine();
            System.out.print("Розмір (S, M, L, XL, UNIVERSAL): "); 
            Size size = Size.fromString(scanner.nextLine());
            System.out.print("Ціна: "); double price = Double.parseDouble(scanner.nextLine());

            store.addClothes(new Clothes(type, brand, size, price));
            System.out.println("Додано успішно.");
        } catch (NumberFormatException e) {
            System.out.println("Помилка: Ціна має бути числом.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        }
    }

    private static void copyItem() {
        List<Clothes> items = store.getInventory();
        if (items.isEmpty()) {
            System.out.println("Магазин порожній, нічого копіювати.");
            return;
        }
        try {
            Clothes original = items.get(0);
            Clothes copy = new Clothes(original); // Використання конструктора копіювання
            store.addClothes(copy);
            System.out.println("Перший товар успішно скопійовано!");
        } catch (Exception e) {
            System.out.println("Помилка при копіюванні: " + e.getMessage());
        }
    }

    private static void showItems() {
        List<Clothes> items = store.getInventory();
        if (items.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            items.forEach(System.out.println);
        }
    }

    private static void showStats() {
        System.out.println("--- СТАТИСТИКА ---");
        System.out.println("Товарів зараз у магазині: " + store.getInventoryCount());
        // Використання статичного методу:
        System.out.println("Всього об'єктів Clothes створено за весь час: " + Clothes.getTotalClothesCreated());
    }
}
