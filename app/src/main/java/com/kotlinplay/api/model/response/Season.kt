package com.kotlinplay.api.model.response

data class Season(
    val _links: Links?,
    val endDate: String?,
    val episodeOrder: Int?,
    val id: Int?,
    val image: Image?,
    val name: String?,
    val network: Network?,
    val number: Int?,
    val premiereDate: String?,
    val summary: String?,
    val url: String?,
    val webChannel: Any?
)
