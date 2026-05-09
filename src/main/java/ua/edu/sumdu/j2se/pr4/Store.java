package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<StoreItem> inventory;

    public Store() {
        this.inventory = new ArrayList<>();
    }

    public void setInventory(List<StoreItem> inventory) {
        this.inventory = inventory;
    }

    public List<StoreItem> getInventory() {
        return inventory;
    }

    // Додавання товару з урахуванням кількості
    public void addNewClothes(Clothes cl, int quantity) {
        // Перевіряємо, чи є вже такий товар (використовуємо equals з Clothes)
        for (StoreItem storeItem : inventory) {
            if (storeItem.getItem().equals(cl)) {
                storeItem.addQuantity(quantity);
                System.out.println("Такий товар вже є. Кількість збільшено на " + quantity);
                return;
            }
        }
        // Якщо немає - створюємо нову обгортку
        inventory.add(new StoreItem(cl, quantity));
        System.out.println("Новий товар додано до магазину!");
    }

    // --- МЕТОДИ ПОШУКУ (без Stream API) ---

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
}
