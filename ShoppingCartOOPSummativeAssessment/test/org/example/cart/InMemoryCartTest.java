package org.example.cart;

import org.example.catalog.StaticCatalog;
import org.example.model.CartLine;
import org.example.model.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    /**
     * What we will verify:
     *  - addItemToCart(Item,int) / removeItemFromCart(Item,int)
     *  - getLines(): List<CartLine>
     *  - getSubtotal(): BigDecimal
     *  - checkout(): BigDecimal (returns total, then empties cart)
     */
    class InMemoryCartTest {

        // Helper: BigDecimal numeric equality (ignores scale like 5.27 vs 5.270)
        private static void assertBigEq(String expected, BigDecimal actual) {
            assertEquals(0, actual.compareTo(new BigDecimal(expected)),
                    "expected " + expected + " but was " + actual);
        }

        // Helper: find a line by SKU in a snapshot list
        private static CartLine findBySku(List<CartLine> lines, String sku) {
            String key = sku.trim().toUpperCase();
            return lines.stream()
                    .filter(l -> l.getItem().getSku().equals(key))
                    .findFirst()
                    .orElse(null);
        }

        @Test
        void emptyCartSubtotalIsZero() {
            InMemoryCart cart = new InMemoryCart();
            assertBigEq("0.00", cart.getSubtotal());
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

            assertBigEq("5.27", cart.getSubtotal());
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
            // Subtotal sanity: 3 * 2.99 = 8.97 (if eggs are 2.99 in your catalog)
            assertBigEq(eggs.getUnitPrice().multiply(new BigDecimal("3")).toPlainString(), cart.getSubtotal());
        }

        @Test
        void removeDecrementsAndDeletesWhenZero() {
            StaticCatalog cat = new StaticCatalog();
            Item bread = cat.findItemBySKU("BRD"); // 2.79

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(bread, 3);         // 8.37

            cart.removeItemFromCart(bread, 1);    // 2 left
            CartLine line = findBySku(cart.getLines(), "BRD");
            assertNotNull(line);
            assertEquals(2, line.getQuantity());
            assertBigEq(bread.getUnitPrice().multiply(new BigDecimal("2")).toPlainString(),
                    cart.getSubtotal());

            cart.removeItemFromCart(bread, 2);    // hits zero → line removed
            assertNull(findBySku(cart.getLines(), "BRD"));
            assertTrue(cart.getLines().isEmpty());
            assertBigEq("0.00", cart.getSubtotal());
        }

        @Test
        void removeMoreThanExistingClampsToZeroAndDeletesLine() {
            StaticCatalog cat = new StaticCatalog();
            Item chips = cat.findItemBySKU("CHP");

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(chips, 2);
            cart.removeItemFromCart(chips, 5); // > current qty

            assertTrue(cart.getLines().isEmpty());
            assertBigEq("0.00", cart.getSubtotal());
        }

        @Test
        void invalidQuantitiesThrow() {
            StaticCatalog cat = new StaticCatalog();
            Item apple = cat.findItemBySKU("APL");

            InMemoryCart cart = new InMemoryCart();

            // add: qty must be > 0 (CartLine/CartLine.add should throw)
            assertThrows(IllegalArgumentException.class, () -> cart.addItemToCart(apple, 0));
            assertThrows(IllegalArgumentException.class, () -> cart.addItemToCart(apple, -3));

            cart.addItemToCart(apple, 2); // valid baseline

            // remove: qty must be > 0 (CartLine.remove should throw)
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
        void checkoutReturnsTotalAndEmptiesCart() {
            StaticCatalog cat = new StaticCatalog();
            Item apple = cat.findItemBySKU("APL"); // 0.89
            Item chips = cat.findItemBySKU("CHP"); // 1.99

            InMemoryCart cart = new InMemoryCart();
            cart.addItemToCart(apple, 2); // 1.78
            cart.addItemToCart(chips, 1); // +1.99 = 3.77

            BigDecimal due = cart.checkout();     // now returns BigDecimal
            assertBigEq("3.77", due);

            assertTrue(cart.getLines().isEmpty());
            assertBigEq("0.00", cart.getSubtotal());
        }

        @Test
        void addNullItemThrowsNpeWithCurrentImplementation() {
            InMemoryCart cart = new InMemoryCart();
            // current code dereferences item.getSku() without null check
            assertThrows(NullPointerException.class, () -> cart.addItemToCart(null, 1));
        }
    }