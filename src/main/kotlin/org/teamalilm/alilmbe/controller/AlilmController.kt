package org.teamalilm.alilmbe.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.teamalilm.alilmbe.domain.member.entity.Member
import org.teamalilm.alilmbe.domain.product.entity.Product.ProductInfo.Store
import org.teamalilm.alilmbe.service.alilm.AlilmService
import org.teamalilm.alilmbe.service.alilm.AlilmService.AlilmRegistrationCommand

@RestController
@RequestMapping("/api/v1/alilms")
@Tag(name = "alilms", description = "재 입고 알림 API")
class AlilmController(
    private val alilmService: AlilmService
) {

    @Operation(
        summary = "재 입고 알림을 등록하는 API",
        description = """
            재 입고 알림을 등록하는 API 이며, 이미 등록된 상품을 등록 시
            상품은 등록되지 않고 사용자의 장바구니만 추가해요.
        """
    )
    @PostMapping
    fun registration(
        @RequestBody @Valid alilmRegistrationRequest: AlilmRegistrationRequest,
        @AuthenticationPrincipal member: Member
    ): ResponseEntity<Unit> {

        alilmService.registration(
            AlilmRegistrationCommand(
                number = alilmRegistrationRequest.number,
                name = alilmRegistrationRequest.name,
                store = alilmRegistrationRequest.store,
                imageUrl = alilmRegistrationRequest.imageUrl,
                option1 = alilmRegistrationRequest.option1,
                option2 = alilmRegistrationRequest.option2,
                option3 = alilmRegistrationRequest.option3,
                member,
            )
        )

        return ResponseEntity.ok().build()
    }

    @Schema(description = "Alilm 등록을 위한 요청 DTO")
    data class AlilmRegistrationRequest(
        @NotBlank(message = "상품 번호는 필수에요.")
        @Schema(description = "각 스토어 별 상품 번호", example = "3859221")
        val number: Int,

        @NotBlank(message = "상품 명은 필수에요.")
        @Schema(description = "이름", example = "COOL 롱 슬리브 셔츠 STYLE 3 TIPE")
        val name: String,

        @NotBlank(message = "구매하는 스토어는 필수에요.")
        @Schema(description = "구매하는 스토어", example = "MUSINSA")
        val store: Store,

        @NotBlank(message = "이미지 링크는 필수에요.")
        @Schema(
            description = "main 이미지 링크",
            example = "https://image.msscdn.net/images/goods_img/20240208/3859221/3859221_17084100068855_500.jpg"
        )
        val imageUrl: String,

        @NotBlank(message = "상품 옵션 1은 필수에요.")
        @Schema(description = "구매 옵션 1", example = "(헤링본)화이트")
        val option1: String,

        @Schema(description = "구매 옵션 2", example = "M")
        val option2: String?,

        @Schema(description = "구매 옵션 3", example = "해당 상품은 없습니다.")
        val option3: String?
    )
}
