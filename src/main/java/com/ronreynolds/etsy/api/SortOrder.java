package com.ronreynolds.etsy.api;

public enum SortOrder {
    ASCENDING("asc"), // AKA "ascending", "up"
    DESCENDING("desc"); // AKA descending", "down"

    public final String value;

    SortOrder(String value) {
        this.value = value;
    }
}
