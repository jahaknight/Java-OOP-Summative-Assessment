package org.example.model;

import java.math.BigDecimal;

/**
 * One row in the shopping cart:
 * - Item being purchased
 * - Quantity of that item
 * - Can compute its own line total (unit price * quantity)
 *
 * Quantity is mutable (changes when you add/remove).
 * Item is immutable (price/name/sku don't change).
 */

public class CartLine {
    private final Item item;   // product (immutable)
    private int quantity;      // current count in the cart (>= 0)

    public CartLine(Item item, int quantity) {
        if (item == null) throw new IllegalArgumentException("item required");
        if (quantity <= 0) throw new IllegalArgumentException("quantity > 0 required");
        this.item = item;
        this.quantity = quantity;
    }

    // getters
    public Item getItem()     { return item; }
    public int  getQuantity() { return quantity; }

    /** Increase quantity by a positive amount. */
    public void add(int delta) {
        if (delta <= 0) throw new IllegalArgumentException("delta > 0");
        quantity += delta;
    }

    /**
     * Decrease quantity by a positive amount.
     * Clamps at zero (never negative).
     * @return the new quantity (0 means the cart should remove this line)
     */

    public int remove(int delta) {
        if (delta <= 0) throw new IllegalArgumentException("delta > 0");
        quantity = Math.max(0, quantity - delta);
        return quantity;
    }

    /** Line total = unitPrice * quantity (exact money math via BigDecimal). */
    public BigDecimal getLineTotal() {
        return item.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return item.getSku() + " | " + item.getName()
                + " | qty=" + quantity
                + " | $" + getLineTotal();
    }
}

