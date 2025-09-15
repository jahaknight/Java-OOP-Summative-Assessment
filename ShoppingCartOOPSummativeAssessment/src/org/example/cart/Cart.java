package org.example.cart;

import org.example.model.CartLine;
import org.example.model.Item;

import java.math.BigDecimal;
import java.util.List;

public interface Cart {
    public void addItemToCart(Item item, int quantity);
    public void removeItemFromCart(Item item, int quantity);
    public BigDecimal getSubtotal();
    public BigDecimal checkout();
}
