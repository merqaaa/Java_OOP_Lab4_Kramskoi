package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<Clothes> inventory = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Практична робота №7. Студент: Крамськой Іван | Варіант: 5");
        boolean running = true;

        while (running) {
            System.out.println("\n--- МЕНЮ (Успадкування та Поліморфізм) ---");
            System.out.println("1. Додати базовий Одяг");
            System.out.println("2. Додати Штани (Pants)");
            System.out.println("3. Додати Сорочку (Shirts)");
            System.out.println("4. Вивести весь список (Поліморфізм)");
            System.out.println("5. Вихід");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> addGeneral();
                case "2" -> addPants();
                case "3" -> addShirts();
                case "4" -> showAll();
                case "5" -> running = false;
                default -> System.out.println("Помилка вибору.");
            }
        }
    }

    private static void addGeneral() {
        try {
            System.out.print("Тип: "); String t = scanner.nextLine();
            System.out.print("Бренд: "); String b = scanner.nextLine();
            System.out.print("Розмір: "); Size s = Size.fromString(scanner.nextLine());
            System.out.print("Ціна: "); double p = Double.parseDouble(scanner.nextLine());
            inventory.add(new Clothes(t, b, s, p));
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    private static void addPants() {
        try {
            System.out.print("Бренд: "); String b = scanner.nextLine();
            System.out.print("Розмір: "); Size s = Size.fromString(scanner.nextLine());
            System.out.print("Ціна: "); double p = Double.parseDouble(scanner.nextLine());
            System.out.print("Довжина (см): "); int l = Integer.parseInt(scanner.nextLine());
            inventory.add(new Pants(b, s, p, l));
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    private static void addShirts() {
        try {
            System.out.print("Бренд: "); String b = scanner.nextLine();
            System.out.print("Розмір: "); Size s = Size.fromString(scanner.nextLine());
            System.out.print("Ціна: "); double p = Double.parseDouble(scanner.nextLine());
            System.out.print("Рукав: "); String sl = scanner.nextLine();
            inventory.add(new Shirts(b, s, p, sl));
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    private static void showAll() {
        System.out.println("\n--- ВМІСТ КОЛЕКЦІЇ (Поліморфний вивід) ---");
        for (Clothes item : inventory) {
            System.out.println(item.toString());
        }
    }
}
