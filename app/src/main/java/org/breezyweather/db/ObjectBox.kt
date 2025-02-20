package org.breezyweather.db

import android.content.Context
import io.objectbox.BoxStore
import org.breezyweather.BuildConfig
import org.breezyweather.common.utils.helpers.LogHelper
import org.breezyweather.db.entities.MyObjectBox

/**
 * Singleton to keep BoxStore reference.
 */
object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()

        if (BuildConfig.DEBUG) {
            LogHelper.log(msg = "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
        }
    }

}