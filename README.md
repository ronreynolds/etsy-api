# etsy-api
using the Etsy API via a client generated from their openapi JSON file

## Etsy API
* https://developers.etsy.com/documentation/reference


### OpenAPI JSON
* download from https://www.etsy.com/openapi/generated/oas/3.0.0.json
* modified several `integer` types with `"format":"int64"` to get the Java generator to use `Long` for numeric types that exceeded Java's `int` limits

### API Key
* get API key issued via steps at https://www.etsy.com/developers/register
* note, in UI API key is called "KEYSTRING"; guessing "SHARED SECRET" is a type of key-ID to use when discussing key issues with Etsy support (https://help.etsy.com/hc/en-us/requests/new)

## This library

### Classes (`com.ronreynolds.etsy.api.`)
* `EtsyClients` - one-stop class for getting API-section-specific client instances
* `ListingField` - TODO (i think it's the fields to return in a `getListingsByShop()` call (and likely others))
* `ListingInclude` - associated objects to return in a `getListingsByShop()` call (and likely others)
* `ListingState` - the state of listings to return in a `getListingsByShop()` call (and likely others)
* `SortOrder` - the order in which the specified column should be sorted in a `getListingsByShop()` call (and likely others)

### Use
* first you need to export the `ETSY_API_KEY` to your environment so it can be injected into the client

### OAuth2
* `getListingsByShop()` (and many others) requires an OAuth token; still working out how to do this
* https://developers.etsy.com/documentation/tutorials/quickstart/#start-with-a-simple-express-server-application
