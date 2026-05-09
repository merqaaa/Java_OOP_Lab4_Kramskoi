package ua.edu.sumdu.j2se.pr4;

public class Shirts extends Clothes {
    private String sleeveType;

    public Shirts(String brand, Size size, double price, String sleeveType) {
        super("Сорочка", brand, size, price);
        setSleeveType(sleeveType);
    }

    public String getSleeveType() { return sleeveType; }
    public void setSleeveType(String sleeveType) {
        this.sleeveType = sleeveType;
    }

    @Override
    public String toString() {
        return super.toString() + " | Рукав: " + sleeveType;
    }
}
