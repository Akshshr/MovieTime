package com.kotlinplay.api.model.response

import java.io.Serializable

data class SearchTermResponse(
    val score: Double?,
    val show: Show?
) : Serializable

