package com.grigor.nearme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grigor.nearme.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


//    private fun showAlert() {
//        var alert = AlertDialog.Builder(requireActivity())
//        alert.setMessage("Hello")
//        alert.setTitle("Enter the otp sent to you")
//        alert.show()
//    }


}
