package knight.nameless.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import knight.nameless.compose.domain.PersonModel
import knight.nameless.compose.room.PersonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonDetailViewModel(
    private val personRepository: PersonRepository,
    personId: Int
) : ViewModel() {

    val person: StateFlow<PersonModel?> = personRepository.getById(personId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null
        )

    fun delete(onDelete: () -> Unit) {

        val person = person.value ?: return

        viewModelScope.launch {
            personRepository.delete(person)
            onDelete()
        }
    }

}