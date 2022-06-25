package com.example.task1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R
import com.example.task1.model.ShortlyModel
import com.example.task1.screens.MainActivity


class RecyclerViewAdapter(private val view: MainActivity) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var dataArray = mutableListOf<ShortlyModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateArrayAdapter(responseArray: List<ShortlyModel>) {
        dataArray.clear()
        dataArray.addAll(responseArray)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.originalLinkIV.text = dataArray[position].originalLink
        holder.shortenedLinkTV.text = dataArray[position].shortlyLink

        holder.deleteButton.setOnClickListener {
            view.handleDeleteButtonClick(dataArray[position])
            view.showToastDeletedSuccessfully()
            notifyDataSetChanged()
        }

        holder.copyButton.setOnClickListener {
            val text = holder.shortenedLinkTV.text.toString()
            view.showToastCopiedSuccessfully(text)
        }
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var originalLinkIV: TextView = itemView.findViewById(R.id.originalLinkTextView)
        var deleteButton: ImageView = itemView.findViewById(R.id.trashCanImageView)
        var shortenedLinkTV: TextView = itemView.findViewById(R.id.shortenedLinkTextView)
        var copyButton: Button = itemView.findViewById(R.id.copyButton)
    }
}