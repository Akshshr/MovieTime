package com.kotlinplay.api.model.response

import java.io.Serializable

data class Cast(
    val character: Character?,
    val person: Person?,
    val self: Boolean?,
    val voice: Boolean?
): Serializable

data class Character(
    val _links: Links?,
    val id: Int?,
    val image: Image?,
    val name: String?,
    val url: String?
): Serializable

data class Person(
    val _links: LinksX?,
    val birthday: String?,
    val country: Country?,
    val deathday: Any?,
    val gender: String?,
    val id: Int?,
    val image: ImageX?,
    val name: String?,
    val url: String?
): Serializable


data class ImageX(
    val medium: String?,
    val original: String?
)