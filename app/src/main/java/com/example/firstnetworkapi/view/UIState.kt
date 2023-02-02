package com.example.firstnetworkapi.view

import com.example.firstnetworkapi.model.SchoolsItem

sealed class UIState {
    object LOADING : UIState()
    data class SUCCESS(val response: List<SchoolsItem>) : UIState()
    data class ERROR(val error: Exception) : UIState()
}
