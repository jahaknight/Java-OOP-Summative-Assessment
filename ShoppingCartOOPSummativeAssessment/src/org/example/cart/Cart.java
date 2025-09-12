package org.example.cart;

import org.example.model.Item;

public interface Cart {
    public void addItemToCart(Item item);
    public void removeItemFromCart(Item item);
    public double getSubtotal();
    public void checkout();
}
