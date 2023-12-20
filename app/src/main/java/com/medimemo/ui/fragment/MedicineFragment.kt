package com.medimemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
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

    private fun showBottomSheet() {
        val bottomSheetFragment = MedicineAdd()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}