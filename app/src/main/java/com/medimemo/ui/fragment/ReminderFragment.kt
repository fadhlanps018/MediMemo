package com.medimemo.ui.fragment

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
import com.medimemo.R
import com.medimemo.data.Reminder
import com.medimemo.data.Vacsine
import com.medimemo.databinding.FragmentHomeBinding
import com.medimemo.databinding.FragmentReminderBinding
import com.medimemo.ui.fragment.adapter.ReminderAdapter
import com.medimemo.ui.fragment.adapter.VacsineAdapter
import com.medimemo.ui.swipeup.MedicalCheck
import com.medimemo.ui.swipeup.ReminderAdd


class ReminderFragment : Fragment() {
    private lateinit var binding: FragmentReminderBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var reminderList: ArrayList<Reminder>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReminderBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        db = FirebaseDatabase
            .getInstance()
            .getReference("Reminder")
            .child(auth.currentUser?.uid.toString())
        reminderList = arrayListOf()
        fetchData()

        binding.apply {
            var x = auth.currentUser?.displayName
            textView.text = "Selamat Datang $x"

            recyclerView.apply {
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
                reminderList.clear()
                if (snapshot.exists()) {
                    for (imtSnap in snapshot.children) {
                        val reminder = imtSnap.getValue(Reminder::class.java)
                        reminderList.add(reminder!!)
                    }
                }
                val reminderAdapter = ReminderAdapter(reminderList)
                binding.recyclerView.adapter = reminderAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showBottomSheet() {
        val bottomSheetFragment = ReminderAdd()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}