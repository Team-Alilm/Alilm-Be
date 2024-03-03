package org.teamalilm.alilmbe.global.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.teamalilm.alilmbe.global.handler.CustomSuccessHandler
import org.teamalilm.alilmbe.global.service.CustomOAuth2UserService

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val customSuccessHandler: CustomSuccessHandler
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun configureH2ConsoleEnable() : WebSecurityCustomizer {
        return WebSecurityCustomizer { web -> web
            .ignoring()
            .requestMatchers(PathRequest.toH2Console())
        }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .formLogin {
                it.disable()
            }

            .httpBasic {
                it.disable()
            }

            .csrf {
                it.disable()
            }

            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            .oauth2Login{ oauth2LoginCustomizer -> oauth2LoginCustomizer
                .userInfoEndpoint { userInfoEndpointCustomizer ->
                    userInfoEndpointCustomizer.userService(customOAuth2UserService)
                }

                .successHandler(customSuccessHandler)
            }

            .authorizeHttpRequests { auth -> auth
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
            }

        return http.build()
    }

}