package com.ronreynolds.etsy.api;

/**
 * based on
 * {@link com.ronreynolds.etsy.api.model.ListingInventoryWithAssociationsListing.StateEnum}
 * {@link com.ronreynolds.etsy.api.model.ShopListing.StateEnum}
 * {@link com.ronreynolds.etsy.api.model.ShopListingsWithAssociationsResultsInner.StateEnum}
 * {@link com.ronreynolds.etsy.api.model.ShopListingsResultsInner.StateEnum}
 * {@link com.ronreynolds.etsy.api.model.ShopListingWithAssociations.StateEnum}
 */
public enum ListingState {
    ACTIVE("active"),
    INACTIVE("inactive"),
    SOLD_OUT("sold_out"),
    DRAFT("draft"),
    EXPIRED("expired");
    public final String value;

    ListingState(String value) {
        this.value = value;
    }
}
