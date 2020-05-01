package com.grigor.nearme.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.grigor.nearme.HomeActivity
import com.grigor.nearme.databinding.FragmentMainBinding
import com.grigor.nearme.viewmodel.LoginViewModel

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val navController = findNavController()

        checkAuthenticationState(navController)

        return binding.root
    }

    private fun checkAuthenticationState(navController: NavController) {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> callActivity()
//                    navController.navigate(
//                    MainFragmentDirections.actionMainFragmentToHomeNavigation()
//                )
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(
                    MainFragmentDirections.actionMainFragmentToLoginFragment()
                )
            }
        })
    }

    fun callActivity() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }
}
