package org.example.catalog;

import org.example.model.Item;

import java.util.List;

public interface Catalog {

    public List<Item> getCatalogItems();
    public void addItemToCatalog(Item item);
    public void removeItemFromCatalog(Item item);
    public Item findItemByName(String itemName);
    public Item findItemBySKU(String sku);

}
