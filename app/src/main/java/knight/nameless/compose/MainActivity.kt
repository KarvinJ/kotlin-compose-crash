package knight.nameless.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.client.engine.okhttp.OkHttp
import knight.nameless.compose.composables.PersonDialog
import knight.nameless.compose.entities.Person
import knight.nameless.compose.ui.theme.ComposeTheme
import knight.nameless.compose.composables.NetworkCall
import knight.nameless.compose.networking.UserClient
import knight.nameless.compose.networking.createHttpClient

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ComposeTheme {

                App()

//                Final()
//                Greeting("Test")
//               ButtonUpdateState()
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun App() {

        val client = remember {
            UserClient(createHttpClient(OkHttp.create()))
        }

        var showCreateDialog by remember { mutableStateOf(false) }

        var persons by remember {

            mutableStateOf(listOf<Person>())
        }

        var personToEdit by remember {
            mutableStateOf<Person?>(null)
        }

        var selectedScreen by remember { mutableIntStateOf(0) }

//    The MaterialTheme and scaffold They have different responsibilities:
//- MaterialTheme defines the visual system: colors, typography, and shapes.
//- Scaffold provides the structure for a screen: top app bar, content, floating action button,
// bottom bar, and snack bar

        MaterialTheme {

//        Scaffold give us a standard structure on how to design the ui of our app,
//        And also give us several components to add in our app. like floating buttons, top app
            //        bar and bottom app bar.
            Scaffold(

                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text("Registration Form")
                        }
                    )
                },

                floatingActionButton = {

                    if (selectedScreen == 1) {

                        FloatingActionButton(
                            onClick = {
                                personToEdit = null
                                showCreateDialog = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add person"
                            )
                        }
                    }
                },

//            adding the bottom bar
                bottomBar = {

//                we normally add the navigation bar here in the bottom bar.
                    NavigationBar {

                        NavigationBarItem(
                            selected = selectedScreen == 0,
                            onClick = { selectedScreen = 0 },
                            icon = { Icon(Icons.Default.Person, null) },
                            label = { Text("Register") }
                        )

                        NavigationBarItem(
                            selected = selectedScreen == 1,
                            onClick = { selectedScreen = 1 },
                            icon = { Icon(Icons.Default.People, null) },
                            label = { Text("People") }
                        )

                        NavigationBarItem(
                            selected = selectedScreen == 2,
                            onClick = { selectedScreen = 2 },
                            icon = { Icon(Icons.Default.Accessibility, null) },
                            label = { Text("People") }
                        )
                    }
                }

            ) { innerPadding ->

                Box(Modifier.padding(innerPadding)) {

                    when (selectedScreen) {
                        0 -> NetworkCall(client)
                        1 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {

                                if (showCreateDialog) {

                                    PersonDialog(
                                        onAdd = { person ->
                                            persons = persons + person
                                        },
                                        onDismiss = {
                                            showCreateDialog = false
                                        },
                                        onEdit = { person ->

                                            showCreateDialog = false
//     This code updates a person in your persons list by matching their id.
//     It's useful for implementing the Edit operation in your CRUD app.
                                            persons = persons.map {

                                                if (it.id == person.id) {
                                                    person
                                                } else {
                                                    it
                                                }
                                            }
                                        },
                                        personToEdit = personToEdit
                                    )
                                }

                                PersonList(
                                    persons = persons,
//            De esta forma enviamos funciones por parametro
                                    onDelete = { personToRemove ->
                                        persons = persons.filter { it != personToRemove }
                                    },
                                    onEdit = { person ->

                                        showCreateDialog = true
                                        personToEdit = person
                                    }
                                )
                            }
                        }
                        2 -> {

                        }
                    }
                }
            }
        }
    }

    @Composable
    fun dbRoomTest() {


    }

    @Composable
    fun PersonList(
        persons: List<Person>,
        onEdit: (Person) -> Unit,
        onDelete: (Person) -> Unit,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(persons) { person ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${person.name} ${person.lastName}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = person.phoneNumber,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = { onEdit(person) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit person",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(
                            onClick = { onDelete(person) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete person",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ComposeTheme {

            App()
//            Final()
//            Greeting("Test6")

        }
    }
}