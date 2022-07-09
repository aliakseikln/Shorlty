package com.example.task1.screens

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.task1.*
import com.example.task1.databinding.ActivityMainBinding
import com.example.task1.db.model.ShortlyModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), Contract.View {

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
                override fun onServiceSuccess(response: ShortlyModel) {
                    if (RV_ITEMS.isEmpty()) {
                        binding.editTextShortenLinkMain.text.clear()
                        showHistoryActivity()
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
                            binding.editTextShortenLinkMain.text.clear()
                            showHistoryActivity()
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
                    showHistoryActivity()
                }
            }
            )
        }
    }

    fun showHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    override fun showToastLinkAlreadyInHistory() {
        Toast.makeText(this, "This link is already in your History", Toast.LENGTH_SHORT)
            .show()
    }

    override fun showMessageInputError() {
        binding.editTextShortenLinkMain.text = null
        binding.editTextShortenLinkMain.hint = "invalid url ..."
        binding.editTextShortenLinkMain.setHintTextColor(Color.RED)
        binding.editTextShortenLinkMain.setBackgroundResource(R.drawable.body_for_edit_text_error)
    }
}