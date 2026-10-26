package knight.nameless.compose.data

import knight.nameless.compose.data.dao.PersonDao
import knight.nameless.compose.domain.PersonModel
import knight.nameless.compose.data.entities.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//class in charge of handling the connection of the database with the app code.
data class PersonRepository(
    private val personDao: PersonDao
) {

    fun getAll(): Flow<List<PersonModel>> {

        val personModels = personDao.getAll().map { persons ->
            persons.map { it.toDomain() }
        }

        return personModels
    }

    fun getById(id: Int): Flow<PersonModel?> {

//        Map the person to person model, cuz this is a flow with need to use the flow map.
        val personModel = personDao.getById(id).map {
            it?.toDomain()
        }

        return personModel
    }

    suspend fun upsert(person: PersonModel) {
        personDao.upsert(person.toEntity())
    }

    suspend fun delete(person: PersonModel) {
        personDao.delete(person.toEntity())
    }
}

//Functions to convert from domain to entity and vice versa.
fun Person.toDomain() = PersonModel(id, name, lastName, phoneNumber)

fun PersonModel.toEntity() = Person(id, name, lastName, phoneNumber)