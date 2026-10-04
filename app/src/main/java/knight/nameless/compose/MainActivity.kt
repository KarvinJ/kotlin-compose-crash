package knight.nameless.compose

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      Coroutines are an easy way to handle a new thread in android without blocking the main thread,
//      With indicate that we are working with the IO Dispatcher, we are switching to a new thread
//      for better handling IO operations
        GlobalScope.launch(Dispatchers.IO) {

//            this will delay the thread for 100 milliseconds, but will not feel this delay in the ui
            delay(100.milliseconds)

//            If inside a coroutine with want to switch back to another thread we use withContext,
            // and here will switch back to the main thread cuz this is the one that handle the ui
            withContext(Dispatchers.Main) {

            }
        }

//        This is a coroutine that will block the main thread, we use this if we want to call a
//        suspend function on the main thread, for example delay
//        runBlocking {
////we can also add other coroutines inside this block, that will run async without blocking the main thread
//            launch {
//                delay(100.milliseconds)
//            }
//
////            this will delay the main thread for 100 milliseconds, and will feel this delay in the ui
//            delay(100.milliseconds)
//        }

//       this code works the same as a delay inside the runBlocking coroutine, in case I want to.
//        Thread.sleep(100)

//        every coroutine will return a job, that can be used to cancel the coroutine
//        or wait for the coroutine to finish
        val job = GlobalScope.launch(Dispatchers.Default) {

//            we can wrap up around a function inside a coroutine with this, and we will cancel the
            //   running of this code if it takes more than 3 seconds, this is the wait method of
            //   the coroutines
            withTimeout(3000.milliseconds){

                repeat(5) {

                    Log.d("MainActivity", "Coroutine is still working")
                    delay(1000.milliseconds)
                }
            }
        }

        Log.d("MainActivity", "Ending calculation by timeout.")


        runBlocking {
//            this will block the main thread until the coroutine is finished
//            job.join()

//            and we can also cancel the job immediately if we want with .cancel
//            delay(2000.milliseconds)
//            job.cancel()
//            logs in kotlin
//            Log.d("MainActivity", "Main thread is continuing")
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