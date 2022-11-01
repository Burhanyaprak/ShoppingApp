package com.burhanyaprak.shoppingapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    //TODO(move to Singleton pattern)
    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setUsername()

        binding.button.setOnClickListener {
            auth!!.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_viewPagerFragment)
        }
    }

    private fun setUsername() {
        fireStoreDatabase.collection("users")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        if (auth?.currentUser?.uid.toString() == document.data.getValue("userId")
                                .toString()
                        ) {
                            binding.textViewUsername.text =
                                document.data.getValue("username").toString()
                            binding.textViewEmail.text = auth?.currentUser?.email.toString()
                        }
                    }
                }
            }
    }
}