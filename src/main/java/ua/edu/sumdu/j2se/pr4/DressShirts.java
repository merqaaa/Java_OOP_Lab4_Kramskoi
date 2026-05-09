package ua.edu.sumdu.j2se.pr4;

public class DressShirts extends Shirts {
    private double collarSize;

    public DressShirts() {}

    public DressShirts(String brand, Size size, double price, String sleeveType, double collarSize) {
        super(brand, size, price, sleeveType);
        this.classType = "DressShirts";
        setCollarSize(collarSize);
        setType("Офіційна сорочка");
    }

    public double getCollarSize() { return collarSize; }
    public void setCollarSize(double collarSize) {
        if (collarSize <= 0) throw new IllegalArgumentException("Комір має бути > 0");
        this.collarSize = collarSize;
    }

    @Override
    public String toString() {
        return super.toString() + " | Комір: " + collarSize + " см";
    }
}
