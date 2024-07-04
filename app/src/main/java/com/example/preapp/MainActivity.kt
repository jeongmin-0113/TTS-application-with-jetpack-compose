package com.example.preapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.preapp.ui.theme.PreAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PreAppTheme {
                Scaffold(modifier = Modifier) { innerPadding ->
                    AppScreen(modifier = Modifier.padding(innerPadding), textViewModel = TextViewModel())
                }
            }
        }
    }
}

@Composable
fun AppScreen(
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
        CustomTextbar(
            msg = myMessage,
            onMsgChanged = {textViewModel.onTextChanged(it)}
        )

        IconButton(
            modifier = Modifier,
            onClick = { textViewModel.onTextPlayed(context) },
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onBackground,
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
            )
        }
    }
}

@Preview
@Composable
fun PreviewOfAppScreen() {
    PreAppTheme {
        AppScreen()
    }
}

@Composable
fun CustomTextbar(
    modifier: Modifier = Modifier,
    msg: String,
    onMsgChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = msg,
        onValueChange = onMsgChanged,
        placeholder = { Text(text = "What do you want to say?") }
    )
}

@Preview
@Composable
fun PreviewOfCustomTextbar() {
    PreAppTheme {
        val textViewModel = TextViewModel()
        val msg by textViewModel.text.observeAsState("")
        CustomTextbar(msg = msg, onMsgChanged = {textViewModel.onTextChanged(it)})
    }
}

