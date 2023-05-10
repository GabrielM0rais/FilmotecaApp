package com.example.filmotecaapp.ui.login

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.filmotecaapp.R
import com.example.filmotecaapp.data.db.AppDatabase
import com.example.filmotecaapp.databinding.FragmentLoginBinding
import com.example.filmotecaapp.domain.datasource.UserDbDataSource
import com.example.filmotecaapp.util.navigateWithAnimations

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels(
        factoryProducer = {
            val database = AppDatabase.getDatabase(requireContext())

            LoginViewModel.LoginViewModelFactory(
                userRepository = UserDbDataSource(database.userDao())
            )
        }
    )

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputLoginPassword.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        initListners()
        initObservers()
    }

    fun initListners() {
        val signUp: LinearLayout = binding.signUpTextContainer
        val signIn: AppCompatButton = binding.buttonLoginSignIn

        signUp.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.signUpFragment)
        }

        signIn.setOnClickListener {
            val username: AppCompatEditText = binding.inputLoginUsername
            val password: AppCompatEditText = binding.inputLoginPassword

            if (username.length() < 1) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Atenção")
                    .setMessage(
                        "Seu nome de usuario não pode ser nulo."
                    )
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
            } else if (password.length() < 6) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Atenção")
                    .setMessage(
                        "Sua senha precisa ter no minimo 6 digitos"
                    )
                    .setPositiveButton("OK") { _, _ -> }
                    .show()


            } else {
                viewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun initObservers() {
        viewModel.loadingLogin.observe(viewLifecycleOwner) {
            binding.buttonLoginSignIn.isEnabled = !it
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {

                parentFragmentManager.setFragmentResult(
                    "GetUser",
                    bundleOf(Pair("User", it))
                )

                findNavController().navigateWithAnimations(R.id.movieListFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}