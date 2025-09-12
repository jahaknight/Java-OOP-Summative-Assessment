package org.example.view;

import org.example.cart.Cart;
import org.example.cart.InMemoryCart;
import org.example.catalog.Catalog;
import org.example.catalog.StaticCatalog;
import org.example.model.CartLine;
import org.example.model.Item;

import java.math.BigDecimal;
import java.util.List;

public class MenuController {

    private InMemoryCart myCart;
    private StaticCatalog catalog;
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
        List<CartLine> items = myCart.getLines();
        for(CartLine item : items){
            console.writeMessage(item.toString());
        }

    }

    /**
     * Prints a list of all items in the catalog then prompts user for
     * the Item and quantity. Then inputted amount is then added to the cart.
     */
    private void addItemToCart(){
        List<Item> catalogItems = catalog.getCatalogItems();
        // print available catalog items
        printOrderedItems(catalogItems);
        int itemIndex = console.getIntegerInputInRange("What item would you like to add to your cart:", 1, catalogItems.size());
        itemIndex--; // adjust for 0-index
        Item itemToAdd = catalogItems.get(itemIndex);
        int quantity = console.getIntegerInput("How much " + itemToAdd.getName() + " would you like to add: ");
        myCart.addItemToCart(itemToAdd, quantity);
    }

    private void removeItemFromCart(){
        List<CartLine> cartLineList = myCart.getLines();
        printOrderedCartItems(cartLineList);
        int itemIndex = console.getIntegerInputInRange("What item would you like to remove from your cart:", 1, cartLineList.size());
        itemIndex--;
        Item itemToRemove = cartLineList.get(itemIndex).getItem();
        int quantity = console.getIntegerInput("How many " + itemToRemove.getName() + "s would you like to remove: ");
        myCart.removeItemFromCart(itemToRemove, quantity);
    }

    private void checkout(){

        displayCart();
        BigDecimal subtotal = myCart.checkout();
        console.writeMessage("Your total is: $" + subtotal.toString());
        console.writeMessage("Cart is emptied");
    }

    private void printOrderedItems(List<Item> items){
        int i = 1;
        for(Item item : items){
            console.writeMessage(i + ". " + item.getName());
            i++;
        }
    }

    private void printOrderedCartItems(List<CartLine> items){
        int i = 1;
        for(CartLine item : items){
            console.writeMessage(i + ". " + item.getItem().getName() + "  x " + item.getQuantity()); // 1. Water  x 2
            i++;
        }
    }

}
