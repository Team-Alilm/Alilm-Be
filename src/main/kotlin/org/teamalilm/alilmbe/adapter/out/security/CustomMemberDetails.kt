package org.teamalilm.alilmbe.adapter.out.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.teamalilm.alilmbe.domain.member.Member


/**
 * Custom implementation of UserDetails to integrate with Spring Security.
 */
class CustomMemberDetails(
    private val member: Member
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        // If you have roles or permissions, return them here.
        // Returning an empty list for simplicity.
        return member.role.map { SimpleGrantedAuthority(it.key) }
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return member.phoneNumber
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
