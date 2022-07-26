package com.example.task1.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.task1.ContractMVVM
import com.example.task1.viewmodels.MainViewModel
import com.example.task1.R
import com.example.task1.ViewModelListener
import com.example.task1.databinding.ActivityMainBinding
import com.example.task1.data.pojo.Shortly
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), ContractMVVM.View {

    private lateinit var binding: ActivityMainBinding
    private val vm by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm.initDataBase()

        binding.editTextShortenLinkMain.addTextChangedListener {
            binding.editTextShortenLinkMain.hint = "Shorten a link here ..."
            binding.editTextShortenLinkMain.setHintTextColor(this.getColor(R.color.hint_color))
            binding.editTextShortenLinkMain.setBackgroundResource(R.drawable.body_for_edit_text)
        }

        binding.buttonShortenItMain.setOnClickListener {
            onButtonShortenItClick()
        }
    }

    private fun onButtonShortenItClick() {
        val imm = baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if (binding.editTextShortenLinkMain.text.toString() == "") {
            binding.editTextShortenLinkMain.hint = "Please add a link here"
            binding.editTextShortenLinkMain.setHintTextColor(Color.RED)
            binding.editTextShortenLinkMain.setBackgroundResource(R.drawable.body_for_edit_text_error);
        } else {
            val linkToShorten: String = binding.editTextShortenLinkMain.text.toString().trim()
            vm.handleButtonShortenItClick(linkToShorten, object : ViewModelListener {

                override fun onServiceSuccess(response: Shortly) {
                    binding.editTextShortenLinkMain.text.clear()
                    showHistoryActivity()
                    vm.insert(response)
                }

                override fun onFailure(throwable: Throwable) {
                    throwable.localizedMessage?.let { Log.e("PENA", it) }
                }

                override fun onIncorrectTextQueryInput() {
                    showMessageInputError()
                }

                override fun onItemAlreadyInDataBase() {
                    showHistoryActivity()
                }
            }
            )
        }
    }

    fun showHistoryActivity() {
        startActivity(Intent(this,
            HistoryActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

    override fun showMessageInputError() {
        binding.editTextShortenLinkMain.text = null
        binding.editTextShortenLinkMain.hint = "invalid url ..."
        binding.editTextShortenLinkMain.setHintTextColor(Color.RED)
        binding.editTextShortenLinkMain.setBackgroundResource(R.drawable.body_for_edit_text_error)
    }
}