package org.teamalilm.alilm.adapter.out.persistence.mapper

import org.springframework.stereotype.Component
import org.teamalilm.alilm.adapter.out.persistence.entity.ProductJpaEntity
import org.teamalilm.alilm.domain.Product

@Component
class ProductMapper {

    fun mapToJpaEntity(product: Product): ProductJpaEntity {
        return ProductJpaEntity(
            id = product.id?.value,
            number = product.number,
            name = product.name,
            brand = product.brand,
            imageUrl = product.imageUrl,
            store = product.store,
            category = product.category,
            price = product.price,
            waitingCount = product.waitingCount,
            option1 = product.option1,
            option2 = product.option2,
            option3 = product.option3
        )
    }

    fun mapToDomainEntityOrNull(productJpaEntity: ProductJpaEntity?): Product? {
        productJpaEntity ?: return null

        return Product(
            id = Product.ProductId(productJpaEntity.id!!),
            number = productJpaEntity.number,
            name = productJpaEntity.name,
            brand = productJpaEntity.brand,
            imageUrl = productJpaEntity.imageUrl,
            store = productJpaEntity.store,
            category = productJpaEntity.category,
            price = productJpaEntity.price,
            waitingCount = productJpaEntity.waitingCount,
            option1 = productJpaEntity.option1,
            option2 = productJpaEntity.option2,
            option3 = productJpaEntity.option3
        )
    }

    fun mapToDomainEntity(productJpaEntity: ProductJpaEntity): Product {
        return Product(
            id = Product.ProductId(productJpaEntity.id!!),
            number = productJpaEntity.number,
            name = productJpaEntity.name,
            brand = productJpaEntity.brand,
            imageUrl = productJpaEntity.imageUrl,
            store = productJpaEntity.store,
            category = productJpaEntity.category,
            price = productJpaEntity.price,
            waitingCount = productJpaEntity.waitingCount,
            option1 = productJpaEntity.option1,
            option2 = productJpaEntity.option2,
            option3 = productJpaEntity.option3
        )
    }

}