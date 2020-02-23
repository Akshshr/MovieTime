package com.kotlinplay.api.model.response

import java.io.Serializable

data class FullSchedule(
    val _embedded: Embedded?,
    val _links: LinksX?,
    val airdate: String?,
    val airstamp: String?,
    val airtime: String?,
    val id: Int?,
    val image: Any?,
    val name: String?,
    val number: Int?,
    val runtime: Int?,
    val season: Int?,
    val summary: Any?,
    val url: String?
) : Serializable

data class Embedded(
    val show: Show?
) : Serializable

data class Show(
    val _links: Show?,
    val externals: Externals?,
    val genres: List<String?>?,
    val id: Int?,
    val image: Image?,
    val language: String?,
    val name: String?,
    val network: Network?,
    val officialSite: String?,
    val premiered: String?,
    val rating: Rating?,
    val runtime: Int?,
    val schedule: Schedule?,
    val status: String?,
    val summary: String?,
    val type: String?,
    val updated: Int?,
    val url: String?,
    val webChannel: Any?,
    val weight: Int?
): Serializable

data class Links(
    val nextepisode: Nextepisode?,
    val previousepisode: Previousepisode?,
    val self: Self?
): Serializable

data class Nextepisode(
    val href: String?
): Serializable

data class Previousepisode(
    val href: String?
):Serializable

data class Self(
    val href: String?
):Serializable

data class Externals(
    val imdb: String?,
    val thetvdb: Int?,
    val tvrage: Int?
): Serializable

data class Image(
    val medium: String?,
    val original: String?
): Serializable

data class Network(
    val country: Country?,
    val id: Int?,
    val name: String?
): Serializable

data class Country(
    val code: String?,
    val name: String?,
    val timezone: String?
): Serializable

data class Rating(
    val average: Double?
): Serializable

data class Schedule(
    val days: List<String?>?,
    val time: String?
): Serializable

data class LinksX(
    val self: SelfX?
): Serializable

data class SelfX(
    val href: String?
): Serializable