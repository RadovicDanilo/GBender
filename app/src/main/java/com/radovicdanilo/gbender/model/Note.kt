package com.radovicdanilo.gbender.model

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

}
