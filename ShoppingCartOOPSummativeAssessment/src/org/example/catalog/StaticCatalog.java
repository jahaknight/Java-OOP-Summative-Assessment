package org.example.catalog;

import org.example.model.Item;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * in memory catalog
 * Used Map<SKU, Item> so we can look up SKU quickly
 * Keeps insertion order (LinkedHashMap)
 * Provides basic add/remove/find operations
 */
public class StaticCatalog implements Catalog{

    private final Map<String, Item> itemsBySku = new LinkedHashMap<>();

    public StaticCatalog() {
        // Sample Items to use
        addItemToCatalog(new Item("APL", "Apple",        new BigDecimal("0.89")));
        addItemToCatalog(new Item("MLK", "Milk (1 gal)", new BigDecimal("3.49")));
        addItemToCatalog(new Item("BRD", "Bread",        new BigDecimal("2.79")));
        addItemToCatalog(new Item("EGG", "Eggs (dozen)", new BigDecimal("2.99")));
        addItemToCatalog(new Item("CHP", "Chips",        new BigDecimal("1.99")));
    }

    /**
     * @return read-only snapshot of catalog items in insertion order
     */
    @Override
    public List<Item> getCatalogItems() {
        return List.copyOf(itemsBySku.values());
    }

    /**
     * Adds an item to the catalog of items which the user can purchase
     *
     * @param item the item to add
     * @throws IllegalArgumentException if {@code item} is null or {@code SKU} already exists
     */
    @Override
    public void addItemToCatalog(Item item) {
        if (item == null) throw new IllegalArgumentException("Item required, was given null");
        String key = normalizeSku(item.getSku());
        if (itemsBySku.containsKey(key)) {
            throw new IllegalArgumentException("SKU already exists in catalog: " + key);
        }
        itemsBySku.put(key, item);
    }

    /**
     * Removes the given item from the catalog
     *
     * @param item Item to remove
     * @throws IllegalArgumentException if {@code item} is null
     */
    @Override
    public void removeItemFromCatalog(Item item) {
        if (item == null) throw new IllegalArgumentException("item required");
        itemsBySku.remove(normalizeSku(item.getSku()));
    }

    /**
     * Finds an item from the catalog by name
     *
     * @param itemName name of the item to find
     * @return first item with the name {@code itemName} or null if it doesn't exist
     */
    @Override
    public Item findItemByName(String itemName) {
        if (itemName == null || itemName.isBlank()) return null;
        for(Item i : itemsBySku.values()) {
            if (i.getName().equalsIgnoreCase(itemName.trim())) return i;
        }
        return null;
    }

    /**
     * Finds an item from the catalog by SKU id
     *
     * @param sku Item sku id to find
     * @return Item with sku value {@code sku} or null if it doesn't exist
     */
    @Override
    public Item findItemBySKU(String sku) {
        if (sku == null || sku.isBlank()) return null;
        return itemsBySku.get(normalizeSku(sku));
    }

    /**
     * Normalizes a sku value to remove leading/trailing whitespace and sets all
     * character to uppercase
     *
     * @param sku String to normalize
     * @return A normalized string
     */
    private String normalizeSku(String sku) {
        return sku == null ? null : sku.trim().toUpperCase();
    }
}
