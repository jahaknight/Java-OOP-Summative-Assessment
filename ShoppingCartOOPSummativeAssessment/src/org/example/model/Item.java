package org.example.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private final String sku;                   // Unique Code, e.g. "MRC"
    private final String name;
    private final BigDecimal unitPrice;         // Price per unit

    /**
     *
     * @param sku Identifier code for this Item
     * @param name Item Name
     * @param unitPrice Price of item
     *
     * @throws IllegalArgumentException when a parameter is null are the equivalent of "empty"
     */
    public Item(String sku, String name, BigDecimal unitPrice) {
        // Basic validation so we don't build bad item
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("sku required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (unitPrice == null || unitPrice.signum() < 0) throw new IllegalArgumentException("price >= 0");

        // Normalize : SKU upper-case, name trimmed
        this.sku = sku.trim().toUpperCase();
        this.name = name.trim();
        this.unitPrice = unitPrice;
    }

    // Getters (no setters = immutable)
    public String getSku() { return sku; }
    public String getName() {return name; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    /**
     * Identifies an Item's equivalency by SKU value as it is unique and
     * immutable for each item.
     *
     * @param o   the reference object with which to compare.
     * @return {@code true} if the object are equal, otherwise {@code false}
     */
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        return sku.equals(((Item) o).sku);
    }

    @Override public String toString() {
        return sku + " - " + name + " ($" + unitPrice + ")";
    }
}
