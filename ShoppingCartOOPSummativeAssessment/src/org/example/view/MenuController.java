package org.example.view;

import org.example.cart.InMemoryCart;
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

    /**
     * This method is called solely by App.java to handle the functionality
     * of the main loop and user interaction. It responds according to
     * the user input and returns a boolean based on whether the user
     * has chosen to exit or not.
     *
     * @param action An integer representing the user's menu choice
     * @return {@code false} is user has chosen 5. Exit otherwise {@code true}
     */
    public boolean handleAction(int action){
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

    /**
     * Displays all items in the cart in the format
     * {@code SKU} | {@code ITEM_NAME} | {@code QUANTITY} | {@code PRICE}
     */
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

    /**
     * Removes an item and quantity from the cart
     *
     * <p>User is prompted for the Item to remove and then for the quantity of the item
     * to remove restricting the user's choice to within the range of the current
     * quantity of the chosen Item.</p>
     */
    private void removeItemFromCart(){
        List<CartLine> cartLineList = myCart.getLines();
        printOrderedCartItems(cartLineList);
        int itemIndex = console.getIntegerInputInRange("What item would you like to remove from your cart:", 1, cartLineList.size());
        itemIndex--;
        Item itemToRemove = cartLineList.get(itemIndex).getItem();
        int quantity = console.getIntegerInputInRange("How many " + itemToRemove.getName() + "s would you like to remove: ", 1, cartLineList.get(itemIndex).getQuantity());
        myCart.removeItemFromCart(itemToRemove, quantity);
    }

    /**
     * Displays all items and the subtotal. Afterward it then empties the cart.
     */
    private void checkout(){
        displayCart();
        BigDecimal subtotal = myCart.checkout();
        console.writeMessage("Your total is: $" + subtotal.toString());
        console.writeMessage("Cart is emptied");
    }

    /**
     * Prints all Items to the screen with numbering
     * to ease user input
     *
     * <p>Ex: 1. Bread   2. Milk   3. Watermelon   etc.</p>
     *
     * @param items The items to be numbered and printed
     */
    private void printOrderedItems(List<Item> items){
        int i = 1;
        for(Item item : items){
            console.writeMessage(i + ". " + item.getName());
            i++;
        }
    }

    /**
     * Prints all Items to the screen with numbering
     * to ease user input. Uses a different format to make the quantity
     * of each item more visible when removing items.
     *
     * <p>Ex: 1. Bread x 2  2. Milk x 1   3. Watermelon x 3   etc.</p>
     *
     * @param items The CartLine items to be numbered and printed
     */
    private void printOrderedCartItems(List<CartLine> items){
        int i = 1;
        for(CartLine item : items){
            console.writeMessage(i + ". " + item.getItem().getName() + "  x " + item.getQuantity()); // 1. Water  x 2
            i++;
        }
    }

}
