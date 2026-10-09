package knight.nameless.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import knight.nameless.compose.ui.theme.ComposeTheme
import org.w3c.dom.NameList

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ComposeTheme {

                Final()
//                Greeting("Test")
//               ButtonUpdateState()
            }
        }
    }

    @Composable
    fun Final() {

        var name by remember {

            mutableStateOf("")
        }

//        State of list
        var names by remember {

            mutableStateOf(listOf<String>())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Row(

                modifier = Modifier.fillMaxWidth()
            ) {
//                We can use BasicTextField and outlinedTextfield for inputs
                OutlinedTextField(
                    value = name,// the value is the text that is currently entered in the textfield
                    onValueChange = { text ->

                        //Everytime the value of the textfield changes this function is called
                        name = text

                    },
                )

                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            names = names + name
                            name = ""
                        }
                    }
                )  {
                    Text(
                        text = "Add"
                    )
                }
            }

            //    This is the main advantage of using compose you can take any ui element and
            //    convert it in a composable function and use it in any other ui
            NameList(names = names)
        }
    }

    @Composable
    fun NameList(names: List<String>, modifier: Modifier = Modifier) {

        LazyColumn(modifier) {
            items(names) { currentName ->

                Text(
                    text = currentName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                HorizontalDivider()
            }
        }
    }

    @Composable
    fun ButtonUpdateState() {

        var count by remember {
            mutableIntStateOf(0)
        } //this is how we can use the state of a composable, we use remember to avoid that
        // when the ui re-compose the value is not lost

//                Greeting(name = "Test")

//                with a column with this value with center everything inside of this column in
        //                the middle of the screen
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(), // every time this value changes the text will be updated,
//                        Just thus element the other ones will not be updated
                fontSize = 30.sp,
            )
            Button(onClick = { // lugar donde definimos el comportamiento del boton

                count++
            }) {
                Text(text = "Click me $count")
            }
        }

    }

    //A composable is any kotlin function that will be using in compose code
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {


//        Let's work with lists. This is the way that we need to use to implement the equivalent
        //        to recyclerView and is the correct way to render multiple elements in the screen
//        cuz in this way the only thing that is consuming resources are the elements that the user
        //        is currently looking the other elements don't use resources
//        And for this we use LazyColumn

//        is almost the same logic as the column and the for, but here we replace this with a lazy column
//        The same way that we have a lazy column we also have a lazy row that works the same
        LazyColumn(modifier = Modifier.fillMaxSize()) {

//            and an items property and with this we indicate the quantity of times that we want
            //            to display the element inside the items
            items(10) { i ->

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.background(Color.Black)
                )
            }
        }


//        This is how we load and draw icons in compose
//        Icon(imageVector = Icons.Default.Add, contentDescription = null) Icons module not found

//        this is how we load and draw images in compose

//        we can also hide ui elements with simple conditionals,
        if (name.length < 5) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.background(Color.Black)
            )
        }

//        we can also use loops in combination with a column for showing the same element several times
//        Column{
//
//            for (i in 1..5) {
//
//                Image(
//                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                    contentDescription = null,
//                    modifier = Modifier.background(Color.Black)
//                )
//            }
//        }

//        The row and the column are the more efficient way to arrange items in a screen,
        // This is the equivalent to a frame layout this is for arrange items horizontally,
        // this interchange the arrangement and alignment vs the column, and apply most of the code use in the column
//        Row(
////            horizontalArrangement = Arrangement.End,
////            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.size(400.dp),
//        ) {
//
//            Text(
//                text = "Hello $name!",
//                modifier = modifier.align(Alignment.CenterVertically),
//                Color.Red,
//                fontSize = 22.sp
//            )
//
//            Text(
//                text = "Test7",
//                modifier = modifier,
//                Color.Blue,
//                fontSize = 22.sp
//            )
//        }

//        //This is the equivalent of linear layout of XML, but in compose, this is for arrange items vertically
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally, // This will align the items
//            // horizontally in the center, we can ubicate this to the start and end for left to right
//            verticalArrangement = Arrangement.Center, // This will arrange the items vertically in the
//            // center, we can ubicate this to the top and bottom of the screen
//            modifier = Modifier.size(400.dp) // with this the content of the column will fill the
//        // entire size of the screen, we can also do that for use the max width and height of the screen,
////            and also to give ourselves the size custom
//        ) {
//
//            Text(
//                text = "Hello $name!",
////                modifier = modifier.fillMaxSize(), // this will fill the entire size of the column
////                Given that this text has as a parent the column with a size of 400 dp, this text
////                is filling the max size of this 400 dp column
//                modifier = modifier, // this will fill the entire size of the column
//                Color.Red,
//                fontSize = 22.sp
//            )
//
//            Text(
//                text = "Test7",
//                modifier = modifier,
//                Color.Blue,
//                fontSize = 22.sp
//            )
//        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ComposeTheme {
            Final()
//            Greeting("Test6")
        }
    }
}