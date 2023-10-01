package org.pentales.pentalesrest.models.converters

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*

class AuthorityConverter : AttributeConverter<List<Authority>, String> {

    override fun convertToDatabaseColumn(attribute: List<Authority>?): String {
        if (attribute == null) {
            return ""
        }
        return attribute.joinToString(",") { it.authority }
    }

    override fun convertToEntityAttribute(dbData: String?): List<Authority> {
        if (dbData == null) {
            return emptyList()
        }
        return dbData
                .split(",")
                .map { Authority(it) }
    }
}