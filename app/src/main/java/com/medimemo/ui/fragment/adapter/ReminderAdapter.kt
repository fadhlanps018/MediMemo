package com.medimemo.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medimemo.data.Reminder
import com.medimemo.data.Vacsine
import com.medimemo.databinding.ItemReminderBinding
import com.medimemo.databinding.ItemVacsineBinding

class ReminderAdapter (private val reminderList: ArrayList<Reminder>): RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemReminderBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemReminderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = reminderList[position]
        holder.apply {
            binding.apply {
                tvLocR.text = currentItem.location
                tvTitleV.text = currentItem.date
                tvTitleV.text = currentItem.title
                tvTimeR.text = currentItem.time
            }
        }
    }
}