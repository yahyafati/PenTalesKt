package org.pentales.pentalesrest.models.converters

import jakarta.persistence.*
import org.pentales.pentalesrest.models.enums.*

class ReasonsConverter : AttributeConverter<EContentReportReason, String> {

    override fun convertToDatabaseColumn(attribute: EContentReportReason?): String {
        if (attribute == null) {
            return ""
        }
        return attribute.name
    }

    override fun convertToEntityAttribute(dbData: String?): EContentReportReason {
        if (dbData == null) {
            return EContentReportReason.SPAM_OR_PHISHING
        }
        return EContentReportReason.valueOf(dbData)
    }
}