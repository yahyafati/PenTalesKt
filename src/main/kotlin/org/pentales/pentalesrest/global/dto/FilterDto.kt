package org.pentales.pentalesrest.global.dto

class FilterDto(
    var name: String = "",

    var value: Any = "",

    var filterType: FilterTypes = FilterTypes.EQUALS
) {}