package com.example.task1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.APP
import com.example.task1.PresenterImpl
import com.example.task1.R
import com.example.task1.model.ShortlyModel


class RecyclerViewAdapter(private val presenterImpl: PresenterImpl) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var dataArray = mutableListOf<ShortlyModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateArrayAdapter(responseArray: List<ShortlyModel>) {
        dataArray.clear()
        dataArray.addAll(responseArray)
        notifyDataSetChanged()
    }

    fun insertDataInROOM(shortlyModel: ShortlyModel) {
        APP.insertInRoom(shortlyModel)
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

        holder.originalLinkTV.text = dataArray[position].originalLink
        holder.shortenedLinkTV.text = dataArray[position].shortlyLink

        holder.trashCanIV.setOnClickListener(OnClickListener {
           // dataArray.removeAt(position)
            APP.deleteRoom(dataArray[position])

            presenterImpl.handleDeleteButtonClick(
                holder.originalLinkTV.text.toString(),
                holder.shortenedLinkTV.text.toString()
            )
            notifyDataSetChanged()
        })

        holder.copyButton.setOnClickListener(OnClickListener {
            val text = holder.shortenedLinkTV.text.toString()
            presenterImpl.handleCopyButtonClick(text)
        })
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var originalLinkTV: TextView = itemView.findViewById(R.id.originalLinkTextView)
        var trashCanIV: ImageView = itemView.findViewById(R.id.trashCanImageView)
        var shortenedLinkTV: TextView = itemView.findViewById(R.id.shortenedLinkTextView)
        var copyButton: Button = itemView.findViewById(R.id.copyButton)
    }
}