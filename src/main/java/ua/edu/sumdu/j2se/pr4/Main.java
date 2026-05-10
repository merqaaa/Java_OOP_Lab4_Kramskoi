package ua.edu.sumdu.j2se.pr4;

import java.util.List;
import java.util.Scanner;

/**
 * Драйвер-клас для керування магазином одягу.
 * Реалізовано інтеграцію з БД через JDBC та роботу з JSON.
 */
public class Main {
    private static final Store store = new Store();
    private static final Scanner scanner = new Scanner(System.in);
    private static DatabaseManager dbManager; // Об'єкт для роботи з БД

    public static void main(String[] args) {
        System.out.println("Практична робота №12. Студент: Крамськой Іван | Варіант: 5");

        // 1. Перевірка аргументів: шлях до db.properties має бути в args[0]
        if (args.length == 0) {
            System.out.println("[УВАГА] Шлях до файлу конфігурації БД не вказано.");
            System.out.println("Використовуйте: mvn exec:java -Dexec.args=\"db.properties\"");
        } else {
            // Ініціалізація менеджера бази даних
            dbManager = new DatabaseManager(args[0]);
        }

        // Завантаження початкових даних із JSON (з ЛР №9-11)
        store.setInventory(FileManager.loadFromFile());
        
        boolean running = true;

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ МАГАЗИНУ (JDBC + JSON) ---");
            System.out.println("1. Пошук об'єкта у колекції");
            System.out.println("2. Створити та додати товар (Колекція + База Даних)");
            System.out.println("3. Вивести інформацію про всі об'єкти");
            System.out.println("4. Завершити роботу та ЗБЕРЕГТИ дані у JSON");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> searchMenu();
                case "2" -> creationMenu();
                case "3" -> showInventory();
                case "4" -> {
                    FileManager.saveToFile(store.getInventory());
                    running = false;
                    System.out.println("Програму завершено.");
                }
                default -> System.out.println("Помилка: Оберіть пункт від 1 до 4.");
            }
        }
    }

    /**
     * Меню створення нових об'єктів ієрархії.
     */
    private static void creationMenu() {
        System.out.println("\n--- ОБЕРІТЬ ТИП ОБ'ЄКТА ДЛЯ СТВОРЕННЯ ---");
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
            System.out.print("Кількість одиниць товару: "); 
            int qty = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Бренд: "); 
            String brand = scanner.nextLine();
            
            System.out.print("Розмір (XS, S, M, L, XL, XXL, UNIVERSAL): "); 
            Size size = Size.fromString(scanner.nextLine());
            
            System.out.print("Ціна: "); 
            double price = Double.parseDouble(scanner.nextLine());

            Clothes newItem = null;

            switch (typeChoice) {
                case "1" -> {
                    System.out.print("Уточніть тип (напр. Куртка): "); 
                    String type = scanner.nextLine();
                    newItem = new Clothes(type, brand, size, price);
                }
                case "2" -> {
                    System.out.print("Довжина штанини (см): "); 
                    int len = Integer.parseInt(scanner.nextLine());
                    newItem = new Pants(brand, size, price, len);
                }
                case "3" -> {
                    System.out.print("Довжина штанини (см): "); 
                    int len = Integer.parseInt(scanner.nextLine());
                    System.out.print("Тип прання (напр. Stone Wash): "); 
                    String wash = scanner.nextLine();
                    newItem = new Jeans(brand, size, price, len, wash);
                }
                case "4" -> {
                    System.out.print("Тип рукава (напр. Короткий): "); 
                    String sleeve = scanner.nextLine();
                    newItem = new Shirts(brand, size, price, sleeve);
                }
                case "5" -> {
                    System.out.print("Тип рукава (напр. Довгий): "); 
                    String sleeve = scanner.nextLine();
                    System.out.print("Розмір коміра: "); 
                    double collar = Double.parseDouble(scanner.nextLine());
                    newItem = new DressShirts(brand, size, price, sleeve, collar);
                }
                default -> System.out.println("Невірний тип об'єкта.");
            }

            if (newItem != null) {
                // Додаємо в локальну колекцію (ArrayList)
                store.addNewClothes(newItem, qty);
                
                // Додаємо в базу даних через JDBC (якщо менеджер ініціалізовано)
                if (dbManager != null) {
                    dbManager.insertClothes(newItem);
                }
            }
        } catch (Exception e) {
            System.out.println("[ПОМИЛКА] Некоректні дані: " + e.getMessage());
        }
    }

    /**
     * Меню пошуку об'єктів (Логіка з ЛР №10-11).
     */
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
            
            if (searchResults.isEmpty()) {
                System.out.println("За вашим запитом нічого не знайдено.");
            } else {
                System.out.println("\n--- РЕЗУЛЬТАТИ ПОШУКУ ---");
                for (StoreItem si : searchResults) {
                    System.out.println(si);
                }
            }
        } catch (Exception e) {
            System.out.println("Помилка під час пошуку: " + e.getMessage());
        }
    }

    /**
     * Вивід усіх товарів магазину.
     */
    private static void showInventory() {
        List<StoreItem> items = store.getInventory();
        if (items.isEmpty()) {
            System.out.println("Магазин порожній.");
        } else {
            System.out.println("\n--- АКТУАЛЬНИЙ ВМІСТ МАГАЗИНУ ---");
            for (StoreItem si : items) {
                System.out.println(si);
            }
        }
    }
}
