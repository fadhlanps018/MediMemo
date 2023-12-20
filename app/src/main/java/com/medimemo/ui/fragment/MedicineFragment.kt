package com.medimemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medimemo.R
import com.medimemo.databinding.FragmentHomeBinding
import com.medimemo.databinding.FragmentMedicineBinding
import com.medimemo.ui.swipeup.MedicalCheck
import com.medimemo.ui.swipeup.MedicineAdd

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MedicineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MedicineFragment : Fragment() {
    private lateinit var binding: FragmentMedicineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicineBinding.inflate(inflater, container, false)

        showBottomSheet()

        return binding.root
    }

    private fun showBottomSheet() {
        val bottomSheetFragment = MedicineAdd()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}