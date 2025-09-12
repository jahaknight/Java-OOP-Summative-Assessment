package org.example.cart;

import org.example.model.CartLine;
import org.example.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCart implements Cart{

    private Map<String, CartLine> itemsInCart;

    public InMemoryCart(){
        itemsInCart = new HashMap<>();
    }

    public List<CartLine> getLines(){
        List<CartLine> lines = new ArrayList<>();
        for(String sku : itemsInCart.keySet()){
            lines.add(itemsInCart.get(sku));
        }
        return lines;
    }

    @Override
    public void addItemToCart(Item item) {

    }

    @Override
    public void removeItemFromCart(Item item) {

    }

    @Override
    public double getSubtotal() {
        return 0;
    }

    @Override
    public void checkout() {

    }
}
