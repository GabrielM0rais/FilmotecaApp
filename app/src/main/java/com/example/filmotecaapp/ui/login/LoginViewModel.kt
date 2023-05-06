package com.example.filmotecaapp.ui.login

import androidx.lifecycle.*
import com.example.filmotecaapp.data.model.User
import com.example.filmotecaapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _loadingLogin = MutableLiveData<Boolean>()
    val loadingLogin: LiveData<Boolean> = _loadingLogin

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _loadingLogin.postValue(true)

                val user = userRepository.login(username, password)

                if (user == null) {
                    throw Exception("User not found")

                }

                _user.postValue(user)
            } catch (e: Exception) {
                println(e.message)
            } finally {
                _loadingLogin.postValue(false)
            }
        }
    }

    class LoginViewModelFactory(private val userRepository: UserRepository):
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(userRepository) as T
        }
    }
}
