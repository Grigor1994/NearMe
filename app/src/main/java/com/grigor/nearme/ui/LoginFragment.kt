package com.grigor.nearme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.grigor.nearme.databinding.FragmentLoginBinding
import com.grigor.nearme.viewmodel.LoginViewModel
import com.grigor.nearme.viewmodel.LoginViewModel.AuthenticationState.*


class LoginFragment : DialogFragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viwModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        viwModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        isAuthenticated()
        confirmAuthentication()
        val navController = findNavController()

        checkValidation(navController)

//        showAlert()
        return binding.root
    }

    private fun confirmAuthentication() {
        binding.confirmButton.setOnClickListener {
            viwModel.authenticate(binding.userEmaileditText.text.toString())
        }
    }

    private fun isAuthenticated() {
        viwModel.authenticate(getEmail())
    }

    private fun checkValidation(navController: NavController) {
        viwModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                AUTHENTICATED -> navController.navigate(LoginFragmentDirections.actionLoginFragmentToHomeNavigation())
                INVALID_AUTHENTICATION -> showErrorMessage()
                else -> {
                    UNAUTHENTICATED
                }
            }
        })
    } /*    }*/

    fun showErrorMessage() {
        var alert =
            AlertDialog.Builder(requireActivity()); alert.setMessage("Incorrect email!"); alert.show()
    }

//    private fun showAlert() {
//        var alert = AlertDialog.Builder(requireActivity());
//        val userEmail =
//            EditText(activity); alert.setTitle("Enter your email address"); alert.setView(userEmail); alert.setPositiveButton(
//            "Confirm"
//        ) { dialog, whichButton -> viwModel.authenticate(userEmail.text.toString()) }; alert.show()
//    }

    private fun getEmail(): String = "grigor.avetisyan@hotmail.com"
}