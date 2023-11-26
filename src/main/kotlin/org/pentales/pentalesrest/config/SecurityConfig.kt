package org.pentales.pentalesrest.config

import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.security.authentication.*
import org.springframework.security.config.*
import org.springframework.security.config.annotation.authentication.configuration.*
import org.springframework.security.config.annotation.method.configuration.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.config.http.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.crypto.password.*
import org.springframework.security.web.*
import org.springframework.web.cors.*
import org.springframework.web.filter.*

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val securityConfigProperties: SecurityConfigProperties,
    private val userService: IUserService,
    private val jwtService: JwtService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.cors(Customizer.withDefaults()).csrf { it.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilter(JWTAuthenticationFilter(authenticationManager(), securityConfigProperties, jwtService))
            .addFilter(
                JWTAuthorizationFilter(
                    authenticationManager(), securityConfigProperties, userService, jwtService
                )
            ).authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    HttpMethod.POST,
                    securityConfigProperties.loginUrl,
                    securityConfigProperties.logoutUrl,
                    securityConfigProperties.registerUrl,
                ).permitAll().requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll().anyRequest()
                    .authenticated()
            }
//                .oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
            .exceptionHandling(Customizer.withDefaults()).build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
//        config.setAllowCredentials(true);
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.addExposedHeader(securityConfigProperties.jwt.header)
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}