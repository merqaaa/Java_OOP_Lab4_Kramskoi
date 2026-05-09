package ua.edu.sumdu.j2se.pr4;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Store store = new Store();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №11. Студент: Крамськой Іван | Варіант: 5");
        
        store.setInventory(FileManager.loadFromFile());
        boolean running = true;

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ МАГАЗИНУ ---");
            System.out.println("1. Пошук об'єкта");
            System.out.println("2. Створити та додати товар (з кількістю)");
            System.out.println("3. Вивести інформацію про всі об'єкти");
            System.out.println("4. Завершити роботу та ЗБЕРЕГТИ дані");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> searchMenu();
                case "2" -> creationMenu();
                case "3" -> showInventory();
                case "4" -> {
                    FileManager.saveToFile(store.getInventory());
                    running = false;
                }
                default -> System.out.println("Помилка вибору.");
            }
        }
    }

    private static void searchMenu() {
        if (store.getInventory().isEmpty()) {
            System.out.println("Магазин порожній. Пошук неможливий.");
            return;
        }

        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. За брендом");
        System.out.println("2. За розміром");
        System.out.println("3. За максимальною ціною");
        System.out.println("0. Назад");
        System.out.print("Оберіть критерій: ");

        String choice = scanner.nextLine();
        List<StoreItem> searchResults;

        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("Введіть бренд: ");
                    searchResults = store.searchByBrand(scanner.nextLine());
                }
                case "2" -> {
                    System.out.print("Введіть розмір: ");
                    searchResults = store.searchBySize(Size.fromString(scanner.nextLine()));
                }
                case "3" -> {
                    System.out.print("Введіть макс. ціну: ");
                    searchResults = store.searchByMaxPrice(Double.parseDouble(scanner.nextLine()));
                }
                case "0" -> { return; }
                default -> { System.out.println("Невірний критерій."); return; }
            }
            printSearchResults(searchResults);
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    private static void printSearchResults(List<StoreItem> results) {
        if (results.isEmpty()) System.out.println("Нічого не знайдено.");
        else {
            System.out.println("\n--- ЗНАЙДЕНО (" + results.size() + " позицій) ---");
            results.forEach(System.out::println);
        }
    }

    private static void creationMenu() {
        System.out.println("\n--- ОБЕРІТЬ ТИП ОБ'ЄКТА ---");
        System.out.println("1. Звичайний Одяг | 2. Штани | 3. Джинси | 4. Сорочка | 5. Офіційна сорочка | 0. Назад");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Кількість одиниць товару: "); int qty = Integer.parseInt(scanner.nextLine());
            System.out.print("Бренд: "); String brand = scanner.nextLine();
            System.out.print("Розмір (S, M, L...): "); Size size = Size.fromString(scanner.nextLine());
            System.out.print("Ціна: "); double price = Double.parseDouble(scanner.nextLine());

            Clothes newItem = null;

            switch (typeChoice) {
                case "1" -> {
                    System.out.print("Тип: "); String type = scanner.nextLine();
                    newItem = new Clothes(type, brand, size, price);
                }
                case "2" -> {
                    System.out.print("Довжина (см): "); int len = Integer.parseInt(scanner.nextLine());
                    newItem = new Pants(brand, size, price, len);
                }
                case "3" -> {
                    System.out.print("Довжина (см): "); int len = Integer.parseInt(scanner.nextLine());
                    System.out.print("Тип прання: "); String wash = scanner.nextLine();
                    newItem = new Jeans(brand, size, price, len, wash);
                }
                case "4" -> {
                    System.out.print("Тип рукава: "); String sleeve = scanner.nextLine();
                    newItem = new Shirts(brand, size, price, sleeve);
                }
                case "5" -> {
                    System.out.print("Тип рукава: "); String sleeve = scanner.nextLine();
                    System.out.print("Комір: "); double collar = Double.parseDouble(scanner.nextLine());
                    newItem = new DressShirts(brand, size, price, sleeve, collar);
                }
                default -> System.out.println("Невірний тип.");
            }
            if (newItem != null) store.addNewClothes(newItem, qty);
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    private static void showInventory() {
        if (store.getInventory().isEmpty()) System.out.println("Магазин порожній.");
        else {
            System.out.println("\n--- ВМІСТ МАГАЗИНУ ---");
            store.getInventory().forEach(System.out::println);
        }
    }
}
