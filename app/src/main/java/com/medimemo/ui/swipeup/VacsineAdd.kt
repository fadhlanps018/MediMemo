package com.medimemo.ui.swipeup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.medimemo.data.Vacsine
import com.medimemo.databinding.FragmentReminderAddBinding
import com.medimemo.databinding.FragmentVacsineAddBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class VacsineAdd : BottomSheetDialogFragment() {
    private var _binding: FragmentVacsineAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var selectedDate = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVacsineAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val x = this.requireContext()
        binding.apply{
            btnTanggal.setOnClickListener {
                showDatePicker()
            }
            btnSubmitVacsine.setOnClickListener {
                val id = db.database.getReference("Vacsine").push().key.toString()
                // Format selectedDate and selectedTime to strings
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = dateFormat.format(selectedDate.time)
                val location = edtLocation.text.toString()
                val reminder = Vacsine(id,date,location)

                pushData(reminder)

                dismiss()
            }
        }
    }


    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                updateDateAndTimeViews()
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }



    private fun updateDateAndTimeViews() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        binding.tvTanggal.text = dateFormat.format(selectedDate.time)
    }

    private fun pushData(vacsine: Vacsine) {
        db = FirebaseDatabase.getInstance().getReference("Vacsine").child(auth.currentUser?.uid.toString()).child(vacsine.id)

        Log.d("Firebase", "Attempting to write data to Firebase")

        db.setValue(vacsine).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Data successfully written to Firebase")
            } else {
                val errorMessage = task.exception?.message ?: "Unknown error"
                Log.e("Firebase", "Error writing data to Firebase: $errorMessage", task.exception)
            }
        }
    }
}