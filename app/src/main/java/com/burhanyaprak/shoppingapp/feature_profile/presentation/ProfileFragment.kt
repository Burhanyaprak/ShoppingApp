package com.burhanyaprak.shoppingapp.feature_profile.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
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
            askLogOut()
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
    override fun onResume() {
        super.onResume()
        val toolbar = requireActivity().findViewById<View>(R.id.my_toolbar) as Toolbar
        toolbar.visibility = View.VISIBLE
        toolbar.title = "Profile"

        val buttonBasket = requireActivity().findViewById<View>(R.id.buttonBasket) as Button
        buttonBasket.visibility = View.VISIBLE
    }
    private fun askLogOut() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(requireContext().getString(R.string.alert_dialog_positive_button)) { _, _ ->
            Toast.makeText(
                requireContext(),
                getString(R.string.log_out_successfully),
                Toast.LENGTH_LONG
            ).show()
            auth!!.signOut()
            navigateProductPage()
        }
        builder.setNegativeButton(requireContext().getString(R.string.alert_dialog_negative_button)) { _, _ -> }
        builder.setMessage(requireContext().getString(R.string.ask_log_out))
        builder.create().show()
    }

    private fun navigateProductPage() {
        findNavController().navigate(R.id.action_profileFragment_to_viewPagerFragment)
    }
}