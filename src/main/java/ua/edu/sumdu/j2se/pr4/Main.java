package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Драйвер-клас для керування магазином.
 * Лабораторна №15: Lambda expressions (Заміна анонімних класів).
 */
public class Main {
    private static final Store store = new Store();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №15. Студент: Крамськой Іван | Варіант: 5");
        
        // Завантаження даних із JSON при старті
        store.setInventory(FileManager.loadFromFile());
        
        boolean running = true;

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ МАГАЗИНУ ---");
            System.out.println("1. Пошук об'єкта у колекції");
            System.out.println("2. Додати новий товар");
            System.out.println("3. Вивести весь список (як у файлі)");
            System.out.println("4. Вивести ВІДСОРТОВАНИЙ список (Лямбда-вирази)");
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
     * Метод для сортування колекції з використанням ЛЯМБДА-ВИРАЗІВ (ЛР №15)
     */
    private static void showSortedInventory() {
        if (store.getInventory().isEmpty()) {
            System.out.println("Магазин порожній. Нічого сортувати.");
            return;
        }

        System.out.println("\n--- ОБЕРІТЬ КРИТЕРІЙ СОРТУВАННЯ ---");
        System.out.println("1. Сортувати за ціною (від дешевих до дорогих)");
        System.out.println("2. Сортувати за брендом (в алфавітному порядку)");
        System.out.println("3. Сортувати за кількістю на складі (від найбільшої)");
        System.out.println("0. Повернутися в головне меню");
        System.out.print("Ваш вибір: ");

        String sortChoice = scanner.nextLine();
        if (sortChoice.equals("0")) return;

        // Створюємо копію списку
        List<StoreItem> itemsToSort = new ArrayList<>(store.getInventory());
        Comparator<StoreItem> comparator = null;

        switch (sortChoice) {
            case "1" -> {
                // Лямбда-вираз для сортування за ціною
                comparator = (o1, o2) -> Double.compare(o1.getItem().getPrice(), o2.getItem().getPrice());
            }
            case "2" -> {
                // Лямбда-вираз для сортування за брендом
                comparator = (o1, o2) -> o1.getItem().getBrand().compareToIgnoreCase(o2.getItem().getBrand());
            }
            case "3" -> {
                // Лямбда-вираз для сортування за кількістю (зворотній порядок)
                comparator = (o1, o2) -> Integer.compare(o2.getQuantity(), o1.getQuantity());
            }
            default -> {
                System.out.println("Невірний критерій сортування.");
                return;
            }
        }

        // Виконання сортування (БЕЗ Stream API)
        Collections.sort(itemsToSort, comparator);

        System.out.println("\n--- ВІДСОРТОВАНИЙ СПИСОК ---");
        for (StoreItem item : itemsToSort) {
            System.out.println(item.toString());
        }
    }

    // --- УСІ ІНШІ МЕТОДИ ЗАЛИШАЮТЬСЯ БЕЗ ЗМІН ---

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
