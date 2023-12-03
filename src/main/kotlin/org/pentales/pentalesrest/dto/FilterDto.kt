package org.pentales.pentalesrest.dto

class FilterDto(
    var name: String = "",

    var value: Any = "",

    var filterType: FilterTypes = FilterTypes.EQUALS
) {}