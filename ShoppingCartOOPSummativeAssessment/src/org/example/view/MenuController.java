package org.example.view;

import org.example.cart.Cart;
import org.example.cart.InMemoryCart;
import org.example.catalog.Catalog;
import org.example.catalog.StaticCatalog;

public class MenuController {

    private Cart myCart;
    private Catalog catalog;
    private ConsoleIO console;

    public MenuController(){
        myCart = new InMemoryCart();
        catalog = new StaticCatalog();
        console = new ConsoleIO();
    }

    /*
    1. display
    2. remove item
    3. add item
    4. checkout
    5. exit (return false)
     */
    public boolean handleAction(int action){
        // TODO
        /*
        there are 5 possible options: display, add, remove, checkout exit
        each action calls its respective method
        if user chooses exit then return false to signify end of program
        otherwise return true
         */
        switch (action){
            case 1:
                displayCart();
                break;
            case 2:
                removeItemFromCart();
                break;
            case 3:
                addItemToCart();
                break;
            case 4:
                checkout();
                break;
            case 5:
                return false;
        }
        return true;
    }

    private void displayCart(){
        // TODO
        /*
        get all items in cart and display as such
        ProductName
            Quantity: [quantity]
            Cost: $[cost]
         */

    }

    private void addItemToCart(){
        // TODO
        /*
        ask user to choose item from catalog
        ask user quantity they would like to add
        give user price and ask for confirmation
        if confirm add item quantity to cart, if deny exit method

        have option to return back to menu
         */
    }

    private void removeItemFromCart(){
        // TODO
        /*
        ask user which item they would like to remove
        ask quantity they would like to remove
        ask for confirmation

        have option to return back to menu
         */
    }

    private void checkout(){
        // TODO
        /*
        display all items and final cost
        empty entire cart
         */
    }

}
