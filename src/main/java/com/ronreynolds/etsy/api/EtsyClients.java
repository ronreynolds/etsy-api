package com.ronreynolds.etsy.api;

import com.ronreynolds.etsy.ApiClient;
import com.ronreynolds.etsy.ApiException;
import com.ronreynolds.etsy.api.model.ShopListingsWithAssociations;
import com.ronreynolds.etsy.api.model.ShopListingsWithAssociationsResultsInner;
import com.ronreynolds.etsy.api.model.Shops;
import com.ronreynolds.etsy.api.model.ShopsResultsInner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * a collection of util methods found useful when working with the Etsy API.
 * <p>
 * by default pulls the x-api-key header value from the environment("ETSY_API_KEY")
 */
public class EtsyClients {
    private static final Logger log = LoggerFactory.getLogger(EtsyClients.class);
    // check system-props and environment for ETSY_CLIENT_DEBUG flag
    private static final boolean DEBUG_ENABLED = Boolean.getBoolean("ETSY_CLIENT_DEBUG") ||
            Boolean.parseBoolean(System.getenv("ETSY_CLIENT_DEBUG"));
    private static final ApiClient defaultApiClient = addEtsyApiKey(new ApiClient());
    private static final EtsyClients defaultClients = new EtsyClients(defaultApiClient);

    /**
     * return the default EtsyClients
     */
    public static EtsyClients getDefaultClients() {
        return defaultClients;
    }

    public final ApiClient apiClient;
    public final BuyerTaxonomyApi buyerTaxonomyApi;
    public final LedgerEntryApi ledgerEntryApi;
    public final OtherApi otherApi;
    public final PaymentApi paymentApi;
    public final ReviewApi reviewApi;
    public final SellerTaxonomyApi sellerTaxonomyApi;
    public final ShopApi shopApi;
    public final ShopListingApi shopListingApi;
    public final ShopListingFileApi shopListingFileApi;
    public final ShopListingImageApi shopListingImageApi;
    public final ShopListingInventoryApi shopListingInventoryApi;
    public final ShopListingOfferingApi shopListingOfferingApi;
    public final ShopListingProductApi shopListingProductApi;
    public final ShopListingTranslationApi shopListingTranslationApi;
    public final ShopListingVariationImageApi shopListingVariationImageApi;
    public final ShopProductionPartnerApi shopProductionPartnerApi;
    public final ShopReceiptApi shopReceiptApi;
    public final ShopReceiptTransactionsApi shopReceiptTransactionsApi;
    public final ShopSectionApi shopSectionApi;
    public final ShopShippingProfileApi shopShippingProfileApi;
    public final UserAddressApi userAddressApi;
    public final UserApi userApi;

    public EtsyClients(@Nonnull ApiClient client) {
        this.apiClient = client;
        this.buyerTaxonomyApi = new BuyerTaxonomyApi(apiClient);
        this.ledgerEntryApi = new LedgerEntryApi(apiClient);
        this.otherApi = new OtherApi(apiClient);
        this.paymentApi = new PaymentApi(apiClient);
        this.reviewApi = new ReviewApi(apiClient);
        this.sellerTaxonomyApi = new SellerTaxonomyApi(apiClient);
        this.shopApi = new ShopApi(apiClient);
        this.shopListingApi = new ShopListingApi(apiClient);
        this.shopListingFileApi = new ShopListingFileApi(apiClient);
        this.shopListingImageApi = new ShopListingImageApi(apiClient);
        this.shopListingInventoryApi = new ShopListingInventoryApi(apiClient);
        this.shopListingOfferingApi = new ShopListingOfferingApi(apiClient);
        this.shopListingProductApi = new ShopListingProductApi(apiClient);
        this.shopListingTranslationApi = new ShopListingTranslationApi(apiClient);
        this.shopListingVariationImageApi = new ShopListingVariationImageApi(apiClient);
        this.shopProductionPartnerApi = new ShopProductionPartnerApi(apiClient);
        this.shopReceiptApi = new ShopReceiptApi(apiClient);
        this.shopReceiptTransactionsApi = new ShopReceiptTransactionsApi(apiClient);
        this.shopSectionApi = new ShopSectionApi(apiClient);
        this.shopShippingProfileApi = new ShopShippingProfileApi(apiClient);
        this.userAddressApi = new UserAddressApi(apiClient);
        this.userApi = new UserApi(apiClient);
    }

