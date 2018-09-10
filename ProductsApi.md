# ProductsApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**productsGet**](ProductsApi.md#productsGet) | **GET** /products | Get all the products in the system.
[**productsPost**](ProductsApi.md#productsPost) | **POST** /products | Add a new product
[**productsProductIdDelete**](ProductsApi.md#productsProductIdDelete) | **DELETE** /products/{productId} | Deletes an existing product
[**productsProductIdGet**](ProductsApi.md#productsProductIdGet) | **GET** /products/{productId} | Find product by ID
[**productsProductIdPatch**](ProductsApi.md#productsProductIdPatch) | **PATCH** /products/{productId} | Updates a product with form data


<a name="productsGet"></a>
# **productsGet**
> List&lt;Product&gt; productsGet()

Get all the products in the system.

Get a list of all products available in the system.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProductsApi;


ProductsApi apiInstance = new ProductsApi();
try {
    List<Product> result = apiInstance.productsGet();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProductsApi#productsGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Product&gt;**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="productsPost"></a>
# **productsPost**
> Product productsPost(body)

Add a new product

This service is used to add a new product in the system.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProductsApi;


ProductsApi apiInstance = new ProductsApi();
Product body = new Product(); // Product | Product object representation in JSON that needs to be added to the system
try {
    Product result = apiInstance.productsPost(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProductsApi#productsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Product**](Product.md)| Product object representation in JSON that needs to be added to the system |

### Return type

[**Product**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="productsProductIdDelete"></a>
# **productsProductIdDelete**
> Product productsProductIdDelete(productId)

Deletes an existing product

Deletes an existing product

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProductsApi;


ProductsApi apiInstance = new ProductsApi();
Long productId = 789L; // Long | ID of a product
try {
    Product result = apiInstance.productsProductIdDelete(productId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProductsApi#productsProductIdDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productId** | **Long**| ID of a product |

### Return type

[**Product**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="productsProductIdGet"></a>
# **productsProductIdGet**
> Product productsProductIdGet(productId)

Find product by ID

Returns a single product based on its ID

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProductsApi;


ProductsApi apiInstance = new ProductsApi();
Long productId = 789L; // Long | ID of a product
try {
    Product result = apiInstance.productsProductIdGet(productId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProductsApi#productsProductIdGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productId** | **Long**| ID of a product |

### Return type

[**Product**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="productsProductIdPatch"></a>
# **productsProductIdPatch**
> Product productsProductIdPatch(productId)

Updates a product with form data

Updates an existing product in the system

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProductsApi;


ProductsApi apiInstance = new ProductsApi();
Long productId = 789L; // Long | ID of a product
try {
    Product result = apiInstance.productsProductIdPatch(productId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProductsApi#productsProductIdPatch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productId** | **Long**| ID of a product |

### Return type

[**Product**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

