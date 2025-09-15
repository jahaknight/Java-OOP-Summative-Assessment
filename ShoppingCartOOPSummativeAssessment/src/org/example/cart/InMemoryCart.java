package org.example.cart;

import org.example.model.CartLine;
import org.example.model.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCart implements Cart {
    private final Map<String, CartLine> itemsInCart;
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

    /**
     * Adds the given Item to the cart. If the Item does not already exist
     * in the cart then a new CartLine is created and added to the cart.
     *
     * @param item the Item to add to the cart
     * @param quantity the number of items to add to the cart
     */
    @Override
    public void addItemToCart(Item item, int quantity){
        String itemSKU = item.getSku();
        if(itemsInCart.containsKey(itemSKU)){
            itemsInCart.get(itemSKU).add(quantity);
        } else {
            CartLine newCartItem = new CartLine(item, quantity);
            itemsInCart.put(itemSKU, newCartItem);
        }
    }

    // Overloaded method when quantity is not given
    public void addItemToCart(Item item) {
        addItemToCart(item, 1);
    }


    /**
     * Removes the amount of the given Item from the cart. If the Item then
     * has a quantity of 0 or less then it is removed from the cart
     *
     * @param item The item to remove from the cart
     * @param quantity The quantity of the item to remove from the cart
     */
    @Override
    public void removeItemFromCart(Item item, int quantity) {
        String itemSKU = item.getSku();
        if(!itemsInCart.containsKey(itemSKU)){
            return;
        }
        itemsInCart.get(itemSKU).remove(quantity);
        if(itemsInCart.get(itemSKU).getQuantity() <= 0){
            itemsInCart.remove(itemSKU);
        }
    }

    // Overloaded method when quantity is not given
    public void removeItemFromCart(Item item){
        removeItemFromCart(item, 1);
    }

    /**
     * @return A subtotal of all items in the cart
     */
    @Override
    public BigDecimal getSubtotal() {
        BigDecimal subtotal = new BigDecimal("0");
        for(String sku : itemsInCart.keySet()){
            subtotal = subtotal.add(itemsInCart.get(sku).getLineTotal());
        }
        return subtotal;
    }

    /**
     * Clears the cart and returns the subtotal of all items
     */
    @Override
    public BigDecimal checkout() {
        BigDecimal subtotal = getSubtotal();
        itemsInCart.clear();
        return subtotal;
    }
}
