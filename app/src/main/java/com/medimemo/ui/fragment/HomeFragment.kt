package com.medimemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.medimemo.databinding.FragmentHomeBinding
import com.medimemo.ui.swipeup.MedicalCheck


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        db = FirebaseDatabase
            .getInstance()
            .getReference("Medicine_Reminder")
            .child(auth.currentUser?.uid.toString())

        binding.apply {
            var x = auth.currentUser?.displayName
            textView.text = "Selamat Datang $x"

            rvMedicine.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this.context)
            }
        }


        showBottomSheet()

        return binding.root
    }

    private fun showBottomSheet() {
        val bottomSheetFragment = MedicalCheck()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }




}