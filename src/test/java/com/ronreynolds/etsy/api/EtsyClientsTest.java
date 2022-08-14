package com.ronreynolds.etsy.api;


import com.ronreynolds.etsy.api.model.Pong;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * env(ETSY_API_KEY) must be set to a non-empty (and preferably valid) value
 */
class EtsyClientsTest {
    private static final String shopName = "onehomecrafts";
    private static final int shopId = 18475126;

    @Test
    void nonNullClients() {
        assertThat(EtsyClients.getDefaultClients()).isNotNull();
    }

    @Test
    void ping() {
        EtsyClients clients = EtsyClients.getDefaultClients();
        assertThat(clients.otherApi).isNotNull();
        Pong pong = assertDoesNotThrow(clients.otherApi::ping);
        assertThat(pong).isNotNull();
        assertThat(pong.getApplicationId()).isNotNull();
        System.out.println(pong);
    }

    @Test
    void findShopsByName() {
        EtsyClients clients = EtsyClients.getDefaultClients();
        assertDoesNotThrow(() -> clients.findShops(shopName, System.out::println));
    }

    @Test
    void findShopListings() {
        EtsyClients clients = EtsyClients.getDefaultClients();
        assertDoesNotThrow(() -> clients.getActiveListingsForShopId(shopId, System.out::println));
    }
}