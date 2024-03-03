package org.pentales.pentalesrest.config

import org.pentales.pentalesrest.components.configProperties.*
import org.pentales.pentalesrest.config.oauth2.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.*
import org.slf4j.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.security.access.expression.method.*
import org.springframework.security.access.hierarchicalroles.*
import org.springframework.security.authentication.*
import org.springframework.security.config.*
import org.springframework.security.config.annotation.authentication.configuration.*
import org.springframework.security.config.annotation.method.configuration.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.config.http.*
import org.springframework.security.web.*
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
    private val oauthUserService: CustomOAuth2UserService,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
) {

    companion object {

        val LOG = LoggerFactory.getLogger(SecurityConfig::class.java)
    }

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val hierarchy = RoleHierarchyImpl()
        hierarchy.setHierarchy(
            """
            ROLE_SUPER_ADMIN > ROLE_ADMIN
            ROLE_ADMIN > ROLE_MODERATOR
            ROLE_MODERATOR > ROLE_USER
            ROLE_USER > ROLE_GUEST
            """.trimIndent()
        )
        return hierarchy
    }

    @Bean
    fun methodSecurityExpressionHandler(
        roleHierarchy: RoleHierarchy?,
        permissionEvaluator: CustomPermissionEvaluator
    ): MethodSecurityExpressionHandler? {
        val handler = DefaultMethodSecurityExpressionHandler()
        handler.setRoleHierarchy(roleHierarchy)
        handler.setPermissionEvaluator(permissionEvaluator)
        return handler
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors(Customizer.withDefaults())
//            .requiresChannel { it.anyRequest().requiresSecure() }
            .csrf { it.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilter(JWTAuthenticationFilter(authenticationManager(), securityConfigProperties, jwtService))
            .addFilterAfter(
                JWTAuthorizationFilter(
                    securityConfigProperties, userService, jwtService
                ), JWTAuthenticationFilter::class.java
            ).authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        HttpMethod.POST,
                        securityConfigProperties.loginUrl,
                        securityConfigProperties.logoutUrl,
                        securityConfigProperties.registerUrl,
                        securityConfigProperties.usernameAvailableUrl,
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/assets/**").permitAll()
                    .requestMatchers("/oauth2/**", "/auth/**", "/oauth/**").permitAll()
                    .requestMatchers("/test/unsecured").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    .anyRequest()
                    .authenticated()
            }.oauth2Login { oauth2 ->
                oauth2
                    .authorizationEndpoint {
                        it.baseUri("/oauth2/authorize")
                            .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                    }
                    .redirectionEndpoint {
                        it.baseUri("/oauth2/callback/*")
                    }
                    .userInfoEndpoint {
                        it.userService(oauthUserService)
                    }
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler { request, response, exception ->
                        LOG.error("Failure handler")
                        println(exception)
                    }
            }.build()
    }

    @Bean
    fun corsFilter(corsConfigProperties: CorsConfigProperties): CorsFilter {
        LOG.info("CORS Configuration: $corsConfigProperties")
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = corsConfigProperties.allowedOrigins
        config.allowedMethods = corsConfigProperties.allowedMethods
        config.allowedHeaders = corsConfigProperties.allowedHeaders
        config.addAllowedHeader(securityConfigProperties.jwt.header)
        config.allowCredentials = corsConfigProperties.allowCredentials
        config.maxAge = corsConfigProperties.maxAge
        config.exposedHeaders = listOf(
            *corsConfigProperties.exposedHeaders.toTypedArray(),
            securityConfigProperties.jwt.header
        )
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    fun cookieAuthorizationRequestRepository(): HttpCookieOAuth2AuthorizationRequestRepository {
        return HttpCookieOAuth2AuthorizationRequestRepository()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

}