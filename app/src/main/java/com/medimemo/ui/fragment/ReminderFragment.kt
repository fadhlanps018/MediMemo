package com.medimemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medimemo.R
import com.medimemo.databinding.FragmentHomeBinding
import com.medimemo.databinding.FragmentReminderBinding
import com.medimemo.ui.swipeup.MedicalCheck
import com.medimemo.ui.swipeup.ReminderAdd


class ReminderFragment : Fragment() {
    private lateinit var binding: FragmentReminderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReminderBinding.inflate(inflater, container, false)

        showBottomSheet()

        return binding.root
    }

    private fun showBottomSheet() {
        val bottomSheetFragment = ReminderAdd()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}