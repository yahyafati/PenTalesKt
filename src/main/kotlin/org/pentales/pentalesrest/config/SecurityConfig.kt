package org.pentales.pentalesrest.config

import org.pentales.pentalesrest.config.properties.*
import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.config.security.oauth2.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.misc.jwt.*
import org.slf4j.*
import org.springframework.beans.factory.annotation.*
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
    private val securityProperties: SecurityProperties,
    private val userService: IUserServices,
    private val jwtService: JwtService,
    private val oauthUserService: CustomOAuth2UserService,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
) {

    companion object {

        private val LOG = LoggerFactory.getLogger(SecurityConfig::class.java)
    }

    @Value("\${server.ssl.enabled}")
    private val requireSecure = false

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
            .requiresChannel {
                if (requireSecure) {
                    it.anyRequest().requiresSecure()
                }
            }
            .csrf { it.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilter(JWTAuthenticationFilter(authenticationManager(), securityProperties, jwtService))
            .addFilterAfter(
                JWTAuthorizationFilter(
                    securityProperties, userService, jwtService
                ), JWTAuthenticationFilter::class.java
            ).authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        HttpMethod.POST,
                        securityProperties.loginUrl,
                        securityProperties.logoutUrl,
                        securityProperties.registerUrl,
                        securityProperties.usernameAvailableUrl,
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/assets/**").permitAll()
                    .requestMatchers("/oauth2/**", "/auth/**", "/oauth/**").permitAll()
                    .requestMatchers("/actuator/**").permitAll()
                    .requestMatchers("/test/unsecured").permitAll()
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui.html", "/swagger-ui/**", "/swagger-ui.html/**",
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .oauth2Login { oauth2 ->
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
                    .failureHandler { _, _, exception ->
                        LOG.error("OAuth2 Login Failure: ${exception.message}")
                    }
            }
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ ->
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase)
                }
            }
            .build()
    }

    @Bean
    fun corsFilter(CORSProperties: CORSProperties): CorsFilter {
        LOG.info("CORS Configuration: $CORSProperties")
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = CORSProperties.allowedOrigins
        config.allowedMethods = CORSProperties.allowedMethods
        config.allowedHeaders = CORSProperties.allowedHeaders
        config.addAllowedHeader(securityProperties.jwt.header)
        config.allowCredentials = CORSProperties.allowCredentials
        config.maxAge = CORSProperties.maxAge
        config.exposedHeaders = listOf(
            *CORSProperties.exposedHeaders.toTypedArray(),
            securityProperties.jwt.header
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