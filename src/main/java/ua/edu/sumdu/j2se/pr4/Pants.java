package ua.edu.sumdu.j2se.pr4;

public class Pants extends Clothes {
    private int length;

    public Pants() {}

    public Pants(String brand, Size size, double price, int length) {
        super("Штани", brand, size, price);
        this.classType = "Pants";
        setLength(length);
    }

    public int getLength() { return length; }
    public void setLength(int length) {
        if (length <= 0) throw new IllegalArgumentException("Довжина повинна бути > 0.");
        this.length = length;
    }

    @Override
    public String toString() {
        return super.toString() + " | Довжина: " + length + " см";
    }
}
