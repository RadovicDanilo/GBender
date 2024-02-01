package com.radovicdanilo.gbender.di

import com.radovicdanilo.gbender.data.model.Level
import com.radovicdanilo.gbender.data.model.Tuning


class AppCore private constructor() {
    companion object {
        val instance: AppCore by lazy {
            AppCore()
        }
    }
    val levels: ArrayList<Level> = arrayListOf(Level.HALF, Level.WHOLE)
    var tuning = Tuning.E
    var accuracyCents = 10
    var secondaryAccuracyCents = 100
    var timeMilis = 2500

}
