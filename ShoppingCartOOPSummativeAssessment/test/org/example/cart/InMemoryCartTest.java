package org.example.cart;

import org.example.catalog.StaticCatalog;
import org.example.model.CartLine;
import org.example.model.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    /**
     * What we will verify:
     * 1. Adding items increases subtotal correctly
     * 2. Adding the SAME SKU bumps quantity on ONE line (no duplicates)
     * 3. Removing decrements quantity line is removed ONLY when quantity hits 0
     * 4. Checkout returns total and EMPTIES the cart
     * 5. Unknown SKU operations throw
     * 6. Invalid quantities (<=0) throw
     * 7. getLines() returns a defensive copy (can't mutate intenal state).
     * 8. SKU lookup is case-insensititve (e.g. "apl" == "APL"
     */

    class InMemoryCartTest {

        // Helper: find a line by SKU in a snapshot list
        private CartLine findBySku(List<CartLine> lines, String sku) {
            String key = sku.trim().toUpperCase();
            return lines.stream()
                    .filter(l -> l.getItem().getSku().equals(key))
                    .findFirst()
                    .orElse(null);
        }

        @Test
        void emptyCartSubtotalIsZero() {
            InMemoryCart cart = new InMemoryCart();
            assertEquals(0.0, cart.getSubtotal(), 1e-9);
            assertTrue(cart.getLines().isEmpty());
        }

        @Test
        void addThenSubtotalAccumulates() {
            StaticCatalog cat = new StaticCatalog();
            Item apple = cat.findItemBySKU("APL"); // 0.89
            Item milk  = cat.findItemBySKU("MLK"); // 3.49

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(apple, 2); // 1.78
            cart.addItemToCart(milk, 1);  // +3.49 = 5.27

            assertEquals(5.27, cart.getSubtotal(), 1e-9);
        }

        @Test
        void addSameItemMergesQuantityNotNewLine() {
            StaticCatalog cat = new StaticCatalog();
            Item eggs = cat.findItemBySKU("EGG");

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(eggs, 1);
            cart.addItemToCart(eggs, 2);

            List<CartLine> lines = cart.getLines();
            assertEquals(1, lines.size(), "Same item should not create a new line");
            assertEquals(3, lines.get(0).getQuantity(), "Quantities should merge");
        }

        @Test
        void removeDecrementsAndDeletesWhenZero() {
            StaticCatalog cat = new StaticCatalog();
            Item bread = cat.findItemBySKU("BRD");

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(bread, 3);

            cart.removeItemFromCart(bread, 1);
            CartLine line = findBySku(cart.getLines(), "BRD");
            assertNotNull(line);
            assertEquals(2, line.getQuantity());

            cart.removeItemFromCart(bread, 2); // qty hits 0 → line removed
            assertNull(findBySku(cart.getLines(), "BRD"));
            assertTrue(cart.getLines().isEmpty());
        }

        @Test
        void removeMoreThanExistingClampsToZeroAndDeletesLine() {
            StaticCatalog cat = new StaticCatalog();
            Item chips = cat.findItemBySKU("CHP");

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(chips, 2);
            cart.removeItemFromCart(chips, 5); // > current qty

            assertTrue(cart.getLines().isEmpty());
            assertEquals(0.0, cart.getSubtotal(), 1e-9);
        }

        @Test
        void invalidQuantitiesThrow() {
            StaticCatalog cat = new StaticCatalog();
            Item apple = cat.findItemBySKU("APL");

            InMemoryCart cart = new InMemoryCart();

            // add: qty must be > 0
            assertThrows(IllegalArgumentException.class, () -> cart.addItemToCart(apple, 0));
            assertThrows(IllegalArgumentException.class, () -> cart.addItemToCart(apple, -3));

            cart.addItemToCart(apple, 2); // valid baseline

            // remove: qty must be > 0
            assertThrows(IllegalArgumentException.class, () -> cart.removeItemFromCart(apple, 0));
            assertThrows(IllegalArgumentException.class, () -> cart.removeItemFromCart(apple, -2));
        }

        @Test
        void removingItemNotInCartDoesNothing() {
            StaticCatalog cat = new StaticCatalog();
            Item milk  = cat.findItemBySKU("MLK");
            Item apple = cat.findItemBySKU("APL");

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(apple, 1);

            // MLK not in cart; current design = no-op
            cart.removeItemFromCart(milk, 1);

            assertEquals(1, cart.getLines().size());
            assertNotNull(findBySku(cart.getLines(), "APL"));
            assertNull(findBySku(cart.getLines(), "MLK"));
        }

        @Test
        void checkoutEmptiesCartAndSubtotalBecomesZero() {
            StaticCatalog cat = new StaticCatalog();
            Item apple = cat.findItemBySKU("APL");
            Item chips = cat.findItemBySKU("CHP");

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(apple, 2);
            cart.addItemToCart(chips, 1);

            cart.checkout(); // returns void, should empty the cart

            assertTrue(cart.getLines().isEmpty());
            assertEquals(0.0, cart.getSubtotal(), 1e-9);
        }

        @Test
        void getLinesReturnsDefensiveCopy() {
            StaticCatalog cat = new StaticCatalog();
            Item apple = cat.findItemBySKU("APL");

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(apple, 2);

            List<CartLine> snapshot = cart.getLines();
            snapshot.clear(); // mutate the copy

            // Cart must be unaffected
            assertEquals(1, cart.getLines().size());
        }

        @Test
        void addNullItemThrows() {
            InMemoryCart cart = new InMemoryCart();
            assertThrows(IllegalArgumentException.class, () -> cart.addItemToCart(null, 1));
        }
    }