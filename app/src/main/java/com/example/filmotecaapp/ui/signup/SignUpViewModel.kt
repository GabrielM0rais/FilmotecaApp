package com.example.filmotecaapp.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.filmotecaapp.domain.model.RegistratoionViewParams
import com.example.filmotecaapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val registratoionViewParams = RegistratoionViewParams(username = "", password = "")

    fun createUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                registratoionViewParams.username = username
                registratoionViewParams.password = password

                val teste = userRepository.createUser(registratoionViewParams)

                println("teste -> $teste")
            }
            catch (e: Exception) {
                println(e.message)
            }

        }
    }

    class SignUpViewModelFactory(private val userRepository: UserRepository):
            ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T  {
        return SignUpViewModel(userRepository) as T
        }
    }
}