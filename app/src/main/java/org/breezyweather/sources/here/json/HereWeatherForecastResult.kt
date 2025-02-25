package org.breezyweather.sources.here.json

import kotlinx.serialization.Serializable

/**
 * List of reports
 * See https://developer.here.com/documentation/destination-weather/dev_guide/topics/resource-response-type-report.html
 */
@Serializable
data class HereWeatherForecastResult(
    val places: List<HereWeatherPlaceReport>?,
)
