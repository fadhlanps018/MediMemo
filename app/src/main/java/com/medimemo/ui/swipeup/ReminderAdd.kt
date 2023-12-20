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
import com.medimemo.data.ReminderReceiver
import com.medimemo.databinding.FragmentReminderAddBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReminderAdd : BottomSheetDialogFragment() {
    private var _binding: FragmentReminderAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var alarmReceiver: ReminderReceiver
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var selectedDateTime: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReminderAddBinding.inflate(inflater, container, false)
        alarmReceiver = ReminderReceiver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val x = this.requireContext()
        binding.apply{
            btnTanggal.setOnClickListener {

                showDateTimePicker(){ date, time ->
                    val title = edtPengingat.text.toString()
                    alarmReceiver.setOneTimeAlarm(x, ReminderReceiver.TYPE_ONE_TIME,date,time,title)
                }


            }
            btnSubmitReminder.setOnClickListener {
                val id = db.database.getReference("Reminder").push().key.toString()


                //pushData(reminder)
                setReminder()
                dismiss()
            }
        }
    }

    private fun showDateTimePicker(callback: (date: String, time: String) -> Unit) {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        calendar.set(year, month, day, hourOfDay, minute)
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                        val date = dateFormat.format(calendar.time)
                        val time = timeFormat.format(calendar.time)

                        callback.invoke(date, time)
                    },
                    hour,
                    minute,
                    false
                )
                timePickerDialog.show()
            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }



    private fun setReminder() {
        if (selectedDateTime == 0L) {
            // Handle case where date and time are not selected
            return
        }

        binding.apply {
            val title = edtPengingat.text.toString()
            val location = edtLocation.text.toString()

            val intent = Intent(requireContext(), ReminderReceiver::class.java)
            intent.putExtra("title", title)
            intent.putExtra("location", location)

            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, selectedDateTime, pendingIntent)
        }
    }


    private fun pushData(reminder: Reminder) {
       // db = FirebaseDatabase.getInstance().getReference("Medicine").child(auth.currentUser?.uid.toString()).child(medicine.id)

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