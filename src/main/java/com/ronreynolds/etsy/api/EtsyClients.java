package com.ronreynolds.etsy.api;

import com.ronreynolds.etsy.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

/**
 * a collection of util methods found useful when working with the Etsy API.
 * <p>
 * by default pulls the x-api-key header value from the environment("ETSY_API_KEY")
 */
public class EtsyClients {
    private static final Logger log = LoggerFactory.getLogger(EtsyClients.class);
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
    public final ShopListingApi listingApi;
    public final ShopReceiptApi receiptApi;
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
        this.listingApi = new ShopListingApi(apiClient);
        this.receiptApi = new ShopReceiptApi(apiClient);
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
            log.error("unable to find API_KEY; clients will not be properly configured");
        }
        return client.setRequestInterceptor(req -> req.header("x-api-key", apiKey));
    }
}
