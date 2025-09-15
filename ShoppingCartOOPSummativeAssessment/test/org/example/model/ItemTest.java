package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    private Item item;
    final private String SKU = "ABC123";
    final private String ITEM_NAME = "Red Bull";
    final private BigDecimal PRICE = new BigDecimal("3.99");

    @BeforeEach
    public void setup(){
        item = new Item(SKU, ITEM_NAME, PRICE);
    }

    @Test
    public void getSkuValue(){
        // assertEquals uses equalsIgnoreCase for strings
        assertEquals(SKU, item.getSku());
    }

    @Test
    public void getNameValue(){
        assertEquals(ITEM_NAME, item.getName());
    }

    @Test
    public void getPriceValue(){
        assertEquals(PRICE, item.getUnitPrice());
    }

    @Test
    public void equalsWorksCorrectWithSkuValuesOnly(){
        Item newItem = new Item(SKU, "Different name", new BigDecimal("9.50"));
        assertEquals(item, newItem);
    }

    @Test
    public void skuIsNormalizedToUpperCase(){
        Item test = new Item("qwerty", "test", new BigDecimal("1.00"));
        assertEquals("QWERTY", test.getSku());
    }

    @Test
    public void skuIsNormalizedWithoutWhitespace(){
        Item test = new Item("   TEST           ", "test", new BigDecimal("1.00"));
        assertEquals("TEST", test.getSku());
    }

    @Test
    public void constructorThrowsErrorWhenPriceIsBlankNullOrNegative(){
        assertThrows(Exception.class, () -> new Item("ABC", "test", new BigDecimal("")));
        assertThrows(Exception.class, () -> new Item("ABC", "test", null));
        assertThrows(Exception.class, () -> new Item("ABC", "test", new BigDecimal("-1.00")));
    }

    @Test
    public void constructorThrowsErrorWhenNameIsEmptyOrNull(){
        assertThrows(Exception.class, () -> new Item("ABC", "", new BigDecimal("1.00")));
        assertThrows(Exception.class, () -> new Item("ABC", null, new BigDecimal("1.00")));
    }

    @Test
    public void constructorThrowsErrorWhenSKUIsEmptyOrNull(){
        assertThrows(Exception.class, () -> new Item("", "test", new BigDecimal("1.00")));
        assertThrows(Exception.class, () -> new Item(null, "test", new BigDecimal("1.00")));
    }

}
