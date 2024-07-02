package com.example.preapp

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.preapp.ui.theme.PreAppTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PreAppTheme {
                Scaffold(modifier = Modifier) { innerPadding ->
                    appScreen(modifier = Modifier.padding(innerPadding), textViewModel = TextViewModel())
                }
            }
        }
    }
}

@Composable
fun appScreen(
    modifier: Modifier = Modifier,
    textViewModel: TextViewModel = TextViewModel(),
) {
    val myMessage by textViewModel.text.observeAsState("")
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        textBar(
            msg = myMessage,
            onMsgChanged = {textViewModel.onTextChanged(it)}
        )

        IconButton(
            modifier = Modifier,
            onClick = { textViewModel.onTextPlayed(context) },
        ) {
            Icon(
                tint = Color.White,
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
            )
        }
    }
}

@Preview
@Composable
fun preview_appScreen() {
    PreAppTheme {
        appScreen()
    }
}

@Composable
fun textBar(
    modifier: Modifier = Modifier,
    msg: String,
    onMsgChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = msg,
        onValueChange = onMsgChanged,
        textStyle = TextStyle(color = Color.White),
        placeholder = { Text(text = "What do you want to say?") }
    )
}

@Preview
@Composable
fun preview_Textbar() {
    PreAppTheme {
        val textViewModel = TextViewModel()
        val msg by textViewModel.text.observeAsState("")
        textBar(msg = msg, onMsgChanged = {textViewModel.onTextChanged(it)})
    }
}

class TextViewModel: ViewModel() {
    private val _text = MutableLiveData("")
    val text: LiveData<String> = _text
    private var textToSpeech:TextToSpeech? = null

    fun onTextChanged(newText: String) { _text.value = newText }

    fun onTextPlayed(context: Context) {
        textToSpeech = TextToSpeech(context) {
            textToSpeech?.let { txtToSpeech ->
                txtToSpeech.language = Locale.KOREA
                txtToSpeech.setSpeechRate(1.0f)
                txtToSpeech.speak(
                    _text.value,
                    TextToSpeech.QUEUE_ADD,
                    null,
                    null
                )
            }
        }
    }
}