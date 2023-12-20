package com.medimemo.ui.swipeup

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.medimemo.R
import com.medimemo.data.MedicalRecord
import com.medimemo.databinding.FragmentMedicalCheckBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar


class MedicalCheck : BottomSheetDialogFragment() {

    private var _binding: FragmentMedicalCheckBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMedicalCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        binding.apply{
            val today = Calendar.getInstance()
            today.add(Calendar.DAY_OF_MONTH, 0)
            val futureDay = today.time
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val formatDate = sdf.format(futureDay)
            edtTglWaktu.setText(formatDate)
            edtTglWaktu.requestFocus()
            tvMemek.text = formatDate

            btnSubmitt.setOnClickListener {
                val id = db.database.getReference("Medical_Check").push().key.toString()
                val anamesis = hasilAnamesis.text.toString()
                val fisik = pemeriksaanFisik.text.toString()
                val diagnosis = diagnosis.text.toString()
                val tindakan = pengobatanTindakan.text.toString()
                val pelayanan = pelayananLain.text.toString()
                val dokter = nmDokter.text.toString()

                val medicalCheck = MedicalRecord(id,formatDate,anamesis,fisik,diagnosis,tindakan, pelayanan, dokter)
                pushData(medicalCheck)

                dismiss()
            }
        }
    }

    private fun pushData(medicalCheck: MedicalRecord) {
        db = FirebaseDatabase.getInstance().getReference("Medical_Check").child(auth.currentUser?.uid.toString()).child(medicalCheck.id)

        Log.d("Firebase", "Attempting to write data to Firebase")

        db.setValue(medicalCheck).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Data successfully written to Firebase")
            } else {
                val errorMessage = task.exception?.message ?: "Unknown error"
                Log.e("Firebase", "Error writing data to Firebase: $errorMessage", task.exception)
            }
        }
    }

}