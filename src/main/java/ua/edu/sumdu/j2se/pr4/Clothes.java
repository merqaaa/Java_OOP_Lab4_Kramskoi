package ua.edu.sumdu.j2se.pr4;

import java.util.Objects;

/**
 * Клас, що представляє предмет одягу.
 * Містить інформацію про тип, бренд, розмір та ціну.
 */
public class Clothes {
    private String type;
    private String brand;
    private String size;
    private double price;

    /**
     * Конструктор для створення об'єкта Clothes.
     *
     * @param type  тип одягу (не може бути порожнім)
     * @param brand бренд одягу (не може бути порожнім)
     * @param size  розмір одягу (не може бути порожнім)
     * @param price ціна одягу (повинна бути більшою за 0)
     * @throws IllegalArgumentException якщо передані некоректні дані
     */
    public Clothes(String type, String brand, String size, double price) {
        setType(type);
        setBrand(brand);
        setSize(size);
        setPrice(price);
    }

    /** Отримує тип одягу. */
    public String getType() { return type; }

    /**
     * Встановлює тип одягу.
     * @param type тип одягу
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип одягу не може бути порожнім.");
        }
        this.type = type;
    }

    /** Отримує бренд одягу. */
    public String getBrand() { return brand; }

    /**
     * Встановлює бренд одягу.
     * @param brand бренд одягу
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Бренд не може бути порожнім.");
        }
        this.brand = brand;
    }

    /** Отримує розмір одягу. */
    public String getSize() { return size; }

    /**
     * Встановлює розмір одягу.
     * @param size розмір одягу
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Розмір не може бути порожнім.");
        }
        this.size = size;
    }

    /** Отримує ціну одягу. */
    public double getPrice() { return price; }

    /**
     * Встановлює ціну одягу.
     * @param price ціна одягу
     * @throws IllegalArgumentException якщо ціна менша або дорівнює 0
     */
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна повинна бути більшою за нуль.");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Clothes[Type: %s, Brand: %s, Size: %s, Price: %.2f]", type, brand, size, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 && 
               Objects.equals(type, clothes.type) && 
               Objects.equals(brand, clothes.brand) && 
               Objects.equals(size, clothes.size);
    }
}
