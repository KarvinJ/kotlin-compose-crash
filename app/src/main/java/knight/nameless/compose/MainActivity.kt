package knight.nameless.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.engine.okhttp.OkHttp
import knight.nameless.compose.composables.PersonDialog
import knight.nameless.compose.entities.Person
import knight.nameless.compose.ui.theme.ComposeTheme
import knight.nameless.compose.composables.NetworkCall
import knight.nameless.compose.composables.PersonList
import knight.nameless.compose.composables.PersonListRoom
import knight.nameless.compose.networking.UserClient
import knight.nameless.compose.networking.createHttpClient
import knight.nameless.compose.room.AppDatabase
import knight.nameless.compose.room.PersonRepository
import knight.nameless.compose.room.toEntity

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

        val repository = PersonRepository(AppDatabase.getDatabase(this).personDao())

        val viewModel = ViewModelProvider(
            this,
            PersonViewModelFactory(repository)
        )[PersonViewModel::class.java]

        val personsDb by viewModel.persons.collectAsState()

        val client = remember {
            UserClient(createHttpClient(OkHttp.create()))
        }

        var showCreateDialog by remember { mutableStateOf(false) }
        var showPersonDialog by remember { mutableStateOf(false) }

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

                    if (selectedScreen == 1 || selectedScreen == 2) {

                        FloatingActionButton(
                            onClick = {
                                personToEdit = null
                                showCreateDialog = true
                                showPersonDialog = true
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
                                        personToEdit = personToEdit,
                                        onDismiss = {
                                            showPersonDialog = false
                                        },
                                        onAdd = { person ->
                                            // Convert Person to PersonModel if necessary
                                            viewModel.upsertPerson(person.toPersonModel())
                                            showPersonDialog = false
                                        },
                                        onEdit = { person ->
                                            // Convert Person to PersonModel if necessary
                                            viewModel.upsertPerson(person.toPersonModel())
                                            showPersonDialog = false
                                        }
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

//                            Code for handling the crud with the room database
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {

                                if (showPersonDialog) {

                                    PersonDialog(
                                        onAdd = { person ->
                                            viewModel.upsertPerson(person.toPersonModel())
                                        },
                                        onDismiss = {
                                            showPersonDialog = false
                                        },
                                        onEdit = { person ->

                                            showPersonDialog = false
                                            viewModel.upsertPerson(person.toPersonModel())
                                        },
                                        personToEdit = personToEdit
                                    )
                                }

                                PersonListRoom(
                                    persons = personsDb,
                                    onDelete = { personToRemove ->

                                    },
                                    onEdit = { person ->

                                        showPersonDialog = true
                                        personToEdit = person.toEntity()
                                    }
                                )
                            }

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