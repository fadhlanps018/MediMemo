package com.medimemo.ui.swipeup

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.medimemo.data.Reminder
import com.medimemo.databinding.FragmentReminderAddBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReminderAdd : BottomSheetDialogFragment() {
    private var _binding: FragmentReminderAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var selectedDateTime: Long = 0
    private var selectedDate = Calendar.getInstance()
    private var selectedTime = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReminderAddBinding.inflate(inflater, container, false)
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
            btnWaktu.setOnClickListener {
                showTimePicker()
            }
            btnSubmitReminder.setOnClickListener {
                val id = db.database.getReference("Reminder").push().key.toString()
                val location = edtLocation.text.toString()
                val color = edtColor.text.toString()
                val title = edtPengingat.text.toString()
                // Format selectedDate and selectedTime to strings
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val date = dateFormat.format(selectedDate.time)
                val time = timeFormat.format(selectedTime.time)
                val reminder = Reminder(id,title,date, time,location,color)

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

    private fun showTimePicker() {
        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                updateDateAndTimeViews()
            },
            selectedTime.get(Calendar.HOUR_OF_DAY),
            selectedTime.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }

    private fun updateDateAndTimeViews() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        binding.tvTanggal.text = dateFormat.format(selectedDate.time)
        binding.tvWaktu.text = timeFormat.format(selectedTime.time)
    }

    private fun pushData(reminder: Reminder) {
       db = FirebaseDatabase.getInstance().getReference("Reminder").child(auth.currentUser?.uid.toString()).child(reminder.id)

        Log.d("Firebase", "Attempting to write data to Firebase")

        db.setValue(reminder).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Data successfully written to Firebase")
            } else {
                val errorMessage = task.exception?.message ?: "Unknown error"
                Log.e("Firebase", "Error writing data to Firebase: $errorMessage", task.exception)
            }
        }
    }
}