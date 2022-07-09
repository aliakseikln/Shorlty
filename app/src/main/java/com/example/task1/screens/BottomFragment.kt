//package com.example.task1.screens
//
//import android.graphics.Color
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.widget.addTextChangedListener
//import androidx.fragment.app.Fragment
//import com.example.task1.MAIN_ACTIVITY
//import com.example.task1.R
//import com.example.task1.ViewModelListener
//import com.example.task1.databinding.FragmentBottomFragmentBinding
//import com.example.task1.db.model.ShortlyModel
//
//class BottomFragment : Fragment() {
//
//    private lateinit var binding: FragmentBottomFragmentBinding
//    private lateinit var activity: MainActivity
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentBottomFragmentBinding.inflate(inflater, container, false)
//        activity = MAIN_ACTIVITY
//
//        binding.ShortenLinkET.addTextChangedListener {
//            binding.ShortenLinkET.hint = "Shorten a link here ..."
//            binding.ShortenLinkET.setHintTextColor(requireContext().getColor(R.color.hint_color))
//            binding.ShortenLinkET.setBackgroundResource(R.drawable.body_for_edit_text)
//        }
//
//        binding.buttonShortenIt.setOnClickListener {
//            if (binding.ShortenLinkET.text.toString() == "") {
//                binding.ShortenLinkET.hint = "Please add a link here"
//                binding.ShortenLinkET.setHintTextColor(Color.RED)
//                binding.ShortenLinkET.setBackgroundResource(R.drawable.body_for_edit_text_error);
//
//            } else {
////                hideKeyboard()
//                val linkToShorten: String = binding.ShortenLinkET.text.toString().trim()
//                activity.vm.handleButtonShortenItClick(linkToShorten, object : ViewModelListener {
//                    override fun onServiceSuccess(response: ShortlyModel) {
//                        if (activity.listOfShortlyModelsFromRoom.isEmpty()) {
//                            activity.vm.insert(response) {}
//                            activity.showHistoryActivity()
//                        } else {
//                            var copies = 0
//                            for (item in activity.listOfShortlyModelsFromRoom) {
//                                if (item.originalLink == response.originalLink) {
//                                    activity.showToastLinkAlreadyInHistory()
//                                    copies += 1
//                                    break
//                                }
//                            }
//                            if (copies == 0) {
//                                activity.vm.insert(response) {}
//                                activity.showHistoryActivity()
//                            }
//                        }
//                        activity.showHistoryActivity()
//                    }
//
//                    override fun onFailure(throwable: Throwable) {
//                        throwable.localizedMessage?.let { Log.e("PENA", it) }
//                    }
//
//                    override fun onIncorrectTextQueryInput() {
//                        showMessageInputError()
//                    }
//
//                    override fun onItemAlreadyInDataBase() {
//                        activity.showToastLinkAlreadyInHistory()
//                    }
//                }
//                )
//            }
//        }
//        return binding.root
//    }
//
//    fun showMessageInputError() {
//        binding.ShortenLinkET.text = null
//        binding.ShortenLinkET.hint = "invalid url ..."
//        binding.ShortenLinkET.setHintTextColor(Color.RED)
//        binding.ShortenLinkET.setBackgroundResource(R.drawable.body_for_edit_text_error)
//    }
//
////    private fun hideKeyboard() {
////        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////        imm.hideSoftInputFromWindow(activity.window.decorView.rootView.windowToken, 0)
////        binding.ShortenLinkET.hint = "Shorten a link here ..."
////        binding.ShortenLinkET.clearFocus()
////    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(): BottomFragment {
//            return BottomFragment()
//        }
//    }
//}