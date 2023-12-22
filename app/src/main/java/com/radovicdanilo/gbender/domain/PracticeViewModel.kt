package com.radovicdanilo.gbender.domain

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.media.RingtoneManager
import android.util.Log
import com.lbbento.pitchuptuner.GuitarTuner
import com.lbbento.pitchuptuner.GuitarTunerListener
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
import com.radovicdanilo.gbender.data.model.Level
import com.radovicdanilo.gbender.data.model.Note
import com.radovicdanilo.gbender.data.model.Tuning
import com.radovicdanilo.gbender.di.AppCore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.pow


class PracticeViewModel(val levels: List<Level>, val tuning: Tuning) {

    var currentNote = MutableStateFlow(getRandomNote())
    var currentLevel = MutableStateFlow(getRandomLevel())
    val currentPitch = MutableStateFlow(440.0f)
    var circleColorOn = MutableStateFlow(arrayListOf(false, false, false, false, false))

    var progress: MutableStateFlow<Long> = MutableStateFlow(0)
    var deltaTime: Long = 0
    var active = false

    @SuppressLint("MissingPermission")
    fun start() {
        val audioRecorder =
            PitchAudioRecorder(
                AudioRecord(
                    MediaRecorder.AudioSource.DEFAULT,
                    44100,
                    AudioFormat.CHANNEL_IN_DEFAULT,
                    AudioFormat.ENCODING_PCM_16BIT,
                    AudioRecord.getMinBufferSize(
                        44100,
                        AudioFormat.CHANNEL_IN_DEFAULT,
                        AudioFormat.ENCODING_PCM_16BIT
                    )
                )
            )

        val guitarTunerListener = object : GuitarTunerListener {

            override fun onNoteReceived(tunerResult: TunerResult) {
                val now = System.currentTimeMillis();
                currentPitch.value = (tunerResult.expectedFrequency + tunerResult.diffFrequency).toFloat()
                if(currentPitch.value.toInt() == 0)
                    return
                val difference = (tunerResult.expectedFrequency + tunerResult.diffFrequency).toFloat() - getDesiredNoteFrequency()
                circleColorOn.value = arrayListOf(false, false, false, false, false)
                var activeTemp = false
                if (difference < -50) {
                    circleColorOn.value[0] = true
                } else if (difference < -25) {
                    circleColorOn.value[1] = true
                } else if (difference > 50) {
                    circleColorOn.value[4] = true
                } else if (difference > 25) {
                    circleColorOn.value[3] = true
                } else {
                    circleColorOn.value[2] = true
                    activeTemp = true
                }
                active = activeTemp
                if(active){
                    if(deltaTime == 0.toLong()){
                        deltaTime = now
                    }
                    progress.value = progress.value + now - deltaTime
                    deltaTime = now
                    if(progress.value > 2000){
                        deltaTime = 0
                        progress.value = 10
                        next()
                    }
                }else{
                    deltaTime = 0
                    progress.value = 10
                }
                Log.d("PROG", progress.value.toString())
            }

            override fun onError(e: Throwable) {
                Log.d("ERROR", e.message.toString())
            }
        }

        val guitarTuner = GuitarTuner(audioRecorder, guitarTunerListener)
        guitarTuner.start()
    }

    fun next() {
        currentLevel.value = getRandomLevel()
        currentNote.value = getRandomNote()
    }

    fun getRandomLevel(): Level {
        val i: Int = (0..levels.size - 1).random()
        return levels[i]
    }

    fun getRandomNote(): Note {
        val string: Int = (1..3).random()
        val fret: Int = (4..24).random()
        return Note(string, fret)
    }

    fun getDesiredNoteFrequency(): Float {
        var steps = currentNote.value.fret - 2
        if (currentNote.value.string == 1) {
            steps += 7
        }
        if (currentNote.value.string == 1) {
            steps += 2
        }
        return tuning.getReference() * 2.0.pow(steps / 12.0).toFloat()
    }

}