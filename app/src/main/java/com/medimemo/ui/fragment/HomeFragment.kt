package com.medimemo.ui.fragment

import android.media.MediaCodecInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.medimemo.data.MedicalRecord
import com.medimemo.data.RacikanObat
import com.medimemo.data.Reminder
import com.medimemo.databinding.FragmentHomeBinding
import com.medimemo.ui.fragment.adapter.HomeAdapter
import com.medimemo.ui.fragment.adapter.ReminderAdapter
import com.medimemo.ui.swipeup.MedicalCheck


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var medicalList: ArrayList<MedicalRecord>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        db = FirebaseDatabase
            .getInstance()
            .getReference("Medical_Reminder")
            .child(auth.currentUser?.uid.toString())

        fetchData()

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

    private fun fetchData() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medicalList.clear()
                if (snapshot.exists()) {
                    for (imtSnap in snapshot.children) {
                        val reminder = imtSnap.getValue(MedicalRecord::class.java)
                        medicalList.add(reminder!!)
                    }
                }
                val reminderAdapter = HomeAdapter(medicalList)
                binding.rvMedicine.adapter = reminderAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showBottomSheet() {
        val bottomSheetFragment = MedicalCheck()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }




}