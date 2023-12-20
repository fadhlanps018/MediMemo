package com.medimemo.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medimemo.data.Reminder
import com.medimemo.data.Vacsine
import com.medimemo.databinding.ItemCheckBinding
import com.medimemo.databinding.ItemReminderBinding
import com.medimemo.databinding.ItemVacsineBinding

class ReminderAdapter (private val reminderList: ArrayList<Reminder>): RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemCheckBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCheckBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = reminderList[position]
        holder.apply {
            binding.apply {
                tvTitleAll.text = currentItem.title
            }
        }
    }
}