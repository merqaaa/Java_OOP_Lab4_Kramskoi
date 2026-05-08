package ua.edu.sumdu.j2se.pr4;

import java.util.Objects;

public class Clothes {
    private String type;
    private String brand;
    private String size;
    private double price;

    public Clothes(String type, String brand, String size, double price) {
        this.type = type;
        this.brand = brand;
        this.size = size;
        this.price = price;
    }

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("Clothes[Type: %s, Brand: %s, Size: %s, Price: %.2f]", type, brand, size, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 && Objects.equals(type, clothes.type) && Objects.equals(brand, clothes.brand) && Objects.equals(size, clothes.size);
    }
}
