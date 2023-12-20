package com.medimemo.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medimemo.data.Vacsine
import com.medimemo.databinding.ItemVacsineBinding

class VacsineAdapter (private val vacsineList: ArrayList<Vacsine>): RecyclerView.Adapter<VacsineAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemVacsineBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemVacsineBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return vacsineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = vacsineList[position]
        holder.apply {
            binding.apply {
                tvLocV.text = currentItem.location
                tvTitleV.text = currentItem.date
            }
        }
    }
}