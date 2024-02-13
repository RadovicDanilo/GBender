package com.radovicdanilo.gbender.core

import com.radovicdanilo.gbender.model.Level
import com.radovicdanilo.gbender.model.Tuning


class AppCore private constructor() {
    companion object {
        val instance: AppCore by lazy {
            AppCore()
        }
    }
    val useSharps = true
    var tuning = Tuning.E
    var accuracyCents = 10
    var secondaryAccuracyCents = 100
    var timeToHoldNoteMilis = 2000

}
