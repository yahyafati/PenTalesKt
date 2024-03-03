package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.repo.base.*
import org.pentales.pentalesrest.repo.specifications.*
import org.pentales.pentalesrest.services.*
import kotlin.reflect.*
import kotlin.reflect.full.*

class PushNotificationTokenServices(
    private val pushNotificationTokenRepository: PushNotificationTokenRepository
) : IPushNotificationTokenServices {

    override val repository: IRepoSpecification<PushNotificationToken, Long>
        get() = pushNotificationTokenRepository
    override val modelProperties: Collection<KProperty1<PushNotificationToken, *>>
        get() = PushNotificationToken::class.memberProperties
    override val specification: ISpecification<PushNotificationToken>
        get() = object : ISpecification<PushNotificationToken> {}
}
