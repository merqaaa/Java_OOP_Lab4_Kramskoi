package ua.edu.sumdu.j2se.pr4;

/**
 * Клас-обгортка, що містить товар та його кількість у магазині.
 */
public class StoreItem {
    private Clothes item;
    private int quantity;

    public StoreItem() {} // Для Gson

    public StoreItem(Clothes item, int quantity) {
        if (item == null) throw new IllegalArgumentException("Товар не може бути null.");
        if (quantity <= 0) throw new IllegalArgumentException("Кількість має бути > 0.");
        this.item = item;
        this.quantity = quantity;
    }

    public Clothes getItem() { return item; }
    public void setItem(Clothes item) { this.item = item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { 
        if (quantity < 0) throw new IllegalArgumentException("Кількість не може бути від'ємною.");
        this.quantity = quantity; 
    }

    public void addQuantity(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Кількість для додавання має бути > 0.");
        this.quantity += amount;
    }

    @Override
    public String toString() {
        return item.toString() + " | В наявності: " + quantity + " шт.";
    }
}
