package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Головний драйвер-клас (Консольна версія).
 * ЛР №16: Додано пошук за унікальним ідентифікатором UUID.
 */
public class Main {
    private static final Store store = new Store();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №16. Студент: Крамськой Іван | Варіант: 5");
        
        // Завантаження даних із JSON
        store.setInventory(FileManager.loadFromFile());
        
        boolean running = true;
        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ МАГАЗИНУ ---");
            System.out.println("1. Пошук об'єкта");
            System.out.println("2. Додати новий товар");
            System.out.println("3. Вивести весь список");
            System.out.println("4. Вивести відсортований список");
            System.out.println("5. Завершити роботу та зберегти дані");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> searchMenu();
                case "2" -> creationMenu();
                case "3" -> showInventory();
                case "4" -> showSortedInventory(); // З ЛР №13-15
                case "5" -> {
                    FileManager.saveToFile(store.getInventory());
                    running = false;
                }
                default -> System.out.println("Помилка вибору.");
            }
        }
    }

    private static void searchMenu() {
        if (store.getInventory().isEmpty()) {
            System.out.println("Магазин порожній.");
            return;
        }
        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. За брендом | 2. За розміром | 3. За макс. ціною | 4. За UUID | 0. Назад");
        System.out.print("Вибір: ");
        String choice = scanner.nextLine();
        try {
            List<StoreItem> results = new ArrayList<>();
            switch (choice) {
                case "1" -> {
                    System.out.print("Введіть бренд: ");
                    results = store.searchByBrand(scanner.nextLine());
                }
                case "2" -> {
                    System.out.print("Введіть розмір: ");
                    results = store.searchBySize(Size.fromString(scanner.nextLine()));
                }
                case "3" -> {
                    System.out.print("Введіть ціну: ");
                    results = store.searchByMaxPrice(Double.parseDouble(scanner.nextLine()));
                }
                case "4" -> {
                    System.out.print("Введіть UUID: ");
                    StoreItem found = store.searchByUuid(scanner.nextLine());
                    if (found != null) results.add(found);
                }
                case "0" -> { return; }
                default -> { System.out.println("Невірний критерій."); return; }
            }
            if (results.isEmpty()) System.out.println("Нічого не знайдено.");
            else results.forEach(System.out::println);
        } catch (Exception e) { 
            System.out.println("Помилка пошуку: " + e.getMessage()); 
        }
    }

    private static void creationMenu() {
        System.out.println("\n--- СТВОРЕННЯ ТОВАРУ ---");
        System.out.println("1. Штани | 2. Джинси | 3. Сорочка | 4. Офіційна сорочка | 0. Назад");
        System.out.print("Вибір: ");
        String typeChoice = scanner.nextLine();
        if (typeChoice.equals("0")) return;
        try {
            System.out.print("Кількість: "); int qty = Integer.parseInt(scanner.nextLine());
            System.out.print("Бренд: "); String brand = scanner.nextLine();
            System.out.print("Розмір: "); Size size = Size.fromString(scanner.nextLine());
            System.out.print("Ціна: "); double price = Double.parseDouble(scanner.nextLine());

            Clothes newItem = switch (typeChoice) {
                case "1" -> new Pants(brand, size, price, 100);
                case "2" -> new Jeans(brand, size, price, 105, "Classic");
                case "3" -> new Shirts(brand, size, price, "Long");
                case "4" -> new DressShirts(brand, size, price, "Long", 40);
                default -> null;
            };

            if (newItem != null) {
                store.addNewClothes(newItem, qty);
                System.out.println("Додано! UUID: " + newItem.getUuid());
            }
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    private static void showInventory() {
        if (store.getInventory().isEmpty()) System.out.println("Порожньо.");
        else store.getInventory().forEach(System.out::println);
    }

    private static void showSortedInventory() {
        List<StoreItem> list = new ArrayList<>(store.getInventory());
        list.sort((a, b) -> a.getItem().compareTo(b.getItem()));
        list.forEach(System.out::println);
    }
}
