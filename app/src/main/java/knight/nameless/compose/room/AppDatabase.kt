package knight.nameless.compose.room

import androidx.room3.Database
import androidx.room3.RoomDatabase
import knight.nameless.compose.entities.Person

@Database(
    entities = [Person::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun personDao(): PersonDao
}