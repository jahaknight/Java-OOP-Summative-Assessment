package org.example.model;

import java.math.BigDecimal;

/**
 * One row in the shopping cart:
 * <p>- Item being purchased</p>
 * <p>- Quantity of that item</p>
 * <p>- Can compute its own line total (unit price * quantity)</p>
 *
 * <p>Quantity is mutable (changes when you add/remove).</p>
 * <p>Item is immutable (price/name/sku don't change).</p>
 */
public class CartLine {
    private final Item item;   // product (immutable)
    private int quantity;      // current count in the cart (>= 0)

    /**
     * @param item Item being tracked in this section of the cart
     * @param quantity Amount of the item in the cart
     * @throws IllegalArgumentException when {@code item} is null
     * or {@code quantity} is less than or equal to zero
     */
    public CartLine(Item item, int quantity) {
        if (item == null) throw new IllegalArgumentException("item required");
        if (quantity <= 0) throw new IllegalArgumentException("quantity > 0 required");
        this.item = item;
        this.quantity = quantity;
    }

    // getters
    public Item getItem()     { return item; }
    public int  getQuantity() { return quantity; }

    /**
     * Increases an item's quantity by the positive inputted amount.
     *
     * @param delta A positive number by which to increase the item quantity
     * @throws IllegalArgumentException when {@code delta} is negative or zero
     */
    public void add(int delta) {
        if (delta <= 0) throw new IllegalArgumentException("delta > 0");
        quantity += delta;
    }

    /**
     * Reduces the item quantity by a positive amount. If the quantity is lowered
     * below zero then it is defaulted to zero.
     *
     * @param delta A positive number by which to decrease the item quantity
     * @throws IllegalArgumentException when {@code delta} is negative or zero
     */
    public void remove(int delta) {
        if (delta <= 0) throw new IllegalArgumentException("delta > 0");
        quantity = Math.max(0, quantity - delta);
    }

    /**
     * @return The total price of the item and its quantity
     */
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

