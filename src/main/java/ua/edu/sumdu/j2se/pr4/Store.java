package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Клас-агрегатор для керування колекцією товарів магазину.
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

    /**
     * Додає новий товар або оновлює кількість існуючого.
     */
    public void addNewClothes(Clothes cl, int quantity) {
        for (StoreItem storeItem : inventory) {
            // Перевірка на еквівалентність об'єктів
            if (storeItem.getItem().equals(cl)) {
                storeItem.addQuantity(quantity);
                System.out.println("Товар вже існує. Кількість оновлена.");
                return;
            }
        }
        inventory.add(new StoreItem(cl, quantity));
        System.out.println("Новий тип товару додано до магазину.");
    }

    // --- МЕТОДИ ПОШУКУ (Лабораторна №10-11) ---

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

    // --- НОВИЙ МЕТОД ПОШУКУ (Лабораторна №16) ---

    /**
     * Пошук конкретного товару за його унікальним ідентифікатором UUID.
     * @param uuidStr рядок у форматі UUID
     * @return знайдений StoreItem або null
     * @throws IllegalArgumentException якщо формат рядка некоректний
     */
    public StoreItem searchByUuid(String uuidStr) {
        if (uuidStr == null || uuidStr.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID не може бути порожнім.");
        }
        
        // Спроба перетворити рядок у тип UUID
        UUID searchId = UUID.fromString(uuidStr.trim());
        
        for (StoreItem si : inventory) {
            if (si.getItem().getUuid().equals(searchId)) {
                return si;
            }
        }
        return null;
    }
}
