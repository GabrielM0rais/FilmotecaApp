package com.example.filmotecaapp.ui.signup

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.filmotecaapp.R
import com.example.filmotecaapp.data.db.AppDatabase
import com.example.filmotecaapp.databinding.FragmentSignUpBinding
import com.example.filmotecaapp.domain.datasource.UserDbDataSource
import com.example.filmotecaapp.ui.MainActivity
import com.example.filmotecaapp.util.navigateWithAnimations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels(
        factoryProducer = {
            val database = AppDatabase.getDatabase(requireContext())

            SignUpViewModel.SignUpViewModelFactory(
                userRepository = UserDbDataSource(database.userDao())
            )
        }
    )

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputSingUpPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        initListners()
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initListners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonSignUp.setOnClickListener {
            val username: AppCompatEditText = binding.inputSignUpUsername
            val password: AppCompatEditText = binding.inputSingUpPassword

            viewModel.createUser(username.text.toString(), password.text.toString())
        }
    }

    private fun initObservers() {
        viewModel.loadingCreateUser.observe(viewLifecycleOwner) {
            binding.buttonSignUp.isEnabled = !it
        }

        binding.buttonSignUp.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.welcomeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).supportActionBar?.hide()

        _binding = null
    }

}