package com.example.ttspoc.ui.nativeTTS

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ttspoc.R
import kotlinx.android.synthetic.main.text_item.view.*

class VoicesAdapter(private val onItemClickListener: (pos: Int) -> Unit) :
    ListAdapter<String, RecyclerView.ViewHolder>(CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_item, parent, false)
        return StringsViewHolder(row, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StringsViewHolder).bind(getItem(position))
    }

    class StringsViewHolder(
        itemView: View,
        val onItemClickListener: (pos: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(voice: String) {

            itemView.voice_name.text = voice

            itemView.adapter_root.setOnClickListener {
                onItemClickListener(adapterPosition)
            }
        }
    }

    companion object {
        @SuppressLint("DiffUtilEquals")
        private val CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem as? String == newItem as? String
        }
    }
}
