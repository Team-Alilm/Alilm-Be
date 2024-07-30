package org.teamalilm.alilmbe.adapter.out.persistence.repository.product

import org.springframework.data.jpa.repository.JpaRepository
import org.teamalilm.alilmbe.adapter.out.persistence.entity.ProductJpaEntity

interface SpringDataProductRepository : JpaRepository<ProductJpaEntity,Long>