package org.pentales.pentalesrest.models.converters

import jakarta.persistence.*
import org.pentales.pentalesrest.models.enums.*

class ReasonsConverter : AttributeConverter<List<EContentReportReason>, String> {

    override fun convertToDatabaseColumn(attribute: List<EContentReportReason>?): String {
        if (attribute == null) {
            return ""
        }
        return attribute.joinToString { it.name }
    }

    override fun convertToEntityAttribute(dbData: String?): List<EContentReportReason> {
        if (dbData == null) {
            return emptyList()
        }
        return dbData.split(",").map { EContentReportReason.valueOf(it) }
    }
}