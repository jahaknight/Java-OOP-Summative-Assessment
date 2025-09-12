package org.example.model;

import java.math.BigDecimal;
import java.util.Objects;

// Represents a product in the store catalog
// Immutable (all fields final, no setters)
// Uses BigDecimal for money to avoid float rounding issues

public class Item {
    private final String sku;                   // Unique Code, e.g. "MRC"
    private final String name;
    private final BigDecimal unitPrice;         // Price per unit

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

    // Identity by SKU (SKUs are unique)
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        return sku.equals(((Item) o).sku);
    }

    @Override public int hashCode() { return Objects.hash(sku); }

    @Override public String toString() {
        return sku + " - " + name + " ($" + unitPrice + ")";
    }
}
