package org.breezyweather.sources.metno.json

import kotlinx.serialization.Serializable

/**
 * MET Norway Nowcast
 */
@Serializable
data class MetNoNowcastResult(
    val properties: MetNoNowcastProperties? = null
)
