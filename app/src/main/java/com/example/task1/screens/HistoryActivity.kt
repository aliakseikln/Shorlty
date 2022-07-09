package com.example.task1.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.task1.*
import com.example.task1.adapter.RecyclerViewAdapter
import com.example.task1.databinding.ActivityHistoryBinding
import com.example.task1.db.model.ShortlyModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity(), Contract.View {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var rVAdapter: RecyclerViewAdapter
    private val vm by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        vm.getAllHistoryLinks().observe(this) { listShortly ->
            RV_ITEMS.clear()
            listShortly.forEach {
                if (COPIED_ITEM_ID == it.id) {
                    RV_ITEMS.add(HistoryTableItem(it.id, it.originalLink, it.shortlyLink, true))
                } else {
                    RV_ITEMS.add(HistoryTableItem(it.id, it.originalLink, it.shortlyLink, false))
                }
            }
            rVAdapter.updateRVAdapter(RV_ITEMS.asReversed())
        }

        binding.ETShortenLinkHistory.addTextChangedListener {
            binding.ETShortenLinkHistory.hint = "Shorten a link here ..."
            binding.ETShortenLinkHistory.setHintTextColor(this.getColor(R.color.hint_color))
            binding.ETShortenLinkHistory.setBackgroundResource(R.drawable.body_for_edit_text)
        }

        binding.buttonShortenItHistory.setOnClickListener {
            onButtonShortenItClick()
        }
    }

    private fun init() {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rVAdapter = RecyclerViewAdapter(this)
        binding.historyRv.adapter = rVAdapter
        binding.historyRv.setHasFixedSize(true)
        vm.initDataBase()
    }

    private fun onButtonShortenItClick() {
        val imm = baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if (binding.ETShortenLinkHistory.text.toString() == "") {
            binding.ETShortenLinkHistory.hint = "Please add a link here"
            binding.ETShortenLinkHistory.setHintTextColor(Color.RED)
            binding.ETShortenLinkHistory.setBackgroundResource(R.drawable.body_for_edit_text_error)
        } else {
            val linkToShorten: String = binding.ETShortenLinkHistory.text.toString().trim()
            vm.handleButtonShortenItClick(linkToShorten, object : ViewModelListener {
                override fun onServiceSuccess(response: ShortlyModel) {
                    if (RV_ITEMS.isEmpty()) {
                        binding.ETShortenLinkHistory.text.clear()
                        vm.insert(response) {}
                    } else {
                        var copies = 0
                        for (item in RV_ITEMS) {
                            if (item.originalLink == response.originalLink) {
                                showToastLinkAlreadyInHistory()
                                copies += 1
                                break
                            }
                        }
                        if (copies == 0) {
                            binding.ETShortenLinkHistory.text.clear()
                            vm.insert(response) {}
                        }
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    throwable.localizedMessage?.let { Log.e("PENA", it) }
                }

                override fun onIncorrectTextQueryInput() {
                    showMessageInputError()
                }

                override fun onItemAlreadyInDataBase() {
                    showToastLinkAlreadyInHistory()
                }
            }
            )
        }
    }

    fun handleRViewDeleteButtonClick(historyTableItem: HistoryTableItem) {
        vm.deleteById(historyTableItem.id) {}
    }

    fun makeTextCopy(textToCopy: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(textToCopy, textToCopy)
        clipboard.setPrimaryClip(clip)
    }

    override fun showToastLinkAlreadyInHistory() {
        Toast.makeText(this, "This link is already in your History", Toast.LENGTH_SHORT)
            .show()
    }

    override fun showMessageInputError() {
        binding.ETShortenLinkHistory.text = null
        binding.ETShortenLinkHistory.hint = "invalid url ..."
        binding.ETShortenLinkHistory.setHintTextColor(Color.RED)
        binding.ETShortenLinkHistory.setBackgroundResource(R.drawable.body_for_edit_text_error)
    }

    fun handleRViewCopyButtonClick(historyTableItem: HistoryTableItem) {
        COPIED_ITEM_ID = historyTableItem.id
    }
}