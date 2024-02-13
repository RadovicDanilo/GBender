package com.radovicdanilo.gbender.domain

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.ViewModel
import com.lbbento.pitchuptuner.GuitarTuner
import com.lbbento.pitchuptuner.GuitarTunerListener
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
import com.radovicdanilo.gbender.R
import com.radovicdanilo.gbender.core.AppCore
import com.radovicdanilo.gbender.model.Level
import com.radovicdanilo.gbender.model.Note
import com.radovicdanilo.gbender.model.Tuning
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.pow


class PracticeViewModel(
) : ViewModel() {
    var context: Context? = null
    private val tuning: Tuning = AppCore.instance.tuning
    private var levels: ArrayList<Level> = arrayListOf()
    var currentNote = MutableStateFlow(Note(2, 15))
    var currentLevel = MutableStateFlow(Level.HALF)
    val currentPitch = MutableStateFlow(440.0f)
    var circleColorOn = MutableStateFlow(arrayListOf(false, false, false, false, false))

    var progress: MutableStateFlow<Long> = MutableStateFlow(0)
    var previousTime: Long = 0
    var active = false

    @SuppressLint("MissingPermission")
    fun start() {
        if(levels.size > 0)
            return
        for (l in Level.values()) {
            if (l.selected.value) levels.add(l)
        }
        currentNote = MutableStateFlow(getRandomNote())
        currentLevel = MutableStateFlow(getRandomLevel())
        val audioRecorder = PitchAudioRecorder(
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
                val now = System.currentTimeMillis()

                currentPitch.value =
                    (tunerResult.expectedFrequency + tunerResult.diffFrequency).toFloat()
                if (currentPitch.value.toInt() < 20) return
                circleColorOn.value = arrayListOf(false, false, false, false, false)
                active = false
                when {
                    currentPitch.value < getDesiredNoteFrequencyWithOffset(-AppCore.instance.secondaryAccuracyCents) -> circleColorOn.value[0] =
                        true

                    currentPitch.value < getDesiredNoteFrequencyWithOffset(-AppCore.instance.accuracyCents) -> circleColorOn.value[1] =
                        true

                    currentPitch.value > getDesiredNoteFrequencyWithOffset(AppCore.instance.secondaryAccuracyCents) -> circleColorOn.value[3] =
                        true

                    currentPitch.value > getDesiredNoteFrequencyWithOffset(AppCore.instance.accuracyCents) -> circleColorOn.value[4] =
                        true

                    else -> {
                        circleColorOn.value[2] = true
                        active = true
                    }
                }
                if (!active) {
                    previousTime = 0
                    progress.value = 0
                    return
                }
                if (previousTime == 0.toLong()) {
                    previousTime = now
                }
                progress.value = progress.value + now - previousTime
                previousTime = now
                if (progress.value > AppCore.instance.timeToHoldNoteMilis) {

                    val mediaPlayer = MediaPlayer.create(context, R.raw.ping)
                    mediaPlayer.start()
                    Thread.sleep(1000)
                    mediaPlayer.release()

                    previousTime = 0
                    progress.value = 0
                    noteChange()

                }
            }

            override fun onError(throwable: Throwable) {
                Log.d("ERROR", throwable.message.toString())
            }
        }

        val guitarTuner = GuitarTuner(audioRecorder, guitarTunerListener)
        guitarTuner.start()
        noteChange()
    }

    fun noteChange() {
        currentLevel.value = getRandomLevel()
        currentNote.value = getRandomNote()
    }

    private fun getRandomLevel(): Level {
        val i: Int = (0 until levels.size).random()
        return levels[i]
    }

    private fun getRandomNote(): Note {
        val string: Int = (1..3).random()
        val fret: Int = (4..24).random()
        return Note(string, fret)
    }

    fun getDesiredNoteFrequency(): Float {
        var steps: Float = (currentNote.value.fret - 2).toFloat()
        if (currentNote.value.string == 1) {
            steps += 9
        }
        if (currentNote.value.string == 2) {
            steps += 4
        }
        return tuning.getReference() * 2.0.pow(steps / 12.0).toFloat()
    }

    fun getDesiredNoteFrequencyWithOffset(offsetInCents: Int): Float {
        return getDesiredNoteFrequency() * 2.0.pow(offsetInCents.toFloat() / 1200.0).toFloat()
    }

}
