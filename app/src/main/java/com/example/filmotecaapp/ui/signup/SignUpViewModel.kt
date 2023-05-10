package com.example.filmotecaapp.ui.signup

import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.filmotecaapp.R
import com.example.filmotecaapp.domain.model.RegistratoionViewParams
import com.example.filmotecaapp.domain.repository.UserRepository
import com.example.filmotecaapp.util.navigateWithAnimations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val registratoionViewParams = RegistratoionViewParams(username = "", password = "")

    private val _userInsertSuccess = MutableLiveData<Boolean>()
    val userInsertSuccess: LiveData<Boolean> = _userInsertSuccess

    private val _loadingCreateUser = MutableLiveData<Boolean>()
    val loadingCreateUser: LiveData<Boolean> = _loadingCreateUser


    fun createUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                _loadingCreateUser.postValue(true)
                registratoionViewParams.username = username
                registratoionViewParams.password = password

                val userInsertReponse = userRepository.createUser(registratoionViewParams)

                _userInsertSuccess.postValue(userInsertReponse)
            } catch (e: Exception) {
                println(e.message)

                _userInsertSuccess.postValue(false)
            } finally {
                _loadingCreateUser.postValue(false)
            }
        }
    }

    class SignUpViewModelFactory(private val userRepository: UserRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel(userRepository) as T
        }
    }
}