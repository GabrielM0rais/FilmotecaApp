package com.example.filmotecaapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.filmotecaapp.R
import com.example.filmotecaapp.databinding.FragmentLoginBinding
import com.example.filmotecaapp.util.navigateWithAnimations

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListners()
    }

    fun initListners() {
        val signUp: LinearLayout = binding.signUpTextContainer
        val signIn: AppCompatButton = binding.buttonLoginSignIn

        signUp.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.signUpFragment)
        }

        signIn.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.movieListFragment)
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