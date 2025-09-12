package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemTest {

    private Item item;
    final private String SKU = "abc123";
    final private String ITEM_NAME = "Red Bull";
    final private BigDecimal PRICE = new BigDecimal("3.99");

    @BeforeEach
    public void setup(){
        item = new Item(SKU, ITEM_NAME, PRICE);
    }

    @Test
    public void getSkuValue(){
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

}
