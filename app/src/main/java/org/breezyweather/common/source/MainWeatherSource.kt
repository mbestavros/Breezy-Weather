package org.breezyweather.common.source

import android.content.Context
import androidx.annotation.ColorInt
import io.reactivex.rxjava3.core.Observable
import org.breezyweather.common.basic.models.Location
import org.breezyweather.common.basic.wrappers.WeatherWrapper

/**
 * Weather service.
 */
interface MainWeatherSource : Source {

    /**
     * Official color used by the source
     */
    @get:ColorInt
    val color: Int

    /**
     * Credits and acknowledgments that will be shown at the bottom of main screen
     * Please check terms of the source to be sure to put the correct term here
     * Example: MyGreatApi (CC BY 4.0)
     */
    val weatherAttribution: String

    /**
     * Returns weather converted to Breezy Weather Weather object
     * @param ignoreFeatures List of features we request later to a secondary source. If your
     * weather source support them, you should ignore them (for example, not call an
     * additional API endpoint), as they will be overwritten later anyway
     * TODO: Implement "ignore" on existing main weather sources
     */
    fun requestWeather(
        context: Context, location: Location,
        ignoreFeatures: List<SecondaryWeatherSourceFeature>
    ): Observable<WeatherWrapper>

}
