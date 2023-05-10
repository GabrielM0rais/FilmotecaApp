package com.example.filmotecaapp.ui.welcome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.filmotecaapp.R
import com.example.filmotecaapp.databinding.FragmentSignUpBinding
import com.example.filmotecaapp.databinding.FragmentWelcomeBinding
import com.example.filmotecaapp.ui.MainActivity
import com.example.filmotecaapp.util.navigateWithAnimations

class WelcomeFragment : Fragment() {

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initObservers() {

        binding.buttonLogin.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.loginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}