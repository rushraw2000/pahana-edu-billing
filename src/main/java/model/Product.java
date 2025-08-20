package model;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int stockQuantity;
    private String description;

    public Product() {}

    public Product(int productId, String productName, double price, int stockQuantity, String description) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public String getDescription() { return description; }
}