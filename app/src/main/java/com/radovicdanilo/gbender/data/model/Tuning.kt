package com.radovicdanilo.gbender.data.model

enum class Tuning {
    E, Eb, D, Db, C, B, Bb, A, Ab;

    override fun toString(): String {
        if (this == E) {
            return "E"
        }
        if (this == Eb) {
            return "Eb"

        }
        if (this == D) {
            return "D"

        }
        if (this == Db) {
            return "Db"

        }
        if (this == C) {
            return "C"

        }
        if (this == B) {
            return "B"

        }
        if (this == Bb) {
            return "Bb"

        }
        if (this == A) {
            return "A"

        }
        if (this == Ab) {
            return "Ab"

        }
        return "ERROR"

    }

    fun getReference(): Float {
        if (this == E) {
            return 220.0f
        }
        return 440.0f
    }
}