    /**
     * return the ETSY_API_KEY environment variable value or null if not found
     */
    public static String getDefaultEtsyApiKey() {
        return System.getenv("ETSY_API_KEY");
    }

    /**
     * add the default ETSY_API_KEY env-var to the provided {@code ApiClient}
     *
     * @param client client to add the default ETSY_API_KEY to
     * @return client passed in (tho that behavior may not be not guaranteed so best to use what's returned)
     */
    public static ApiClient addEtsyApiKey(ApiClient client) {
        return addEtsyApiKey(client, getDefaultEtsyApiKey());
    }

    /**
     * add the provided apiKey to the provided {@code ApiClient}
     *
     * @param client client to add the default ETSY_API_KEY to
     * @param apiKey the Etsy API key value to use for all requests sent thru {@code ApiClient}
     * @return client passed in (tho that behavior may not be not guaranteed so best to use what's returned)
     */
    public static ApiClient addEtsyApiKey(@Nonnull ApiClient client, String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            log.error("unable to find ETSY_API_KEY; clients will not be properly configured");
        }
        if (DEBUG_ENABLED) {
            log.info("debug flag set");
            client = client.setResponseInterceptor(res -> log.info("response = {}", res));
        }
        return client.setRequestInterceptor(req -> {
            req.header("x-api-key", apiKey);
            if (DEBUG_ENABLED) {
                log.info("request = {}", req);
            }
        });
    }

    public void findShops(String shopName, Consumer<ShopsResultsInner> shopConsumer) throws ApiException {
        int lastFetchOffset = 0;
        Integer batchSize = 25; // default
        while (true) {
            Shops shops = shopApi.findShops(shopName, batchSize, lastFetchOffset);
            if (shops == null) {
                break;
            }
            Objects.requireNonNull(shops.getResults(), "shops.results was null").forEach(shopConsumer);
            int resultCount = Objects.requireNonNull(shops.getCount(), "shops.count was null");
            if (resultCount < batchSize) {
                log.info("batch of {} is less than allowed size {}; assuming done", resultCount, batchSize);
                break;
            }
            lastFetchOffset += resultCount;
            log.info("updated offset to {} to fetch next batch", lastFetchOffset);
        }
    }

    public void getActiveListingsForShopId(int shopId, Consumer<ShopListingsWithAssociationsResultsInner> listingConsumer)
            throws ApiException {
        getListingsForShopId(shopId, ListingState.ACTIVE, listingConsumer);
    }

    public void getListingsForShopId(int shopId, ListingState listingState,
                                     Consumer<ShopListingsWithAssociationsResultsInner> listingConsumer) throws ApiException {
        int lastFetchOffset = 0;
        Integer batchSize = 25; // default
        String sortField = null;
        String sortOrder = SortOrder.DESCENDING.value;
        List<String> includeList = null;

        /*
         * @param sortOn The value to sort a search result of listings on.
         * NOTE: sort_on only works when combined with one of the search options (keywords, region, etc.).
         * (optional, default to created)
         * @param sortOrder The ascending(up) or descending(down) order to sort listings by.
         * NOTE: sort_order only works when combined with one of the search options (keywords, region, etc.).
         * (optional, default to desc)
         * @param includes An enumerated string that attaches a valid association.
         * Acceptable inputs are &#39;Shipping&#39;, &#39;Shop&#39;, &#39;Images&#39;, &#39;User&#39;,
         * &#39;Translations&#39; and &#39;Inventory&#39;. Default value is an empty array. (optional
         * @return
         */
        while (true) {
            ShopListingsWithAssociations listings = shopListingApi.getListingsByShop(shopId,
                    listingState != null ? listingState.value : null, batchSize, lastFetchOffset,
                    sortField, sortOrder, includeList);
            if (listings == null) {
                break;
            }
            Objects.requireNonNull(listings.getResults(), "listings.results was null").forEach(listingConsumer);
            int numListings = Objects.requireNonNull(listings.getCount(), "listings.count was null");
            if (numListings < batchSize) {
                log.info("num listings returned ({}) is < batch-size ({}); assuming we're done", numListings, batchSize);
                break;
            }
            lastFetchOffset += numListings;
        }

    }

}
