package org.pentales.pentalesrest.models.converters

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*

class AuthorityConverter : AttributeConverter<Authority, String> {

    override fun convertToDatabaseColumn(attribute: Authority?): String {
        if (attribute == null) {
            return ""
        }
        return attribute.authority
    }

    override fun convertToEntityAttribute(dbData: String?): Authority {
        if (dbData == null) {
            return Authority("")
        }
        return Authority(dbData)
    }
}