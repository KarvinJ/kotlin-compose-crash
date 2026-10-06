package knight.nameless.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds

class CoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun coroutinesScope() {

//        The globalScope coroutine lives as long as the application does,
        //        so is a bad practice to always use this cuz rarely we need our coroutines to lives as
        //        long as the application

//        What we need to use instead is the lifecycleScope coroutine, that lives as long as our
        //        activities lives, it sticks to the lifecycle of the activity. And we can do everything
        //        that we did on GlobalScope
        lifecycleScope.launch {


        }
    }


    //    Async/Await calls
    private fun networkCalls() {

//        we can also make the GlobalScope async if we need it.
        GlobalScope.launch(Dispatchers.IO) {

//          This is how we can measure time of a block of code on kotlin
            val time = measureTimeMillis {

//                if we handle this calls like this it will take 6 seconds, cuz we are running
                //  this in a synchronous way
//                val net1 = networkCall1()
//                val net2 = networkCall2()
//
//                Log.d("MainActivity", net1)
//                Log.d("MainActivity", net2)

//            now this is how we handle the network calls asynchronously with the async coroutine
//                And with this code should take only 3 seconds to complete.
                val net1 = async { networkCall1() }
                val net2 = async { networkCall2() }

//                we need to add the await method to get the value of the async coroutine
                Log.d("MainActivity", net1.await())
                Log.d("MainActivity", net2.await())
            }

            Log.d("MainActivity", "Requests took $time ms.")
        }

    }

    private suspend fun networkCall1(): String {

        delay(3000.milliseconds)
        return "call 1"
    }

    private suspend fun networkCall2(): String {

        delay(3000.milliseconds)
        return "call 2"
    }

    private fun coroutines() {
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
                runBlocking {
        //we can also add other coroutines inside this block, that will run async without blocking the main thread
                    launch {
                        delay(100.milliseconds)
                    }

        //            this will delay the main thread for 100 milliseconds, and will feel this delay in the ui
                    delay(100.milliseconds)
                }

        //       this code works the same as a delay inside the runBlocking coroutine, in case I want to.
        //        Thread.sleep(100)

        //        every coroutine will return a job, that can be used to cancel the coroutine
        //        or wait for the coroutine to finish
        val job = GlobalScope.launch(Dispatchers.Default) {

            //            we can wrap up around a function inside a coroutine with this, and we will cancel the
            //   running of this code if it takes more than 3 seconds, this is the wait method of
            //   the coroutines
            withTimeout(3000.milliseconds) {

                repeat(5) {

                    Log.d("MainActivity", "Coroutine is still working")
                    delay(1000.milliseconds)
                }
            }
        }

        Log.d("MainActivity", "Ending calculation by timeout.")


        runBlocking {
            //            this will block the main thread until the coroutine is finished
                        job.join()

            //            and we can also cancel the job immediately if we want with .cancel
//                        delay(2000.milliseconds)
//                        job.cancel()
            //            logs in kotlin
                        Log.d("MainActivity", "Main thread is continuing")
        }
    }
}
