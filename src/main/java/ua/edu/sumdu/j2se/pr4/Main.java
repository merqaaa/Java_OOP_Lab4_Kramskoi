package ua.edu.sumdu.j2se.pr4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Lab 4. Student: Kramskoi Ivan | Variant: 5");
        
        System.out.print("Enter number of clothes items: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        Clothes[] clothesArray = new Clothes[count];

        for (int i = 0; i < count; i++) {
            System.out.println("Enter details for item " + (i + 1) + ":");
            System.out.print("Type: "); String type = scanner.nextLine();
            System.out.print("Brand: "); String brand = scanner.nextLine();
            System.out.print("Size: "); String size = scanner.nextLine();
            System.out.print("Price: "); double price = scanner.nextDouble();
            scanner.nextLine();
            clothesArray[i] = new Clothes(type, brand, size, price);
        }

        System.out.println("\n--- Inventory List ---");
        for (Clothes item : clothesArray) {
            System.out.println(item);
        }
        scanner.close();
    }
}
