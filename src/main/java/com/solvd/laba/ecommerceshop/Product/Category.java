package com.solvd.laba.ecommerceshop.Product;

public enum Category{
    HOMEGOODS("Home Goods", "Items for the home"),
    BOOKS("Books", "Literature and publications"),
    ELECTRONICS("Electronics", "Electronic devices");

    private final String categoryName;
    private final String description;

    Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    // Additional method to get a formatted string of enum details
    public String getDetails() {
        return String.format("Category: %s, Description: %s", categoryName, description);
    }

    // Additional method to perform certain operations based on category
    public void performOperation() {
        // Perform an operation specific to each category
        switch (this) {
            case HOMEGOODS:
                System.out.println("Handling home goods...");
                break;
            case BOOKS:
                System.out.println("Managing books...");
                break;
            case ELECTRONICS:
                System.out.println("Handling electronics...");
                break;
            default:
                System.out.println("Unknown category...");
                break;
        }
    }
}
