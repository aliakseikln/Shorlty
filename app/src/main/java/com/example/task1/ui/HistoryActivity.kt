package com.example.task1.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.task1.*
import com.example.task1.ui.adapters.RecyclerViewAdapter
import com.example.task1.data.pojo.HistoryTableItem
import com.example.task1.databinding.ActivityHistoryBinding
import com.example.task1.data.pojo.Shortly
import com.example.task1.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity(), ContractMVVM.View {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var rVAdapter: RecyclerViewAdapter
    private val vm by viewModel<MainViewModel>()
    private lateinit var rVItems: MutableList<HistoryTableItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        vm.getAllHistoryLinks().observe(this) { listShortly ->
            rVItems.clear()
            listShortly.forEach {
                if (vm.getCopiedItemId() == it.id) {
                    rVItems.add(HistoryTableItem(it.id, it.originalLink, it.shortlyLink, true))
                } else {
                    rVItems.add(HistoryTableItem(it.id, it.originalLink, it.shortlyLink, false))
                }
            }
            rVAdapter.updateRVAdapter(rVItems.asReversed())
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
        rVItems = arrayListOf()
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
                override fun onServiceSuccess(response: Shortly) {
                    binding.ETShortenLinkHistory.text.clear()
                    vm.insert(response)
                }

                override fun onFailure(throwable: Throwable) {
                    throwable.localizedMessage?.let { Log.e("PENA", it) }
                }

                override fun onIncorrectTextQueryInput() {
                    showMessageInputError()
                }

                override fun onItemAlreadyInDataBase() {
                }
            }
            )
        }
    }

    fun handleRViewDeleteButtonClick(historyTableItem: HistoryTableItem) {
        vm.deleteById(historyTableItem.id)
    }

    fun makeTextCopy(textToCopy: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(textToCopy, textToCopy)
        clipboard.setPrimaryClip(clip)
    }

    override fun showMessageInputError() {
        binding.ETShortenLinkHistory.text = null
        binding.ETShortenLinkHistory.hint = "invalid url ..."
        binding.ETShortenLinkHistory.setHintTextColor(Color.RED)
        binding.ETShortenLinkHistory.setBackgroundResource(R.drawable.body_for_edit_text_error)
    }

    fun handleRViewCopyButtonClick(selectedItemId: Int) {
        vm.setCopiedItemId(selectedItemId)
    }
}