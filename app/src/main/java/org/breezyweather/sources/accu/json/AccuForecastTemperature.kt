package org.breezyweather.sources.accu.json

import kotlinx.serialization.Serializable

@Serializable
data class AccuForecastTemperature(
    val Minimum: AccuValue?,
    val Maximum: AccuValue?
)
