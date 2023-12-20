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
import com.medimemo.data.Vacsine
import com.medimemo.databinding.FragmentReminderBinding
import com.medimemo.databinding.FragmentVacsineBinding
import com.medimemo.ui.fragment.adapter.VacsineAdapter
import com.medimemo.ui.swipeup.ReminderAdd
import com.medimemo.ui.swipeup.VacsineAdd


class VacsineFragment : Fragment() {
    private lateinit var binding: FragmentVacsineBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var vacsineList: ArrayList<Vacsine>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVacsineBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        db = FirebaseDatabase
            .getInstance()
            .getReference("Vacsine")
            .child(auth.currentUser?.uid.toString())
        vacsineList = arrayListOf()
        fetchData()

        showBottomSheet()

        binding.apply {
            var x = auth.currentUser?.displayName
            textView.text = "Selamat Datang $x"

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this.context)
            }
        }


        return binding.root
    }

    private fun fetchData() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                vacsineList.clear()
                if (snapshot.exists()) {
                    for (imtSnap in snapshot.children) {
                        val vacsines = imtSnap.getValue(Vacsine::class.java)
                        vacsineList.add(vacsines!!)
                    }
                }
                val vacsinesAdapter = VacsineAdapter(vacsineList)
                binding.recyclerView.adapter = vacsinesAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showBottomSheet() {
        val bottomSheetFragment = VacsineAdd()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}