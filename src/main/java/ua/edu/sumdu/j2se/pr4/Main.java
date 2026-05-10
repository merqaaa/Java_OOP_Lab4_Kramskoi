package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Драйвер-клас для керування магазином.
 * Лабораторна №13: Абстрактні класи та сортування через Comparable.
 */
public class Main {
    private static final Store store = new Store();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №13. Студент: Крамськой Іван | Варіант: 5");
        
        // Завантаження даних із JSON при старті
        store.setInventory(FileManager.loadFromFile());
        
        boolean running = true;

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ МАГАЗИНУ ---");
            System.out.println("1. Пошук об'єкта у колекції");
            System.out.println("2. Додати новий товар");
            System.out.println("3. Вивести весь список (як у файлі)");
            System.out.println("4. Вивести ВІДСОРТОВАНИЙ список (за ціною)");
            System.out.println("5. Завершити роботу та зберегти дані");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> searchMenu();
                case "2" -> creationMenu();
                case "3" -> showInventory();
                case "4" -> showSortedInventory();
                case "5" -> {
                    FileManager.saveToFile(store.getInventory());
                    running = false;
                    System.out.println("Дані збережено. Програму завершено.");
                }
                default -> System.out.println("Помилка: Оберіть пункт від 1 до 5.");
            }
        }
    }

    /**
     * Метод для виведення відсортованого списку (ЛР №13).
     */
    private static void showSortedInventory() {
        List<StoreItem> inventory = store.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Магазин порожній. Нічого сортувати.");
            return;
        }

        // Створюємо тимчасовий список саме об'єктів Clothes для сортування
        List<Clothes> itemsToSort = new ArrayList<>();
        for (StoreItem si : inventory) {
            itemsToSort.add(si.getItem());
        }

        // Сортуємо список. Java автоматично використає метод compareTo, 
        // який ми реалізували в класі Clothes.
        Collections.sort(itemsToSort);

        System.out.println("\n--- ВІДСОРТОВАНИЙ СПИСОК ТОВАРІВ (від дешевих до дорогих) ---");
        for (Clothes c : itemsToSort) {
            System.out.println(c);
        }
    }

    private static void creationMenu() {
        System.out.println("\n--- ОБЕРІТЬ ТИП ТОВАРУ ---");
        System.out.println("1. Штани (Pants) | 2. Джинси (Jeans) | 3. Сорочка (Shirts) | 4. Офіційна сорочка | 0. Назад");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Кількість одиниць: "); int qty = Integer.parseInt(scanner.nextLine());
            System.out.print("Бренд: "); String brand = scanner.nextLine();
            System.out.print("Розмір (XS, S, M, L, XL...): "); Size size = Size.fromString(scanner.nextLine());
            System.out.print("Ціна: "); double price = Double.parseDouble(scanner.nextLine());

            Clothes newItem = null;
            switch (typeChoice) {
                case "1" -> {
                    System.out.print("Довжина (см): "); int len = Integer.parseInt(scanner.nextLine());
                    newItem = new Pants(brand, size, price, len);
                }
                case "2" -> {
                    System.out.print("Довжина (см): "); int len = Integer.parseInt(scanner.nextLine());
                    System.out.print("Тип прання: "); String wash = scanner.nextLine();
                    newItem = new Jeans(brand, size, price, len, wash);
                }
                case "3" -> {
                    System.out.print("Тип рукава: "); String sleeve = scanner.nextLine();
                    newItem = new Shirts(brand, size, price, sleeve);
                }
                case "4" -> {
                    System.out.print("Тип рукава: "); String sleeve = scanner.nextLine();
                    System.out.print("Розмір коміра: "); double collar = Double.parseDouble(scanner.nextLine());
                    newItem = new DressShirts(brand, size, price, sleeve, collar);
                }
                default -> System.out.println("Невірний тип.");
            }

            if (newItem != null) {
                store.addNewClothes(newItem, qty);
                System.out.println("Товар успішно додано!");
            }
        } catch (Exception e) {
            System.out.println("Помилка при створенні: " + e.getMessage());
        }
    }

    private static void searchMenu() {
        if (store.getInventory().isEmpty()) {
            System.out.println("Магазин порожній.");
            return;
        }
        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. За брендом | 2. За розміром | 3. За макс. ціною | 0. Назад");
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
                case "0" -> { return; }
            }
            if (results.isEmpty()) System.out.println("Нічого не знайдено.");
            else results.forEach(System.out::println);
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    private static void showInventory() {
        if (store.getInventory().isEmpty()) System.out.println("Магазин порожній.");
        else {
            System.out.println("\n--- ВМІСТ МАГАЗИНУ (БЕЗ СОРТУВАННЯ) ---");
            store.getInventory().forEach(System.out::println);
        }
    }
}
