package org.breezyweather.common.basic.wrappers

import org.breezyweather.common.basic.models.weather.Alert
import org.breezyweather.common.basic.models.weather.Base
import org.breezyweather.common.basic.models.weather.Current
import org.breezyweather.common.basic.models.weather.Daily
import org.breezyweather.common.basic.models.weather.History
import org.breezyweather.common.basic.models.weather.Minutely

/**
 * Wrapper very similar to the object in database.
 * Helps the transition process and computing of missing data.
 */
data class WeatherWrapper(
    val base: Base? = null,
    val current: Current? = null,
    val yesterday: History? = null,
    val dailyForecast: List<Daily>? = null,
    val hourlyForecast: List<HourlyWrapper>? = null,
    val minutelyForecast: List<Minutely>? = null,
    val alertList: List<Alert>? = null
)