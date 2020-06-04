package com.grigor.nearme.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.grigor.nearme.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        confirmAuthentication()
        val navController = findNavController()
        checkValidation(navController)
        return binding.root
    }

    private fun confirmAuthentication() {
        binding.confirmButton.setOnClickListener {
            viewModel.authenticate(binding.userEmaileditText.text.toString())
        }
    }

    private fun checkValidation(navController: NavController) {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> navController.navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeActivity()
                )
            }
        })
    }
}