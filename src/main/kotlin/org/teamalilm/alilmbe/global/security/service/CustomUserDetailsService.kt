package org.teamalilm.alilmbe.global.security.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.teamalilm.alilmbe.domain.member.error.NotFoundMemberException
import org.teamalilm.alilmbe.domain.member.repository.MemberRepository
import org.teamalilm.alilmbe.global.security.entity.CustomUserDetails

@Component
@Transactional(readOnly = true)
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(memberId: String): UserDetails? {
        return null
    }

    fun loadUserByMemberId(memberId: Long): UserDetails {
        val member = memberRepository.findByIdOrNull(memberId.toLong())
            ?: (throw NotFoundMemberException(""))

        return CustomUserDetails(member)
    }
}