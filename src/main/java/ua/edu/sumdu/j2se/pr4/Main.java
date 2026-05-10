package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Store store = new Store();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №17. Студент: Крамськой Іван | Варіант: 5");
        store.setInventory(FileManager.loadFromFile());
        
        boolean running = true;
        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ МАГАЗИНУ ---");
            System.out.println("1. Пошук об'єкта у колекції");
            System.out.println("2. Додати новий товар");
            System.out.println("3. Модифікувати товар (Update)");
            System.out.println("4. Видалити товар (Delete)");
            System.out.println("5. Вивести весь список");
            System.out.println("6. Вивести відсортований список");
            System.out.println("0. Завершити роботу та зберегти дані");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> searchMenu();
                case "2" -> creationMenu();
                case "3" -> updateItemMenu();
                case "4" -> deleteItemMenu();
                case "5" -> showInventory();
                case "6" -> showSortedInventory();
                case "0" -> {
                    FileManager.saveToFile(store.getInventory());
                    running = false;
                    System.out.println("Дані збережено. Програму завершено.");
                }
                default -> System.out.println("Помилка: Оберіть існуючий пункт меню.");
            }
        }
    }

    // --- ЛР 17: МОДИФІКАЦІЯ (UPDATE) ---
    private static void updateItemMenu() {
        StoreItem selected = selectItemFromList("ОБЕРІТЬ ТОВАР ДЛЯ МОДИФІКАЦІЇ");
        if (selected == null) return;

        System.out.println("\nВи обрали: " + selected.getItem().getType() + " " + selected.getItem().getBrand());
        System.out.println("Що бажаєте змінити?");
        System.out.println("1. Ціну");
        System.out.println("2. Кількість на складі");
        System.out.print("Вибір: ");
        String attrChoice = scanner.nextLine();

        try {
            // Створюємо копії об'єктів для безпечного оновлення (щоб передати newObject)
            Clothes newClothes = selected.getItem(); 
            int newQuantity = selected.getQuantity();

            if (attrChoice.equals("1")) {
                System.out.print("Введіть нову ціну: ");
                double newPrice = Double.parseDouble(scanner.nextLine());
                // Створюємо копію через конструктор (в Java зазвичай роблять так для незмінності)
                // Але оскільки в нас є сетер, ми можемо змінити ціну напряму, проте для демонстрації 
                // update(old, new) створимо повністю нову обгортку StoreItem.
                newClothes.setPrice(newPrice);
            } else if (attrChoice.equals("2")) {
                System.out.print("Введіть нову кількість: ");
                newQuantity = Integer.parseInt(scanner.nextLine());
            } else {
                System.out.println("Операцію скасовано.");
                return;
            }

            StoreItem newItem = new StoreItem(newClothes, newQuantity);
            
            // Виклик методу агрегатора
            boolean success = store.update(selected, newItem);
            
            if (success) System.out.println("[СИСТЕМА] Товар успішно модифіковано!");
            else System.out.println("[СИСТЕМА] Помилка: Товар не знайдено в колекції.");

        } catch (Exception e) {
            System.out.println("[ПОМИЛКА] Некоректне введення: " + e.getMessage());
        }
    }

    // --- ЛР 17: ВИДАЛЕННЯ (DELETE) ---
    private static void deleteItemMenu() {
        StoreItem selected = selectItemFromList("ОБЕРІТЬ ТОВАР ДЛЯ ВИДАЛЕННЯ");
        if (selected == null) return;

        System.out.println("\nВи збираєтесь видалити: " + selected.toString());
        System.out.print("Ви впевнені? (Y/N): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            // Виклик методу агрегатора
            boolean success = store.delete(selected);
            if (success) System.out.println("[СИСТЕМА] Товар успішно видалено!");
            else System.out.println("[СИСТЕМА] Помилка: Товар не знайдено.");
        } else {
            System.out.println("Видалення скасовано.");
        }
    }

    // Допоміжний метод для вибору товару зі списку
    private static StoreItem selectItemFromList(String title) {
        List<StoreItem> items = store.getInventory();
        if (items.isEmpty()) {
            System.out.println("Магазин порожній. Операція неможлива.");
            return null;
        }

        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + items.get(i).getItem().getType() + " " + items.get(i).getItem().getBrand());
        }
        System.out.println("[0] Скасувати");
        System.out.print("Введіть номер: ");

        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index == 0) return null;
            if (index > 0 && index <= items.size()) {
                return items.get(index - 1);
            } else {
                System.out.println("Невірний номер.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Помилка: введіть число.");
        }
        return null;
    }

    // --- МЕТОДИ З МИНУЛИХ ЛАБ ---

    private static void creationMenu() {
        System.out.println("\n--- ОБЕРІТЬ ТИП ТОВАРУ ---");
        System.out.println("1. Штани | 2. Джинси | 3. Сорочка | 4. Офіційна сорочка | 0. Назад");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Кількість одиниць: "); int qty = Integer.parseInt(scanner.nextLine());
            System.out.print("Бренд: "); String brand = scanner.nextLine();
            System.out.print("Розмір (XS, S, M, L...): "); Size size = Size.fromString(scanner.nextLine());
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
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    private static void searchMenu() {
        if (store.getInventory().isEmpty()) {
            System.out.println("Магазин порожній.");
            return;
        }
        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. За брендом | 2. За розміром | 3. За ціною | 4. За UUID | 0. Назад");
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
                    System.out.print("Введіть макс. ціну: ");
                    results = store.searchByMaxPrice(Double.parseDouble(scanner.nextLine()));
                }
                case "4" -> {
                    System.out.print("Введіть UUID: ");
                    StoreItem found = store.searchByUuid(scanner.nextLine());
                    if (found != null) results.add(found);
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
            System.out.println("\n--- ВМІСТ МАГАЗИНУ ---");
            store.getInventory().forEach(System.out::println);
        }
    }

    private static void showSortedInventory() {
        if (store.getInventory().isEmpty()) {
            System.out.println("Магазин порожній."); return;
        }
        System.out.println("1. За ціною | 2. За брендом | 3. За кількістю | 0. Назад");
        System.out.print("Вибір: ");
        String sortChoice = scanner.nextLine();
        if (sortChoice.equals("0")) return;

        List<StoreItem> itemsToSort = new ArrayList<>(store.getInventory());
        Comparator<StoreItem> comparator = null;

        switch (sortChoice) {
            case "1" -> comparator = (o1, o2) -> Double.compare(o1.getItem().getPrice(), o2.getItem().getPrice());
            case "2" -> comparator = (o1, o2) -> o1.getItem().getBrand().compareToIgnoreCase(o2.getItem().getBrand());
            case "3" -> comparator = (o1, o2) -> Integer.compare(o2.getQuantity(), o1.getQuantity());
            default -> { System.out.println("Невірний критерій."); return; }
        }
        Collections.sort(itemsToSort, comparator);
        System.out.println("\n--- ВІДСОРТОВАНИЙ СПИСОК ---");
        itemsToSort.forEach(System.out::println);
    }
}
