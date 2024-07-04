package com.example.preapp

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Locale

class TextViewModel: ViewModel() {
    private val _text = MutableLiveData("")
    val text: LiveData<String> = _text
    private var textToSpeech: TextToSpeech? = null

    fun onTextChanged(newText: String) { _text.value = newText }

    fun onTextPlayed(context: Context) {
        textToSpeech = TextToSpeech(context) {
            textToSpeech?.let { tts ->
                tts.language = Locale.KOREA
                tts.setSpeechRate(1.0f)
                tts.speak(
                    _text.value,
                    TextToSpeech.QUEUE_ADD,
                    null,
                    null
                )
            }
        }
    }
}