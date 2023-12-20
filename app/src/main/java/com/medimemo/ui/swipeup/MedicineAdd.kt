package com.medimemo.ui.swipeup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.medimemo.data.RacikanObat
import com.medimemo.databinding.FragmentMedicineAddBinding


class MedicineAdd : BottomSheetDialogFragment() {
    private var _binding: FragmentMedicineAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMedicineAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        binding.apply{

            btnSubmitMedicine.setOnClickListener {
                val id = db.database.getReference("Medicine").push().key.toString()
                val obat1 = edtSaranObat.text.toString()
                val obat2 = edtSaranObat2.text.toString()
                val obat3 = edtSaranObat3.text.toString()
                val sakit = edtPenyakitDiderita.text.toString()

                val medicine = RacikanObat(id,sakit,obat1, obat2, obat3)
                pushData(medicine)
                dismiss()
            }
        }
    }


    private fun pushData(medicine: RacikanObat) {
        db = FirebaseDatabase.getInstance().getReference("Medicine").child(auth.currentUser?.uid.toString()).child(medicine.id)

        Log.d("Firebase", "Attempting to write data to Firebase")

        db.setValue(medicine).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Data successfully written to Firebase")
            } else {
                val errorMessage = task.exception?.message ?: "Unknown error"
                Log.e("Firebase", "Error writing data to Firebase: $errorMessage", task.exception)
            }
        }
    }
}