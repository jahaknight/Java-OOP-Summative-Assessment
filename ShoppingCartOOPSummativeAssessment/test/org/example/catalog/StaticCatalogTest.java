package org.example.catalog;

import org.example.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StaticCatalogTest {

    private StaticCatalog catalog;
    final private Item TEST_ITEM = new Item("CHP", "Chips", new BigDecimal("1.99"));

    @BeforeEach
    public void setup(){
        catalog = new StaticCatalog();
    }

    @Test
    public void getCatalogItemsReturnsCorrectList(){
        List<Item> expected = new ArrayList<>();
        expected.add(new Item("APL", "Apple",        new BigDecimal("0.89")));
        expected.add(new Item("MLK", "Milk (1 gal)", new BigDecimal("3.49")));
        expected.add(new Item("BRD", "Bread",        new BigDecimal("2.79")));
        expected.add(new Item("EGG", "Eggs (dozen)", new BigDecimal("2.99")));
        expected.add(TEST_ITEM);
        assertEquals(expected, catalog.getCatalogItems());
    }

    @Test
    public void canAddItemsToCatalog(){
        Item testItem = new Item("TMP", "Test Item", new BigDecimal("9.99"));
        catalog.addItemToCatalog(testItem);
        assertTrue(catalog.getCatalogItems().contains(testItem));
    }

    @Test
    public void canRemoveItemByReference(){
        catalog.removeItemFromCatalog(TEST_ITEM);
        assertFalse(catalog.getCatalogItems().contains(TEST_ITEM));
    }

    @Test
    public void canRemoveItemByValue(){
        Item duplicateItem = new Item("CHP", "Chips", new BigDecimal("1.99"));
        catalog.removeItemFromCatalog(duplicateItem);
        assertFalse(catalog.getCatalogItems().contains(TEST_ITEM));
    }

    @Test
    public void canGetItemByName(){
        Item actual = catalog.findItemByName("Chips");
        assertEquals(TEST_ITEM, actual);
    }

    @Test
    public void canGetItemBySKU(){
        Item actual = catalog.findItemBySKU("CHP");
        assertEquals(TEST_ITEM, actual);
    }

}
