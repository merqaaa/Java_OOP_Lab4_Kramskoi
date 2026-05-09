package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private String storeName;
    private List<Clothes> inventory;

    public Store(String storeName) {
        if (storeName == null || storeName.trim().isEmpty()) throw new IllegalArgumentException("Назва порожня.");
        this.storeName = storeName;
        this.inventory = new ArrayList<>();
    }

    public void addClothes(Clothes item) {
        if (item == null) throw new IllegalArgumentException("Об'єкт null.");
        inventory.add(item);
    }

    public List<Clothes> getInventory() { return inventory; }
    public String getStoreName() { return storeName; }
    public int getInventoryCount() { return inventory.size(); }
}
