package com.burhanyaprak.shoppingapp.feature_authentication

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //TODO(Move to singleton pattern)
    private var auth: FirebaseAuth? = null
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        loginEmail = binding.editTextLoginEmail
        loginPassword = binding.editTextLoginPassword
        loginButton = binding.buttonLogin

        loginButton.setOnClickListener {
            val email = loginEmail.text.toString()
            val pass = loginPassword.text.toString()

            checkLoginProcess(email, pass)
        }
    }

    private fun checkLoginProcess(email: String, pass: String) {
        //TODO(refactor)
        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (pass.isNotEmpty()) {
                auth!!.signInWithEmailAndPassword(email, pass)
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.login_successful),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_viewPagerFragment_to_productFragment)
                    }.addOnFailureListener {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.login_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                loginPassword.error = getString(R.string.password_cannot_be_empty)
            }
        } else if (email.isEmpty()) {
            loginEmail.error = getString(R.string.email_cannot_be_empty)
        } else {
            loginEmail.error = getString(R.string.please_enter_valid_email)
        }
    }
}