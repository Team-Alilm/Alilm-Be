package org.teamalilm.alilm.global.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.teamalilm.alilm.global.security.ExcludedUrls
import org.teamalilm.alilm.application.service.security.CustomUserDetailsService

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: CustomUserDetailsService,
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(JwtFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        log.info(" request.requestURI : ${request.requestURI}")

        val shouldFilter =
            ExcludedUrls.entries.toTypedArray()
                .none { (it.path) == request.requestURI }

        if (shouldFilter) {
            val parserToken = request.getHeader("Authorization")?.replace("Bearer ", "") ?: ""
            log.info("token : $parserToken")

            if (jwtUtil.validate(parserToken)) {
                val memberId = jwtUtil.getMemberId(parserToken)

                val userDetails = userDetailsService.loadUserByUsername(memberId.toString())

                val authToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                log.info("authToken : $authToken")

                SecurityContextHolder.getContext().authentication = authToken

                log.info("SecurityContextHolder.getContext().authentication : ${SecurityContextHolder.getContext().authentication}")
            } else {
                logger.info("JWT claims is empty, 잘못된 JWT 토큰 입니다. token : $parserToken")
            }
        }

        filterChain.doFilter(request, response)
    }

}