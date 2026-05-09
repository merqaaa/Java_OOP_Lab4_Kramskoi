package ua.edu.sumdu.j2se.pr4;

import java.util.Objects;

public class Clothes {
    private String type;
    private String brand;
    private Size size;
    private double price;

    public Clothes(String type, String brand, Size size, double price) {
        setType(type);
        setBrand(brand);
        setSize(size);
        setPrice(price);
    }

    public String getType() { return type; }
    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) throw new IllegalArgumentException("Тип порожній.");
        this.type = type;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) throw new IllegalArgumentException("Бренд порожній.");
        this.brand = brand;
    }

    public Size getSize() { return size; }
    public void setSize(Size size) { this.size = size; }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price <= 0) throw new IllegalArgumentException("Ціна <= 0.");
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Одяг [Тип: %s, Бренд: %s, Розмір: %s, Ціна: %.2f]", type, brand, size, price);
    }
}
