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
import com.medimemo.data.RacikanObat
import com.medimemo.data.Reminder
import com.medimemo.databinding.FragmentHomeBinding
import com.medimemo.databinding.FragmentMedicineBinding
import com.medimemo.ui.fragment.adapter.MedicineAdapter
import com.medimemo.ui.fragment.adapter.ReminderAdapter
import com.medimemo.ui.swipeup.MedicalCheck
import com.medimemo.ui.swipeup.MedicineAdd

class MedicineFragment : Fragment() {
    private lateinit var binding: FragmentMedicineBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var medicineList: ArrayList<RacikanObat>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicineBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        db = FirebaseDatabase
            .getInstance()
            .getReference("Medicine")
            .child(auth.currentUser?.uid.toString())
        medicineList = arrayListOf()
        fetchData()
        binding.apply {
            var x = auth.currentUser?.displayName
            tvNama.text = "Selamat Datang $x"

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
                medicineList.clear()
                if (snapshot.exists()) {
                    for (imtSnap in snapshot.children) {
                        val reminder = imtSnap.getValue(RacikanObat::class.java)
                        medicineList.add(reminder!!)
                    }
                }
                val reminderAdapter = MedicineAdapter(medicineList)
                binding.rvMedicine.adapter = reminderAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showBottomSheet() {
        val bottomSheetFragment = MedicineAdd()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}