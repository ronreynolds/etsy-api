package com.ronreynolds.etsy.api;


import com.ronreynolds.etsy.api.model.Pong;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * env(ETSY_API_KEY) must be set to a non-empty (and preferably valid) value
 */
class EtsyClientsTest {
    @Test
    void ping() {
        EtsyClients clients = EtsyClients.getDefaultClients();
        assertThat(clients).isNotNull();
        assertThat(clients.otherApi).isNotNull();
        Pong pong = assertDoesNotThrow(clients.otherApi::ping);
        assertThat(pong).isNotNull();
        assertThat(pong.getApplicationId()).isNotNull();
        System.out.println(pong);
    }
}