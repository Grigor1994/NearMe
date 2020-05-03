package com.grigor.nearme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.grigor.nearme.databinding.FragmentMainBinding
import com.grigor.nearme.viewmodel.LoginViewModel
import java.lang.Exception

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val navController = findNavController()

        isAuthenticated()
        checkAuthenticationState(navController)

        return binding.root
    }

    private fun isAuthenticated() {
        viewModel.authenticate(getEmail())
    }

    private fun checkAuthenticationState(navController: NavController) {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED ->
                    navController.navigate(
                        MainFragmentDirections.actionMainFragmentToHomeActivity()
                    )
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(
                    MainFragmentDirections.actionMainFragmentToLoginFragment()
                )
            }
        })
    }

//    fun callActivity() {
//        val intent = Intent(activity, HomeActivity::class.java)
//        startActivity(intent)
//    }

    private fun getEmail(): String = "grigor.avetisyan@hotmail.com"

}
