package com.example.guessnumber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guessnumber.ui.theme.GuessNumberTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuessNumberTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                    ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun Title() {
        Text(
            text = stringResource(R.string.title),
            fontSize = 46.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
        )
}

//@Composable
//fun EditNumberField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    TextField(
//        value = value,
//        //value = amountInput,
//        onValueChange = onValueChange,
//        //onValueChange = { amountInput= it },
//        singleLine = true,
//        label = { Text(stringResource(R.string.guess_amount)) },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//        modifier = modifier,
//    )
//}
@Composable
fun App(){
    //var amountInput by remember { mutableStateOf("") }
    var randomNumber by remember { mutableStateOf(0) }
    var guessText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    ) {
        Title()

        Spacer(modifier = Modifier.height(40.dp))

        TextField(
            value = guessText,
            onValueChange = { guessText = it },
            label = { Text("Enter your guess") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    checkGuess(guessText, randomNumber) { result ->
                        resultText = result
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                checkGuess(guessText, randomNumber) { result ->
                    resultText = result
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = resultText)
    }

    DisposableEffect(Unit) {
        randomNumber = Random.nextInt(1, 101)
        onDispose { }
    }
}

fun checkGuess(guessText: String, randomNumber: Int, onResult: (String) -> Unit) {
    if (guessText.isNotEmpty()) {
        val guess = guessText.toInt()
        when {
            guess < randomNumber -> onResult("Too low! Try again.")
            guess > randomNumber -> onResult("Too high! Try again.")
            else -> onResult("Congratulations! You guessed the number.")
        }
    } else {
        onResult("Please enter a number.")
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GuessNumberTheme {
        App()
    }
}