package com.burhanyaprak.shoppingapp.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var auth: FirebaseAuth? = null
    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupPasswordRepeat: EditText
    private lateinit var textViewUsername: EditText
    private lateinit var signupButton: Button
    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        signupEmail = binding.editTextSignupEmail
        signupPassword = binding.editTextSignupPassword
        textViewUsername = binding.editTextSignUpUsername
        signupPasswordRepeat = binding.editTextSignupPasswordRepeat
        signupButton = binding.buttonSignUp

        signupButton.setOnClickListener {
            val email = signupEmail.text.toString().trim { it <= ' ' }
            val password = signupPassword.text.toString().trim { it <= ' ' }
            val signupPasswordRepeat = signupPasswordRepeat.text.toString().trim { it <= ' ' }
            if (password != signupPasswordRepeat) {
                Toast.makeText(
                    requireContext(), getString(R.string.passwords_are_not_same), Toast.LENGTH_SHORT
                ).show()
            } else {
                //TODO(refactor)
                createUser(email, password)
            }
        }
    }

    private fun saveUserName(userId: String?) {
        val userName = signupPassword.text.toString().trim { it <= ' ' }
        val user: MutableMap<String, Any> = HashMap()
        user["userId"] = userId.toString()
        user["username"] = userName

        fireStoreDatabase.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("deneme", "basarili {${it.id}}")
            }
            .addOnFailureListener {
                Log.d("deneme", "basarisiz {${it}}")
            }
    }

    //TODO(Check empty statement)
    private fun createUser(user: String, pass: String) {
        auth!!.createUserWithEmailAndPassword(user, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(), getString(R.string.sign_up_Successful), Toast.LENGTH_SHORT
                    ).show()
                    saveUserName(auth?.uid)
                    findNavController().navigate(R.id.action_viewPagerFragment_to_productFragment)
                } else {
                    Toast.makeText(
                        requireContext(), task.exception?.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}