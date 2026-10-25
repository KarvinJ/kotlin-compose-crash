package knight.nameless.compose.room

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import knight.nameless.compose.entities.Person

@Database(
    entities = [Person::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun personDao(): PersonDao

//    How to handle the database in a singleton way
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}
