package org.pentales.pentalesrest.config

import de.codecentric.boot.admin.server.config.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.context.annotation.*
import org.springframework.security.authentication.*
import org.springframework.security.config.annotation.authentication.configuration.*
import org.springframework.security.config.annotation.method.configuration.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.crypto.password.*
import org.springframework.security.web.*
import org.springframework.security.web.authentication.*
import org.springframework.web.cors.*
import org.springframework.web.filter.*

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val securityConfigProperties: SecurityConfigProperties,
    private val userService: IUserServices,
    private val jwtService: JwtService,
    private val adminServerProperties: AdminServerProperties,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val successHandler = SavedRequestAwareAuthenticationSuccessHandler()
        successHandler.setTargetUrlParameter("redirectTo")
        successHandler.setDefaultTargetUrl(this.adminServerProperties.contextPath + "/")
        val TOKEN_VALIDITY = 1209600 // 2 weeks
        http.cors(org.springframework.security.config.Customizer.withDefaults()).csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll()
            }

        return http.build()
//        return http.cors(Customizer.withDefaults()).csrf { it.disable() }
//            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            .authorizeHttpRequests { auth ->
//                auth.requestMatchers(
//                    adminServerProperties.contextPath + "/login",
//                    adminServerProperties.contextPath + "/assets/**",
//                ).permitAll()
//            }.addFilter(JWTAuthenticationFilter(authenticationManager(), securityConfigProperties, jwtService))
//            .addFilterAfter(
//                JWTAuthorizationFilter(
//                    authenticationManager(), securityConfigProperties, userService, jwtService
//                ), JWTAuthenticationFilter::class.java
//            ).authorizeHttpRequests { auth ->
//                auth.requestMatchers(
//                    HttpMethod.POST,
//                    securityConfigProperties.loginUrl,
//                    securityConfigProperties.logoutUrl,
//                    securityConfigProperties.registerUrl,
//                    securityConfigProperties.usernameAvailableUrl,
//                ).permitAll()
//
//                    .requestMatchers("/test/unsecured").permitAll()
//
//                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
//
//                    .anyRequest().authenticated()
//            }.exceptionHandling {
////                it.authenticationEntryPoint(JwtAuthenticationEntryPoint())
//            }.formLogin {
//                it.loginPage(adminServerProperties.contextPath + "/login").successHandler(successHandler)
//            }.logout { it.logoutUrl(adminServerProperties.contextPath + "/logout") }
//            .httpBasic(Customizer.withDefaults()).rememberMe {
//                it.key(UUID.randomUUID().toString()).tokenValiditySeconds(TOKEN_VALIDITY)
//            }.build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
//        config.setAllowCredentials(true);
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedHeader(securityConfigProperties.jwt.header)
        config.addAllowedMethod("*")
        config.maxAge = 3600L
        config.exposedHeaders = listOf(
            "x-xsrf-token",
            "Access-Control-Allow-Headers",
            "Origin",
            "Accept",
            "X-Requested-With",
            "Content-Type",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        )
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