package ua.edu.sumdu.j2se.pr4;

import java.util.Objects;
import java.util.UUID;

public abstract class Clothes implements Comparable<Clothes>, Identifiable {
    protected String classType;
    private UUID uuid;
    private String type;
    private String brand;
    private Size size;
    private double price;

    public Clothes() {
        this.uuid = UUID.randomUUID();
    }

    public Clothes(String type, String brand, Size size, double price) {
        this.uuid = UUID.randomUUID();
        this.classType = "Clothes";
        setType(type);
        setBrand(brand);
        setSize(size);
        setPrice(price);
    }

    @Override
    public UUID getUuid() { return uuid; }

    @Override
    public int compareTo(Clothes other) {
        if (other == null) return 1;
        return Double.compare(this.price, other.price);
    }

    public String getClassType() { return classType; }
    public String getType() { return type; }
    
    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) 
            throw new InvalidFieldValueException("Тип одягу не може бути порожнім.");
        this.type = type;
    }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) 
            throw new InvalidFieldValueException("Бренд не може бути порожнім.");
        this.brand = brand;
    }
    
    public Size getSize() { return size; }
    public void setSize(Size size) { 
        if (size == null) throw new InvalidFieldValueException("Розмір не обрано.");
        this.size = size; 
    }
    
    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price <= 0) 
            throw new InvalidFieldValueException("Ціна повинна бути більшою за 0.");
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("[UUID: %s] Одяг [Тип: %s, Бренд: %s, Розмір: %s, Ціна: %.2f]", 
                uuid.toString().substring(0,8) + "...", type, brand, size, price); // Короткий UUID для зручності
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clothes clothes)) return false;
        return Double.compare(clothes.price, price) == 0 && Objects.equals(type, clothes.type) && 
               Objects.equals(brand, clothes.brand) && size == clothes.size && Objects.equals(uuid, clothes.uuid);
    }
}
