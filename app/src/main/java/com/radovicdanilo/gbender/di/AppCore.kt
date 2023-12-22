package com.radovicdanilo.gbender.di



class AppCore private constructor() {

    companion object {
        val instance: AppCore by lazy {
            AppCore()
        }
    }
    var accuracy = 20
    var timeMilis = 5000

}