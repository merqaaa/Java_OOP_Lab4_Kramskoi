package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Store {
    private List<StoreItem> inventory;

    public Store() {
        this.inventory = new ArrayList<>();
    }

    public List<StoreItem> getInventory() { return inventory; }
    public void setInventory(List<StoreItem> inventory) { this.inventory = inventory; }

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

    // --- ЛР 18: Кидаємо ObjectNotFoundException ---
    public void delete(StoreItem existingObject) {
        if (existingObject == null) {
            throw new ObjectNotFoundException("Передано порожній об'єкт для видалення.");
        }
        
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getItem().getUuid().equals(existingObject.getItem().getUuid())) {
                inventory.remove(i);
                return; // Успішно видалено
            }
        }
        throw new ObjectNotFoundException("Товар не знайдено в базі магазину. Видалення скасовано.");
    }

    // --- ЛР 18: Кидаємо ObjectNotFoundException ---
    public void update(StoreItem existingObject, StoreItem newObject) {
        if (existingObject == null || newObject == null) {
            throw new ObjectNotFoundException("Передано некоректні об'єкти для модифікації.");
        }

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getItem().getUuid().equals(existingObject.getItem().getUuid())) {
                inventory.set(i, newObject); 
                return; // Успішно оновлено
            }
        }
        throw new ObjectNotFoundException("Товар для модифікації не знайдено в колекції.");
    }

    public List<StoreItem> searchByBrand(String brand) {
        List<StoreItem> results = new ArrayList<>();
        for (StoreItem si : inventory) {
            if (si.getItem().getBrand().equalsIgnoreCase(brand.trim())) results.add(si);
        }
        return results;
    }

    public List<StoreItem> searchBySize(Size size) {
        List<StoreItem> results = new ArrayList<>();
        for (StoreItem si : inventory) {
            if (si.getItem().getSize() == size) results.add(si);
        }
        return results;
    }

    public List<StoreItem> searchByMaxPrice(double maxPrice) {
        List<StoreItem> results = new ArrayList<>();
        for (StoreItem si : inventory) {
            if (si.getItem().getPrice() <= maxPrice) results.add(si);
        }
        return results;
    }

    public StoreItem searchByUuid(String uuidStr) {
        if (uuidStr == null || uuidStr.trim().isEmpty()) throw new InvalidFieldValueException("UUID порожній.");
        try {
            UUID searchId = UUID.fromString(uuidStr.trim());
            for (StoreItem si : inventory) {
                if (si.getItem().getUuid().equals(searchId)) return si;
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidFieldValueException("Некоректний формат UUID.");
        }
        return null;
    }
}
