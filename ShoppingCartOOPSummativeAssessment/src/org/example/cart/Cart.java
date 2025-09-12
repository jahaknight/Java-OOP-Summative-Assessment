package org.example.cart;

import org.example.model.Item;

public interface Cart {
    public void addItemToCart(Item item, int quantity);
    public void removeItemFromCart(Item item, int quantity);
    public double getSubtotal();
    public void checkout();
}
