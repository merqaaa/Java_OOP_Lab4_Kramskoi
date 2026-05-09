package ua.edu.sumdu.j2se.pr4;

public class Jeans extends Pants {
    private String washType; // Тип прання (напр. Stone Wash, Dark)

    public Jeans(String brand, Size size, double price, int length, String washType) {
        super(brand, size, price, length);
        setWashType(washType);
        setType("Джинси");
    }

    public String getWashType() { return washType; }
    public void setWashType(String washType) { this.washType = washType; }

    @Override
    public String toString() {
        return super.toString() + " | Тип прання: " + washType;
    }
}
