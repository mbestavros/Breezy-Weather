package org.breezyweather.sources.china

import android.content.Context
import android.graphics.Color
import io.reactivex.rxjava3.core.Observable
import org.breezyweather.common.basic.models.Location
import org.breezyweather.common.source.HttpSource
import org.breezyweather.common.source.LocationSearchSource
import org.breezyweather.common.source.ReverseGeocodingSource
import org.breezyweather.common.basic.wrappers.WeatherWrapper
import org.breezyweather.settings.SettingsManager
import org.breezyweather.common.source.MainWeatherSource
import org.breezyweather.common.source.SecondaryWeatherSourceFeature
import org.breezyweather.sources.china.json.ChinaForecastResult
import org.breezyweather.sources.china.json.ChinaMinutelyResult
import retrofit2.Retrofit
import javax.inject.Inject

class ChinaService @Inject constructor(
    client: Retrofit.Builder
) : HttpSource(), MainWeatherSource, LocationSearchSource, ReverseGeocodingSource {

    override val id = "china"
    override val name = "中国"
    override val privacyPolicyUrl = "https://privacy.mi.com/all/zh_CN"

    override val color = Color.rgb(255, 105, 0)
    override val weatherAttribution = "北京天气、彩云天气、中国环境监测总站"
    override val locationSearchAttribution = "北京天气、彩云天气、中国环境监测总站"

    private val mApi by lazy {
        client
            .baseUrl(CHINA_WEATHER_BASE_URL)
            .build()
            .create(ChinaApi::class.java)
    }

    override fun requestWeather(
        context: Context, location: Location,
        ignoreFeatures: List<SecondaryWeatherSourceFeature>
    ): Observable<WeatherWrapper> {
        val mainly = mApi.getForecastWeather(
            location.latitude.toDouble(),
            location.longitude.toDouble(),
            location.isCurrentPosition,
            locationKey = "weathercn%3A" + location.cityId,
            days = 15,
            appKey = "weather20151024",
            sign = "zUFJoAR2ZVrDy1vF3D07",
            isGlobal = false,
            SettingsManager.getInstance(context).language.code
        )
        val forecast = mApi.getMinutelyWeather(
            location.latitude.toDouble(),
            location.longitude.toDouble(),
            SettingsManager.getInstance(context).language.code,
            isGlobal = false,
            appKey = "weather20151024",
            locationKey = "weathercn%3A" + location.cityId,
            sign = "zUFJoAR2ZVrDy1vF3D07"
        )
        return Observable.zip(mainly, forecast) {
                mainlyResult: ChinaForecastResult,
                forecastResult: ChinaMinutelyResult
            ->
            convert(
                location,
                mainlyResult,
                forecastResult
            )
        }
    }

    override fun requestLocationSearch(
        context: Context, query: String
    ): Observable<List<Location>> {
        return mApi.getLocationSearch(
            query,
            SettingsManager.getInstance(context).language.code
        )
            .map { results ->
                val locationList: MutableList<Location> = ArrayList()
                results.forEach {
                    if (it.locationKey?.startsWith("weathercn:") == true
                        && it.status == 0) {
                        locationList.add(convert(null, it))
                    }
                }
                locationList
            }
    }

    override fun requestReverseGeocodingLocation(
        context: Context,
        location: Location
    ): Observable<List<Location>> {
        return mApi.getLocationByGeoPosition(
            location.latitude.toDouble(),
            location.longitude.toDouble(),
            SettingsManager.getInstance(context).language.code
        )
            .map {
                val locationList: MutableList<Location> = ArrayList()
                if (it.getOrNull(0)?.locationKey?.startsWith("weathercn:") == true
                    && it[0].status == 0) {
                    locationList.add(convert(location, it[0]))
                }
                locationList
            }
    }

    companion object {
        private const val CHINA_WEATHER_BASE_URL = "https://weatherapi.market.xiaomi.com/wtr-v3/"
    }
}