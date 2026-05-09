package ua.edu.sumdu.j2se.pr4;

import java.util.Objects;

/**
 * Клас предметної області "Одяг" з валідацією даних.
 */
public class Clothes {
    // Статичне поле для підрахунку всіх створених об'єктів
    private static int totalClothesCreated = 0;

    private String type;
    private String brand;
    private Size size; // Використання Enum
    private double price;

    /**
     * Основний конструктор.
     */
    public Clothes(String type, String brand, Size size, double price) {
        setType(type);
        setBrand(brand);
        setSize(size);
        setPrice(price);
        totalClothesCreated++; // Збільшуємо лічильник при створенні
    }

    /**
     * Конструктор копіювання.
     * @param other об'єкт для копіювання
     */
    public Clothes(Clothes other) {
        if (other == null) {
            throw new IllegalArgumentException("Неможливо скопіювати null об'єкт.");
        }
        this.type = other.type;
        this.brand = other.brand;
        this.size = other.size;
        this.price = other.price;
        totalClothesCreated++; // Збільшуємо лічильник при копіюванні
    }

    /** Статичний гетер для лічильника об'єктів */
    public static int getTotalClothesCreated() {
        return totalClothesCreated;
    }

    public String getType() { return type; }
    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип не може бути порожнім.");
        }
        this.type = type;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Бренд не може бути порожнім.");
        }
        this.brand = brand;
    }

    public Size getSize() { return size; }
    public void setSize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Розмір не може бути null.");
        }
        this.size = size;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна повинна бути більшою за 0.");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Clothes[Тип: %s, Бренд: %s, Розмір: %s, Ціна: %.2f]", type, brand, size, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 && Objects.equals(type, clothes.type) && Objects.equals(brand, clothes.brand) && size == clothes.size;
    }
}
