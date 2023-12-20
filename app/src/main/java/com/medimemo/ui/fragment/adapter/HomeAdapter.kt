package com.medimemo.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medimemo.data.MedicalRecord
import com.medimemo.data.RacikanObat
import com.medimemo.data.Reminder
import com.medimemo.data.Vacsine
import com.medimemo.databinding.ItemCheckBinding
import com.medimemo.databinding.ItemReminderBinding
import com.medimemo.databinding.ItemVacsineBinding
import com.medimemo.ui.swipeup.MedicalCheck

class HomeAdapter (private val medicineList: ArrayList<MedicalRecord>): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemCheckBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCheckBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = medicineList[position]
        holder.apply {
            binding.apply {
                tvTitleAll.text = currentItem.anamesis
            }
        }
    }
}