//package com.example.task1.screens
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.RecyclerView
//import com.example.task1.R
//import com.example.task1.adapter.RecyclerViewAdapter
//
//
//class HistoryFragment : Fragment() {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var rVAdapter: RecyclerViewAdapter
//    private lateinit var activity: MainActivity
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.history_fragment, container, false)
//        recyclerView = view.findViewById(R.id.myRecyclerView)
//        recyclerView.adapter = rVAdapter
//        recyclerView.setHasFixedSize(true)
//        return view
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(
//            mainActivity: MainActivity,
//            recyclerViewAdapter: RecyclerViewAdapter,
//        ): HistoryFragment {
//            val fragment = HistoryFragment()
//            val bundle = Bundle().apply {
//                fragment.activity = mainActivity
//                fragment.rVAdapter = recyclerViewAdapter
//            }
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
//}