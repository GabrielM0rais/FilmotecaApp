package com.example.filmotecaapp.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.filmotecaapp.R
import com.example.filmotecaapp.ui.MainActivity
import com.example.filmotecaapp.util.navigateWithAnimations

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.hide()

        Handler().postDelayed({

            findNavController().navigate(R.id.loginFragment)
            (requireActivity() as MainActivity).supportActionBar?.show()
        }, 3000)
    }
}
