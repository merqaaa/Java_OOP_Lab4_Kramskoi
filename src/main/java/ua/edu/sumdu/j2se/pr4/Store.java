package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас Магазин, що демонструє агрегацію (зберігає список Clothes).
 */
public class Store {
    private String storeName;
    private List<Clothes> inventory;

    public Store(String storeName) {
        if (storeName == null || storeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва магазину не може бути порожньою.");
        }
        this.storeName = storeName;
        this.inventory = new ArrayList<>();
    }

    public void addClothes(Clothes item) {
        if (item == null) {
            throw new IllegalArgumentException("Неможливо додати null об'єкт у магазин.");
        }
        inventory.add(item);
    }

    public List<Clothes> getInventory() {
        return inventory;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getInventoryCount() {
        return inventory.size();
    }
}
