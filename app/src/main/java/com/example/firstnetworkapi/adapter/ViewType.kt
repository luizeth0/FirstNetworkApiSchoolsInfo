package com.example.firstnetworkapi.adapter

import com.example.firstnetworkapi.model.SchoolsItem

sealed class ViewType {
    data class SCHOOL(val schoolItem: SchoolsItem) : ViewType()
    data class LETTER(val letter: String): ViewType()
}
