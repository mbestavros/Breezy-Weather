package org.breezyweather.db.repositories

import org.breezyweather.common.basic.models.Location
import org.breezyweather.common.basic.models.weather.Weather
import org.breezyweather.db.ObjectBox.boxStore
import org.breezyweather.db.entities.AlertEntity
import org.breezyweather.db.entities.DailyEntity
import org.breezyweather.db.entities.HistoryEntity
import org.breezyweather.db.entities.HourlyEntity
import org.breezyweather.db.entities.MinutelyEntity
import org.breezyweather.db.entities.WeatherEntity
import org.breezyweather.db.entities.WeatherEntity_
import org.breezyweather.db.generators.*

object WeatherEntityRepository {
    // insert.
    fun writeWeather(location: Location, weather: Weather) {
        boxStore.callInTxNoException {
            deleteWeather(location)
            insertWeatherEntity(WeatherEntityGenerator.generate(location, weather))
            DailyEntityRepository.insertDailyList(
                DailyEntityGenerator.generate(
                    location.formattedId, weather.dailyForecast
                )
            )
            HourlyEntityRepository.insertHourlyList(
                HourlyEntityGenerator.generateEntityList(
                    location.formattedId, weather.hourlyForecast
                )
            )
            MinutelyEntityRepository.insertMinutelyList(
                MinutelyEntityGenerator.generate(
                    location.formattedId, weather.minutelyForecast
                )
            )
            AlertEntityRepository.insertAlertList(
                AlertEntityGenerator.generate(
                    location.formattedId, weather.alertList
                )
            )
            if (weather.dailyForecast.isNotEmpty() && weather.dailyForecast[0].day?.temperature != null && weather.dailyForecast[0].night?.temperature != null) {
                HistoryEntityRepository.insertHistoryEntity(
                    HistoryEntityGenerator.generate(
                        location.formattedId, weather
                    )
                )
            }
            if (weather.yesterday != null) {
                HistoryEntityRepository.insertHistoryEntity(
                    HistoryEntityGenerator.generate(
                        location.formattedId, weather.yesterday
                    )
                )
            }
            true
        }
    }

    fun insertWeatherEntity(entity: WeatherEntity) {
        boxStore.boxFor(WeatherEntity::class.java).put(entity)
    }

    // delete.
    fun deleteWeather(location: Location) {
        boxStore.callInTxNoException {
            deleteWeather(
                selectWeatherEntityList(location.formattedId)
            )
            HistoryEntityRepository.deleteLocationHistoryEntity(
                HistoryEntityRepository.selectHistoryEntityList(location.formattedId)
            )
            DailyEntityRepository.deleteDailyEntityList(
                DailyEntityRepository.selectDailyEntityList(location.formattedId)
            )
            HourlyEntityRepository.deleteHourlyEntityList(
                HourlyEntityRepository.selectHourlyEntityList(location.formattedId)
            )
            MinutelyEntityRepository.deleteMinutelyEntityList(
                MinutelyEntityRepository.selectMinutelyEntityList(location.formattedId)
            )
            AlertEntityRepository.deleteAlertList(
                AlertEntityRepository.selectLocationAlertEntity(location.formattedId)
            )
            true
        }
    }

    fun deleteWeather(entityList: List<WeatherEntity>) {
        boxStore.boxFor(WeatherEntity::class.java).remove(entityList)
    }

    /**
     * Cascade removes all weather data saved in database
     * Locations are preserved
     */
    fun deleteAllWeather() {
        boxStore.boxFor(WeatherEntity::class.java).removeAll()
        boxStore.boxFor(AlertEntity::class.java).removeAll()
        boxStore.boxFor(DailyEntity::class.java).removeAll()
        boxStore.boxFor(HistoryEntity::class.java).removeAll()
        boxStore.boxFor(HourlyEntity::class.java).removeAll()
        boxStore.boxFor(MinutelyEntity::class.java).removeAll()
    }

    // select.
    fun readWeather(location: Location): Weather? {
        val weatherEntity = selectWeatherEntity(location.formattedId) ?: return null
        val historyEntity = HistoryEntityRepository.selectYesterdayHistoryEntity(
            location.formattedId, weatherEntity.publishDate, location.timeZone
        )
        return WeatherEntityGenerator.generate(weatherEntity, historyEntity, boxStore)
    }

    fun selectWeatherEntity(formattedId: String): WeatherEntity? {
        val entityList = selectWeatherEntityList(formattedId)
        return if (entityList.isEmpty()) null else entityList[0]
    }

    fun selectWeatherEntityList(formattedId: String): List<WeatherEntity> {
        val query = boxStore.boxFor(WeatherEntity::class.java)
            .query(WeatherEntity_.formattedId.equal(formattedId)).build()
        val results = query.find()
        query.close()
        return results
    }
}
