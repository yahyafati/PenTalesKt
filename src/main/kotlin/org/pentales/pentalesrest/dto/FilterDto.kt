package org.pentales.pentalesrest.dto

class FilterDto(
    var columnName: String = "",

    var value: Any = "",

    var filterType: FilterTypes = FilterTypes.EQUALS
) {}