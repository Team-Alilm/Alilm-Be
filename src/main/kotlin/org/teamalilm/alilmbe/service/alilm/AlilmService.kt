package org.teamalilm.alilmbe.service.alilm

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamalilm.alilmbe.domain.basket.entity.Basket
import org.teamalilm.alilmbe.domain.member.entity.Member
import org.teamalilm.alilmbe.domain.product.entity.Product
import org.teamalilm.alilmbe.domain.product.entity.Product.ProductInfo
import org.teamalilm.alilmbe.domain.product.entity.Product.ProductInfo.Store
import org.teamalilm.alilmbe.domain.product.repository.ProductRepository
import org.teamalilm.alilmbe.global.slack.service.SlackService

@Service
@Transactional(readOnly = true)
class AlilmService(
    private val productRepository: ProductRepository,
    private val slackService: SlackService,
    private val basketRepository: org.teamalilm.alilmbe.domain.basket.repository.BasketRepository
) {

    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun registration(
        command: AlilmRegistrationCommand
    ) {
        val productInfo = ProductInfo(
            store = command.store,
            number = command.number,
            option1 = command.option1,
            option2 = command.option2,
            option3 = command.option3
        )

        val product = productRepository.findByProductInfo(productInfo)
            ?: productRepository.save(
                Product(
                    name = command.name,
                    brand = command.brand,
                    imageUrl = command.imageUrl,
                    category = command.category,
                    price = command.price,
                    productInfo = productInfo
                )
            )

        log.info("$product")

        if (
            basketRepository.existsByMemberAndProduct(
                member =command.member,
                product = product
            )
        ) {
            log.info("이미 등록된 상품입니다.")
        } else {
            log.info("상품을 등록합니다.")

            basketRepository.save(
                Basket(
                    member = command.member,
                    product = product
                )
            )
        }

        slackService.sendSlackMessage(
            """
                id: ${command.member.id}
                nickname : ${command.member.nickname} 님이 상품을 등록했어요. 
                상품 : $product
                """.trimIndent()
        )

    }

    data class AlilmRegistrationCommand(
        val number: Int,
        val name: String,
        val brand: String,
        val store: Store,
        val imageUrl: String,
        val category: String,
        val price: Int,
        val option1: String,
        val option2: String?,
        val option3: String?,
        val member: Member
    )

}

