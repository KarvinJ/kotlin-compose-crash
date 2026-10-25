package knight.nameless.compose.domain

data class PersonModel (
    var id: Int = 0,
    val name: String,
    val lastName: String,
    val phoneNumber: String
)