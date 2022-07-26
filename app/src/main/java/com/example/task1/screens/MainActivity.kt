package com.example.task1.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.task1.*
import com.example.task1.adapter.RecyclerViewAdapter
import com.example.task1.databinding.ActivityMainBinding
import com.example.task1.model.ShortlyModel

class MainActivity : AppCompatActivity(), Contract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MainViewModel
    private lateinit var rVAdapter: RecyclerViewAdapter
    private lateinit var listOfShortlyModelsFromRoom: MutableList<ShortlyModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        vm.getAllHistoryLinks().observe(this) { listShortly ->
            rVAdapter.updateArrayAdapter(listShortly.asReversed())
            listOfShortlyModelsFromRoom.clear()
            listOfShortlyModelsFromRoom.addAll(listShortly)
        }

        showMainFragment()

        binding.ShortenLinkET.setOnClickListener {
            binding.ShortenLinkET.hint = "Shorten a link here ..."
            binding.ShortenLinkET.setHintTextColor(Color.WHITE)
            binding.ShortenLinkET.setBackgroundResource(R.drawable.body_for_edit_text)
        }

        binding.buttonShortenIt.setOnClickListener {
            if (binding.ShortenLinkET.text.toString() == "") {
                binding.ShortenLinkET.hint = "Please add a link here"
                binding.ShortenLinkET.setHintTextColor(Color.RED)
                binding.ShortenLinkET.setBackgroundResource(R.drawable.body_for_edit_text_error)
            } else {
                val linkToShorten: String = binding.ShortenLinkET.text.toString().trim()

                vm.handleButtonShortenItClick(linkToShorten, object : ViewModelListener {
                    override fun onServiceSuccess(response: ShortlyModel) {
                        if (listOfShortlyModelsFromRoom.isEmpty()) {
                            vm.insert(response) {}
                        } else {
                            var copies = 0
                            for (item in listOfShortlyModelsFromRoom) {
                                if (item.originalLink == response.originalLink) {
                                    showToastLinkAlreadyInHistory()
                                    copies += 1
                                    break
                                }
                            }
                            if (copies == 0) {
                                vm.insert(response) {}
                            }
                        }
                        showHistoryFragment()
                    }

                    override fun onFailure(throwable: Throwable) {
                        throwable.localizedMessage?.let { Log.e("PENA", it) }
                    }

                    override fun onIncorrectInputQuery() {
                        showMessageInputError()
                    }

                    override fun onItemAlreadyInDB() {
                        showToastLinkAlreadyInHistory()
                        showHistoryFragment()
                    }
                }
                )
            }
        }
    }

    private fun init() {
        listOfShortlyModelsFromRoom = arrayListOf()
        vm = ViewModelProvider(
            owner = this,
            factory = MainViewModelFactory(application, ServiceImpl())
        )[MainViewModel::class.java]
        vm.initDataBase()
        rVAdapter = RecyclerViewAdapter(this)
    }

    fun handleDeleteButtonClick(shortlyModel: ShortlyModel) {
        vm.delete(shortlyModel) {}
    }

    override fun showMainFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.place_holder, MainFragment.newInstance())
            .commit()
    }

    override fun showHistoryFragment() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.place_holder,
                    HistoryFragment.newInstance(this, rVAdapter)
                )
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.place_holder,
                    HistoryFragment.newInstance(this, rVAdapter)
                )
                .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed();
        }
    }

    override fun showToastCopiedSuccessfully(textToCopy: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(textToCopy, textToCopy)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "COPIED!", Toast.LENGTH_SHORT).show()
    }

    override fun showToastDeletedSuccessfully() {
        Toast.makeText(this, "DELETED!", Toast.LENGTH_SHORT).show()
    }

    override fun showMessageInputError() {
        binding.ShortenLinkET.hint = "invalid url ..."
        binding.ShortenLinkET.setHintTextColor(Color.RED)
        binding.ShortenLinkET.setBackgroundResource(R.drawable.body_for_edit_text_error)
        binding.ShortenLinkET.text = null
    }

    override fun showToastLinkAlreadyInHistory() {
        Toast.makeText(this, "This link is already in your History", Toast.LENGTH_SHORT)
            .show()
    }
}