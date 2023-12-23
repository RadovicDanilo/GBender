package com.radovicdanilo.gbender.data.model

import kotlinx.coroutines.flow.MutableStateFlow

enum class Level {

    HALF, WHOLE, WHOLE_AND_HALF, TWO_WHOLE, TWO_WHOLE_AND_HALF, THREE_WHOLE;
    var selected = MutableStateFlow(false)

    override fun toString(): String {
        if (this == HALF) {
            return "a half step"
        }
        if (this == WHOLE) {
            return "a whole step"
        }
        if (this == WHOLE_AND_HALF) {
            return "one and a half step"
        }
        if (this == TWO_WHOLE) {
            return "two whole steps"
        }
        if (this == TWO_WHOLE_AND_HALF) {
            return "two and a half whole steps"
        }
        if (this == THREE_WHOLE) {
            return "three whole steps"
        }
        return "ERROR"
    }

    fun frets(): Int {
        if (this == HALF) {
            return 1
        }
        if (this == WHOLE) {
            return 2
        }
        if (this == WHOLE_AND_HALF) {
            return 3
        }
        if (this == TWO_WHOLE) {
            return 4
        }
        if (this == TWO_WHOLE_AND_HALF) {
            return 5
        }
        if (this == THREE_WHOLE) {
            return 6
        }
        return 0
    }

}


