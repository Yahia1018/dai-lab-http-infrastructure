package dai.database;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    private static int serialId = 0;
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("kgPrice")
    private double kgPrice;
    @JsonProperty("isBio")
    private boolean isBio;
    @JsonProperty("origin")
    private String origin;
    @JsonProperty("imagePath")
    private String imagePath;

    public int id() { return id; }

    public String name() { return name; }

    public double kgPrice() { return kgPrice; }

    public boolean isBio() {
        return isBio;
    }

    public String origin() {
        return origin;
    }

    public String imagePath() {
        return imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKgPrice(double kgPrice) {
        if (kgPrice < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        this.kgPrice = kgPrice;
    }

    public void setIsBio(boolean isBio) {
        this.isBio = isBio;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Product() { this.id = ++serialId; }

    public Product(String name,
                   double kgPrice,
                   boolean isBio,
                   String origin,
                   String imagePath) {
        this.id = ++serialId;
        this.name = name;
        this.kgPrice = kgPrice;
        this.isBio = isBio;
        this.origin = origin;
        this.imagePath = imagePath;
    }

    public void inflation() {
        kgPrice *= 1.1;
    }

    public void sale() { kgPrice *= 0.5; }
}
