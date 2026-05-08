package ua.edu.sumdu.j2se.pr4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Практична робота №4. Студент: Крамськой Іван | Варіант: 5");
        
        System.out.print("Введіть кількість елементів для масиву одягу: ");
        int count = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера

        Clothes[] clothesArray = new Clothes[count];

        for (int i = 0; i < count; i++) {
            System.out.println("\nВведення даних для товару №" + (i + 1) + ":");
            System.out.print("Тип: "); String type = scanner.nextLine();
            System.out.print("Бренд: "); String brand = scanner.nextLine();
            System.out.print("Розмір: "); String size = scanner.nextLine();
            System.out.print("Ціна: "); double price = scanner.nextDouble();
            scanner.nextLine(); // Очищення буфера
            clothesArray[i] = new Clothes(type, brand, size, price);
        }

        System.out.println("\n--- Список введеного одягу ---");
        for (Clothes item : clothesArray) {
            System.out.println(item);
        }
        scanner.close();
    }
}
