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

### Classes (`com.ronreynolds.etsy-api.`)
* `EtsyApiClients` - one-stop class for getting API-section-specific client instances

### Use
* first you need to export the API_KEY to your environment so it can be injected into the client

