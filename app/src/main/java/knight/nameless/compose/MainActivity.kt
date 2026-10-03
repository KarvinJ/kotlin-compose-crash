package knight.nameless.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import knight.nameless.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {

            }
        }

        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Test",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


//A composable is any kotlin function that will be using in compose code
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    //This is the equivalent of linear layout of xml, but in compose
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Hello $name!",
            modifier = modifier,
            Color.Red,
            fontSize = 22.sp
        )

        Text(
            text = "Test7",
            modifier = modifier,
            Color.Blue,
            fontSize = 22.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        Greeting("Test4")
    }
}