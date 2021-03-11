package com.example.infrastructure.repositories

import com.example.domain.model.ProductItem
import com.example.domain.model.ProductSearchItem
import com.example.domain.model.ResultWrapper
import com.example.domain.model.Site
import com.example.domain.repositories.ProductRepository
import com.example.infrastructure.clients.ProductsClient
import com.example.infrastructure.entities.toProductItem
import com.example.infrastructure.entities.toProductSearchItem
import com.example.infrastructure.entities.toSite
import com.example.infrastructure.network.ResponseHandler

class ProductRepositoryImpl(
        private val client: ProductsClient,
        private val responseHandler: ResponseHandler
): ProductRepository {

    override suspend fun getProductItem(productId: String): ResultWrapper<ProductItem> {
        return responseHandler {
            client.getProductItem(productId).toProductItem()
        }
    }

    override suspend fun getProductDescription(productId: String): ResultWrapper<String> {
        return responseHandler {
            client.getProductDescription(productId).first().text
        }
    }

    override suspend fun getProductSearch(query: String, site: String): ResultWrapper<List<ProductSearchItem>> {
        return responseHandler {
            client.getProductSearch(site, query).results.map { it.toProductSearchItem() }
        }
    }

    override suspend fun getSites(): ResultWrapper<List<Site>> {
        return responseHandler {
            client.getSites().map { it.toSite() }
        }
    }
}