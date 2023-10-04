package org.pentales.pentalesrest.config

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import com.auth0.jwt.interfaces.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*
import java.time.*
import java.util.*

@Service
class JwtService(securityConfigProperties: SecurityConfigProperties) {

    private val jwtProperties: SecurityConfigProperties.JwtProperties = securityConfigProperties.jwt
    private fun decodedJWT(token: String): DecodedJWT {
        return JWT.require(getSignInKey()).build().verify(token)
    }

    private fun extractAllClaims(token: String): Map<String, Claim> {
        return decodedJWT(token).claims
    }

    fun extractClaim(token: String, name: String): Claim? {
        return decodedJWT(token).getClaim(name)
    }

    fun generateToken(
        userDetails: UserDetails, extraClaims: Map<String, Any> = emptyMap(),
    ): String {
        return buildToken(userDetails, extraClaims)
    }

    private fun buildToken(
        userDetails: UserDetails, extraClaims: Map<String, Any>
    ): String {
        val jwtBuilder = JWT.create()
        extraClaims.forEach { (key, value) -> jwtBuilder.withClaim(key, value.toString()) }

        return jwtBuilder.withArrayClaim(
            "authorities", userDetails.authorities.map { it.authority }.toTypedArray<String?>()
        ).withSubject(userDetails.username).withIssuedAt(Instant.now()).withIssuer(jwtProperties.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + jwtProperties.expiration)).sign(getSignInKey())
    }

    fun extractUsername(token: String): String {
        return decodedJWT(token).subject
    }

    private fun extractExpiration(token: String): Date {
        return decodedJWT(token).expiresAt
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username: String = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun getSignInKey(): Algorithm {
        return Algorithm.HMAC512(jwtProperties.secret.toByteArray())
    }
}