package com.example.task1.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.*
import com.example.task1.adapter.RecyclerViewAdapter
import com.example.task1.model.ShortlyModel

class HistoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.history_fragment, container, false)
        recyclerView = view.findViewById(R.id.myRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(APP)
        recyclerAdapter = RECYCLERVIEWADAPTER
        recyclerView.adapter = recyclerAdapter

        return view
    }

    companion object {
//        @JvmStatic
//        fun newInstance(recyclerAdapter: RecyclerViewAdapter): HistoryFragment {
//            val fragment = HistoryFragment()
////            val bundle = Bundle().apply {
////                fragment.recyclerAdapter = recyclerAdapter
////            }
////            fragment.arguments = bundle
//            return fragment
//        }

        @JvmStatic
        fun newInstance(): HistoryFragment {

            return HistoryFragment()
        }
    }
}