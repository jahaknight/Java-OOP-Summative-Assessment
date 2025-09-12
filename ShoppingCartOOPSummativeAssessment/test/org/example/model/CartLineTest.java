package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartLineTest {

    private CartLine cartLine;
    private Item item = new Item("TEST", "Test Item", new BigDecimal("2.37"));
    private int quantity = 5;


    @BeforeEach
    public void setup(){
        cartLine = new CartLine(item, quantity);
    }

    @Test
    public void errorThrownIfAddingNegativeNumber(){
        assertThrows(Exception.class, () -> cartLine.add(-4));
    }

    @Test
    public void correctlyIncrementsQuantityByGivenValue(){
        cartLine.add(3);
        assertEquals(8, cartLine.getQuantity());
    }

    @Test
    public void errorThrownIfRemovingNegativeNumber(){
        assertThrows(Exception.class, () -> cartLine.remove(-3));
    }

    @Test
    public void removesCorrectAmountFromQuantity(){
        cartLine.remove(3);
        assertEquals(2, cartLine.getQuantity());
    }

    @Test
    public void quantityIsSetToZeroIfRemovingMoreThanCurrentQuantity(){
        cartLine.remove(8);
        assertEquals(0, cartLine.getQuantity());
    }

    @Test
    public void getsCorrectPriceTotal(){
        BigDecimal expected = new BigDecimal("11.85");
        assertEquals(expected, cartLine.getLineTotal());
    }

}
