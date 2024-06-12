package org.pentales.pentalesrest.models.entities.converters

import jakarta.persistence.*

class ReasonsConverter :
    AttributeConverter<List<org.pentales.pentalesrest.models.entities.report.EContentReportReason>, String> {

    override fun convertToDatabaseColumn(attribute: List<org.pentales.pentalesrest.models.entities.report.EContentReportReason>?): String {
        if (attribute == null) {
            return ""
        }
        return attribute.joinToString(",")
    }

    override fun convertToEntityAttribute(dbData: String?): List<org.pentales.pentalesrest.models.entities.report.EContentReportReason> {
        if (dbData == null) {
            return emptyList()
        }
        return dbData.split(",").map { org.pentales.pentalesrest.models.entities.report.EContentReportReason.valueOf(it) }
    }
}