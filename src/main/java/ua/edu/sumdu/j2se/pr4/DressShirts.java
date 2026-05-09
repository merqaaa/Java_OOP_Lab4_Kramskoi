package ua.edu.sumdu.j2se.pr4;

public class DressShirts extends Shirts {
    private double collarSize; // Розмір коміра

    public DressShirts(String brand, Size size, double price, String sleeveType, double collarSize) {
        super(brand, size, price, sleeveType);
        setCollarSize(collarSize);
        setType("Офіційна сорочка");
    }

    public double getCollarSize() { return collarSize; }
    public void setCollarSize(double collarSize) {
        if (collarSize <= 0) throw new IllegalArgumentException("Розмір коміра має бути > 0");
        this.collarSize = collarSize;
    }

    @Override
    public String toString() {
        return super.toString() + " | Комір: " + collarSize + " см";
    }
}
