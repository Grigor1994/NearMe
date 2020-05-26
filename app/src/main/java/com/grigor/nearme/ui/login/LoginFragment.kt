package com.grigor.nearme.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
//        isAuthenticated()
        confirmAuthentication()
        val navController = findNavController()
        checkValidation(navController)
//        showAlert()
        return binding.root
    }

    private fun confirmAuthentication() {
        binding.confirmButton.setOnClickListener {
            viewModel.authenticate(binding.userEmaileditText.text.toString())
        }
    }

//    private fun isAuthenticated() {
//        viewModel.authenticate(getEmail())
//    }

    private fun checkValidation(navController: NavController) {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> navController.navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeActivity()
                )
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

//    fun callActivity() {
//        val intent = Intent(activity, HomeActivity::class.java)
//        startActivity(intent)
//    }

    private fun getEmail(): String = "grigor.avetisyan@hotmail.com"
}