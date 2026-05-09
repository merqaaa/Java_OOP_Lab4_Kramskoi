package ua.edu.sumdu.j2se.pr4;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Store store = new Store("Модний Бутик");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №6. Студент: Крамськой Іван");
        boolean running = true;

        while (running) {
            System.out.println("\n--- МЕНЮ '" + store.getStoreName() + "' ---");
            System.out.println("1. Додати товар");
            System.out.println("2. Скопіювати перший товар");
            System.out.println("3. Показати всі");
            System.out.println("4. Статистика");
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
            System.out.println("Додано!");
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void copyItem() {
        List<Clothes> items = store.getInventory();
        if (items.isEmpty()) {
            System.out.println("Магазин порожній.");
            return;
        }
        store.addClothes(new Clothes(items.get(0)));
        System.out.println("Копію створено!");
    }

    private static void showItems() {
        List<Clothes> items = store.getInventory();
        if (items.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            for (Clothes c : items) {
                System.out.println(c);
            }
        }
    }

    private static void showStats() {
        System.out.println("В магазині: " + store.getInventoryCount());
        System.out.println("Всього об'єктів Clothes створено: " + Clothes.getTotalClothesCreated());
    }
}
