package com.ronreynolds.etsy.api;

/**
 * allowed include values to pass to shopListingApi.getListingsByShop() et al
 */
public enum ListingInclude {
    SHIPPING("Shipping"),
    IMAGES("Images"),
    SHOP("Shop"),
    USER("User"),
    TRANSLATIONS("Translations"),
    INVENTORY("Inventory");
    public final String value;

    ListingInclude(String value) {
        this.value = value;
    }
}
