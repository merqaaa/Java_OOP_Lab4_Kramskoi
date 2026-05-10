package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Клас-агрегатор для керування колекцією товарів магазину.
 * ЛР №17: Додано методи update та delete.
 */
public class Store {
    private List<StoreItem> inventory;

    public Store() {
        this.inventory = new ArrayList<>();
    }

    public List<StoreItem> getInventory() {
        return inventory;
    }

    public void setInventory(List<StoreItem> inventory) {
        this.inventory = inventory;
    }

    public void addNewClothes(Clothes cl, int quantity) {
        for (StoreItem storeItem : inventory) {
            if (storeItem.getItem().equals(cl)) {
                storeItem.addQuantity(quantity);
                System.out.println("Товар вже існує. Кількість оновлена.");
                return;
            }
        }
        inventory.add(new StoreItem(cl, quantity));
        System.out.println("Новий тип товару додано до магазину.");
    }

    // --- ЛАБОРАТОРНА РОБОТА №17: ВИДАЛЕННЯ (DELETE) ---
    public boolean delete(StoreItem existingObject) {
        if (existingObject == null) return false;
        
        for (int i = 0; i < inventory.size(); i++) {
            // Використовуємо UUID для точного знаходження об'єкта
            if (inventory.get(i).getItem().getUuid().equals(existingObject.getItem().getUuid())) {
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }

    // --- ЛАБОРАТОРНА РОБОТА №17: МОДИФІКАЦІЯ (UPDATE) ---
    public boolean update(StoreItem existingObject, StoreItem newObject) {
        if (existingObject == null || newObject == null) return false;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getItem().getUuid().equals(existingObject.getItem().getUuid())) {
                inventory.set(i, newObject); // Замінюємо старий об'єкт на новий
                return true;
            }
        }
        return false;
    }

    // --- МЕТОДИ ПОШУКУ (з попередніх ЛР) ---
    public List<StoreItem> searchByBrand(String brand) {
        List<StoreItem> results = new ArrayList<>();
        for (StoreItem si : inventory) {
            if (si.getItem().getBrand().equalsIgnoreCase(brand.trim())) {
                results.add(si);
            }
        }
        return results;
    }

    public List<StoreItem> searchBySize(Size size) {
        List<StoreItem> results = new ArrayList<>();
        for (StoreItem si : inventory) {
            if (si.getItem().getSize() == size) {
                results.add(si);
            }
        }
        return results;
    }

    public List<StoreItem> searchByMaxPrice(double maxPrice) {
        List<StoreItem> results = new ArrayList<>();
        for (StoreItem si : inventory) {
            if (si.getItem().getPrice() <= maxPrice) {
                results.add(si);
            }
        }
        return results;
    }

    public StoreItem searchByUuid(String uuidStr) {
        if (uuidStr == null || uuidStr.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID не може бути порожнім.");
        }
        UUID searchId = UUID.fromString(uuidStr.trim());
        for (StoreItem si : inventory) {
            if (si.getItem().getUuid().equals(searchId)) {
                return si;
            }
        }
        return null;
    }
}
