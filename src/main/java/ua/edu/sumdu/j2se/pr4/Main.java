package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Store store = new Store();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №18. Студент: Крамськой Іван | Варіант: 5");
        store.setInventory(FileManager.loadFromFile());
        
        boolean running = true;
        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ МАГАЗИНУ ---");
            System.out.println("1. Пошук об'єкта");
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

    private static void updateItemMenu() {
        StoreItem selected = selectItemFromList("ОБЕРІТЬ ТОВАР ДЛЯ МОДИФІКАЦІЇ");
        if (selected == null) return;

        System.out.println("\nВи обрали: " + selected.getItem().getType() + " " + selected.getItem().getBrand());
        System.out.println("Що змінити? 1. Ціну | 2. Кількість");
        System.out.print("Вибір: ");
        String attrChoice = scanner.nextLine();

        try {
            Clothes newClothes = selected.getItem(); 
            int newQuantity = selected.getQuantity();

            if (attrChoice.equals("1")) {
                System.out.print("Введіть нову ціну: ");
                double newPrice = Double.parseDouble(scanner.nextLine());
                newClothes.setPrice(newPrice); // Може кинути InvalidFieldValueException
            } else if (attrChoice.equals("2")) {
                System.out.print("Введіть нову кількість: ");
                newQuantity = Integer.parseInt(scanner.nextLine());
            }

            StoreItem newItem = new StoreItem(newClothes, newQuantity);
            
            // Виклик методу агрегатора (Може кинути ObjectNotFoundException)
            store.update(selected, newItem);
            System.out.println("[СИСТЕМА] Товар успішно модифіковано!");

        // --- ЛР 18: Обробка власних винятків ---
        } catch (InvalidFieldValueException | ObjectNotFoundException e) {
            System.out.println("[ПОМИЛКА БІЗНЕС-ЛОГІКИ] " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("[ПОМИЛКА ВВОДУ] Будь ласка, вводьте лише числа.");
        }
    }

    private static void deleteItemMenu() {
        StoreItem selected = selectItemFromList("ОБЕРІТЬ ТОВАР ДЛЯ ВИДАЛЕННЯ");
        if (selected == null) return;

        System.out.println("\nВи збираєтесь видалити: " + selected.getItem().getBrand());
        System.out.print("Ви впевнені? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            try {
                store.delete(selected); // Може кинути ObjectNotFoundException
                System.out.println("[СИСТЕМА] Товар успішно видалено!");
            } catch (ObjectNotFoundException e) {
                System.out.println("[ПОМИЛКА БІЗНЕС-ЛОГІКИ] " + e.getMessage());
            }
        } else {
            System.out.println("Видалення скасовано.");
        }
    }

    private static void creationMenu() {
        System.out.println("\n--- ОБЕРІТЬ ТИП ТОВАРУ ---");
        System.out.println("1. Штани | 2. Джинси | 3. Сорочка | 4. Офіційна сорочка | 0. Назад");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Кількість одиниць: "); int qty = Integer.parseInt(scanner.nextLine());
            System.out.print("Бренд: "); String brand = scanner.nextLine();
            System.out.print("Розмір (S, M, L...): "); Size size = Size.fromString(scanner.nextLine());
            System.out.print("Ціна: "); double price = Double.parseDouble(scanner.nextLine());

            Clothes newItem = null;
            switch (typeChoice) {
                case "1" -> newItem = new Pants(brand, size, price, 100);
                case "2" -> newItem = new Jeans(brand, size, price, 105, "Classic");
                case "3" -> newItem = new Shirts(brand, size, price, "Long");
                case "4" -> newItem = new DressShirts(brand, size, price, "Long", 40);
            }

            if (newItem != null) {
                store.addNewClothes(newItem, qty);
                System.out.println("Товар успішно додано!");
            }
        } catch (InvalidFieldValueException e) {
            System.out.println("[ПОМИЛКА ВАЛІДАЦІЇ] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Помилка: Некоректний формат даних.");
        }
    }

    // Інші методи (selectItemFromList, searchMenu, showInventory, showSortedInventory) залишаються без змін
    private static StoreItem selectItemFromList(String title) {
        List<StoreItem> items = store.getInventory();
        if (items.isEmpty()) { System.out.println("Магазин порожній."); return null; }
        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < items.size(); i++) System.out.println("[" + (i + 1) + "] " + items.get(i).getItem().getType() + " " + items.get(i).getItem().getBrand());
        System.out.print("Введіть номер (0 для відміни): ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index == 0) return null;
            if (index > 0 && index <= items.size()) return items.get(index - 1);
            else System.out.println("Невірний номер.");
        } catch (NumberFormatException e) { System.out.println("Помилка: введіть число."); }
        return null;
    }
    private static void searchMenu() { /* Код пошуку з попередніх лаб */ }
    private static void showInventory() { store.getInventory().forEach(System.out::println); }
    private static void showSortedInventory() { 
        List<StoreItem> list = new ArrayList<>(store.getInventory());
        list.sort((a, b) -> a.getItem().compareTo(b.getItem()));
        list.forEach(System.out::println);
    }
}
