package com.radovicdanilo.gbender.di

import com.radovicdanilo.gbender.data.model.Tuning
import com.radovicdanilo.gbender.domain.PracticeViewModel


class AppCore private constructor() {

    companion object {
        val instance: AppCore by lazy {
            AppCore()
        }
    }

    var practiceViewModel = PracticeViewModel(Tuning.E)
    var accuracyCents = 10
    var secondaryAccuracyCents = 100
    var timeMilis = 2500
    var tuning = Tuning.E

}