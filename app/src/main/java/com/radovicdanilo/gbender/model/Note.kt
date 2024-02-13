package com.radovicdanilo.gbender.model

import kotlin.math.pow

class Note(var string: Int, var fret: Int) {
    override fun toString(): String {

        val fretSuffix: String
        if (fret == 1) {
            fretSuffix = "st"
        } else if (fret == 2) {
            fretSuffix = "nd"
        } else if (fret == 3) {
            fretSuffix = "rd"
        } else {
            fretSuffix = "th"
        }

        val stringName: String
        if (string == 1) {
            stringName = "e"
        } else if (string == 2) {
            stringName = "B"
        } else {
            stringName = "G"
        }

        return "$fret$fretSuffix fret on the $stringName String"
    }
    fun getDesiredNoteFrequency(tuning: Tuning): Float {
        var steps: Float = (this.fret - 2).toFloat()
        if (this.string == 1) {
            steps += 9
        }
        if (this.string == 2) {
            steps += 4
        }
        return tuning.getReference() * 2.0.pow(steps / 12.0).toFloat()
    }

    fun getDesiredNoteFrequencyWithOffset(tuning: Tuning, offsetInCents: Int): Float {
        return getDesiredNoteFrequency(tuning) * 2.0.pow(offsetInCents.toFloat() / 1200.0).toFloat()
    }
}
