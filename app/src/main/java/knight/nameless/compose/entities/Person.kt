package knight.nameless.compose.entities

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class Person(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    val lastName: String,
    val phoneNumber: String
)
