package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Clothes> inventory;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №10. Студент: Крамськой Іван | Варіант: 5");
        
        inventory = FileManager.loadFromFile();
        boolean running = true;

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Пошук об'єкта");
            System.out.println("2. Створити новий об'єкт");
            System.out.println("3. Вивести інформацію про всі об'єкти");
            System.out.println("4. Завершити роботу та ЗБЕРЕГТИ дані");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> searchMenu();
                case "2" -> creationMenu();
                case "3" -> showInventory();
                case "4" -> {
                    FileManager.saveToFile(inventory);
                    running = false;
                }
                default -> System.out.println("Помилка вибору.");
            }
        }
    }

    // --- ЛОГІКА ПОШУКУ (Лабораторна №10) ---

    private static void searchMenu() {
        if (inventory.isEmpty()) {
            System.out.println("Колекція порожня. Пошук неможливий.");
            return;
        }

        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. За брендом");
        System.out.println("2. За розміром");
        System.out.println("3. За максимальною ціною");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Оберіть критерій: ");

        String choice = scanner.nextLine();
        List<Clothes> searchResults = new ArrayList<>();

        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("Введіть назву бренду: ");
                    String brand = scanner.nextLine();
                    searchResults = searchByBrand(brand);
                }
                case "2" -> {
                    System.out.print("Введіть розмір (S, M, L, XL...): ");
                    Size size = Size.fromString(scanner.nextLine());
                    searchResults = searchBySize(size);
                }
                case "3" -> {
                    System.out.print("Введіть максимальну ціну: ");
                    double maxPrice = Double.parseDouble(scanner.nextLine());
                    searchResults = searchByMaxPrice(maxPrice);
                }
                case "0" -> { return; }
                default -> {
                    System.out.println("Невірний критерій.");
                    return;
                }
            }
            printSearchResults(searchResults);
        } catch (Exception e) {
            System.out.println("Помилка під час пошуку: " + e.getMessage());
        }
    }

    // Метод пошуку 1: За брендом (без Stream API)
    private static List<Clothes> searchByBrand(String brand) {
        List<Clothes> results = new ArrayList<>();
        for (Clothes item : inventory) {
            if (item.getBrand().equalsIgnoreCase(brand.trim())) {
                results.add(item);
            }
        }
        return results;
    }

    // Метод пошуку 2: За розміром (без Stream API)
    private static List<Clothes> searchBySize(Size size) {
        List<Clothes> results = new ArrayList<>();
        for (Clothes item : inventory) {
            if (item.getSize() == size) {
                results.add(item);
            }
        }
        return results;
    }

    // Метод пошуку 3: За ціною (без Stream API)
    private static List<Clothes> searchByMaxPrice(double maxPrice) {
        List<Clothes> results = new ArrayList<>();
        for (Clothes item : inventory) {
            if (item.getPrice() <= maxPrice) {
                results.add(item);
            }
        }
        return results;
    }

    // Метод для виведення результатів пошуку
    private static void printSearchResults(List<Clothes> results) {
        if (results.isEmpty()) {
            System.out.println("\nНа жаль, за вашим критерієм нічого не знайдено.");
        } else {
            System.out.println("\n--- РЕЗУЛЬТАТИ ПОШУКУ (" + results.size() + " шт.) ---");
            for (Clothes item : results) {
                System.out.println(item.toString());
            }
        }
    }

    // --- ЛОГІКА СТВОРЕННЯ (З минулої лаби) ---

    private static void creationMenu() {
        System.out.println("\n--- ОБЕРІТЬ ТИП ОБ'ЄКТА ---");
        System.out.println("1. Звичайний Одяг (Clothes)");
        System.out.println("2. Штани (Pants)");
        System.out.println("3. Джинси (Jeans)");
        System.out.println("4. Сорочка (Shirts)");
        System.out.println("5. Офіційна сорочка (DressShirts)");
        System.out.println("0. Повернутися назад");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Бренд: "); String brand = scanner.nextLine();
            System.out.print("Розмір (S, M, L...): "); Size size = Size.fromString(scanner.nextLine());
            System.out.print("Ціна: "); double price = Double.parseDouble(scanner.nextLine());

            switch (typeChoice) {
                case "1" -> {
                    System.out.print("Тип: "); String type = scanner.nextLine();
                    inventory.add(new Clothes(type, brand, size, price));
                }
                case "2" -> {
                    System.out.print("Довжина (см): "); int len = Integer.parseInt(scanner.nextLine());
                    inventory.add(new Pants(brand, size, price, len));
                }
                case "3" -> {
                    System.out.print("Довжина (см): "); int len = Integer.parseInt(scanner.nextLine());
                    System.out.print("Тип прання: "); String wash = scanner.nextLine();
                    inventory.add(new Jeans(brand, size, price, len, wash));
                }
                case "4" -> {
                    System.out.print("Тип рукава: "); String sleeve = scanner.nextLine();
                    inventory.add(new Shirts(brand, size, price, sleeve));
                }
                case "5" -> {
                    System.out.print("Тип рукава: "); String sleeve = scanner.nextLine();
                    System.out.print("Розмір коміра: "); double collar = Double.parseDouble(scanner.nextLine());
                    inventory.add(new DressShirts(brand, size, price, sleeve, collar));
                }
                default -> System.out.println("Невірний тип.");
            }
            System.out.println("Об'єкт додано!");
        } catch (Exception e) {
            System.out.println("Помилка при створенні: " + e.getMessage());
        }
    }

    private static void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Колекція порожня.");
        } else {
            System.out.println("\n--- ВМІСТ КОЛЕКЦІЇ ---");
            for (Clothes item : inventory) {
                System.out.println(item.toString());
            }
        }
    }
}
