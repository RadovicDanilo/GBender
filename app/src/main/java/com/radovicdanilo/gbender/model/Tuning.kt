package com.radovicdanilo.gbender.model

import com.radovicdanilo.gbender.core.AppCore

enum class Tuning {
    E, Eb, D, Db, C, B, Bb, A, Ab;

    override fun toString(): String {
        if(AppCore.instance.useSharps){
            return toStringSharp()
        }
        return toStringFlat()
    }
    private fun toStringSharp(): String{
        if (this == E) {
            return "E"
        }
        if (this == Eb) {
            return "D#"
        }
        if (this == D) {
            return "D"
        }
        if (this == Db) {
            return "C#"
        }
        if (this == C) {
            return "C"
        }
        if (this == B) {
            return "B"
        }
        if (this == Bb) {
            return "A#"
        }
        if (this == A) {
            return "A"
        }
        if (this == Ab) {
            return "G#"
        }
        return "ERROR"
    }
    private fun toStringFlat(): String{
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
        //TODO calculate these pitches
        if (this == E) {
            return 220.0f
        }
        if (this == Eb) {
            return 0f
        }
        if (this == D) {
            return 0f
        }
        if (this == Db) {
            return 0f
        }
        if (this == C) {
            return 0f
        }
        if (this == B) {
            return 0f
        }
        if (this == Bb) {
            return 0f
        }
        if (this == A) {
            return 0f
        }
        if (this == Ab) {
            return 0f
        }
        return 440.0f
    }
}