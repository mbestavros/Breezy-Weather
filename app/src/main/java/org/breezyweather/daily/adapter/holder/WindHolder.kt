package org.breezyweather.daily.adapter.holder

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import org.breezyweather.R
import org.breezyweather.common.basic.models.options.unit.SpeedUnit
import org.breezyweather.daily.adapter.DailyWeatherAdapter
import org.breezyweather.daily.adapter.model.DailyWind
import org.breezyweather.settings.SettingsManager

class WindHolder(parent: ViewGroup) : DailyWeatherAdapter.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_weather_daily_wind, parent, false)
) {
    private val mIcon: AppCompatImageView = itemView.findViewById(R.id.item_weather_daily_wind_arrow)
    private val mDirectionText: TextView = itemView.findViewById(R.id.item_weather_daily_wind_directionValue)
    private val mSpeed: LinearLayout = itemView.findViewById(R.id.item_weather_daily_wind_speed)
    private val mSpeedText: TextView = itemView.findViewById(R.id.item_weather_daily_wind_speedValue)
    private val mStrengthText: TextView = itemView.findViewById(R.id.item_weather_daily_wind_strengthValue)
    private val mSpeedUnit: SpeedUnit = SettingsManager.getInstance(parent.context).speedUnit
    private val mGusts: LinearLayout = itemView.findViewById(R.id.item_weather_daily_wind_gusts)
    private val mGustsText: TextView = itemView.findViewById(R.id.item_weather_daily_wind_gustsValue)

    @SuppressLint("SetTextI18n", "RestrictedApi")
    override fun onBindView(model: DailyWeatherAdapter.ViewModel, position: Int) {
        val wind = (model as DailyWind).wind
        if (wind.speed != null) {
            val talkBackBuilder = StringBuilder(
                itemView.context.getString(R.string.wind)
            )
            mIcon.supportImageTintList = ColorStateList.valueOf(wind.getColor(itemView.context))
            if (wind.degree != null) {
                if (wind.degree != -1f) {
                    mIcon.rotation = wind.degree + 180
                }
                talkBackBuilder.append(", ").append(wind.getDirection(itemView.context))
                if (wind.degree == -1f || wind.degree % 45 == 0f) {
                    mDirectionText.text = wind.getDirection(itemView.context)
                } else {
                    mDirectionText.text = (wind.getDirection(itemView.context)
                            + " (" + (wind.degree % 360).toInt() + "°)")
                }
            }
            if (wind.speed > 0) {
                talkBackBuilder.append(", ")
                    .append(mSpeedUnit.getValueText(mSpeedText.context, wind.speed))
                mSpeed.visibility = View.VISIBLE
                mSpeedText.text = mSpeedUnit.getValueText(mSpeedText.context, wind.speed)
            } else {
                mSpeed.visibility = View.GONE
            }
            talkBackBuilder.append(", ").append(wind.getStrength(mSpeedText.context))
            mStrengthText.text = wind.getStrength(mSpeedText.context)
            itemView.contentDescription = talkBackBuilder.toString()
            if (wind.gusts != null && wind.gusts > 0) {
                talkBackBuilder.append(", ")
                    .append(mSpeedUnit.getValueText(mGustsText.context, wind.gusts))
                mGusts.visibility = View.VISIBLE
                mGustsText.text = mSpeedUnit.getValueText(mGustsText.context, wind.gusts)
            } else {
                mGusts.visibility = View.GONE
            }
        } else {
            mSpeed.visibility = View.GONE
            // TODO: Hide
        }
    }
}
