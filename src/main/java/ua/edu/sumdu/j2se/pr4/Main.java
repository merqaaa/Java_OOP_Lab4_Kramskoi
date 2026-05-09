package ua.edu.sumdu.j2se.pr4;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Clothes> inventory;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №9. Студент: Крамськой Іван | Варіант: 5");
        
        // 1. Завантажуємо дані з файлу при старті
        inventory = FileManager.loadFromFile();
        
        boolean running = true;

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ (Робота з файлами JSON) ---");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Завершити роботу та ЗБЕРЕГТИ дані у файл");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> creationMenu();
                case "2" -> showInventory();
                case "3" -> {
                    // 2. Зберігаємо дані перед виходом
                    FileManager.saveToFile(inventory);
                    running = false;
                }
                default -> System.out.println("Помилка вибору.");
            }
        }
    }

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
            System.out.println("Об'єкт додано до оперативної пам'яті (щоб зберегти у файл, оберіть вихід)!");
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
