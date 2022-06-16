package com.example.task1.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.task1.*
import com.example.task1.adapter.RecyclerViewAdapter
import com.example.task1.model.ShortlyModel


class MainActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: PresenterImpl
    private lateinit var button: Button
    private lateinit var editText: EditText
    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP = this
        button = findViewById(R.id.buttonShortenIt)
        editText = findViewById(R.id.editTextShortenLink)
        presenter = PresenterImpl(this, ServiceImpl())
        PRESENTER = presenter
        recyclerViewAdapter = RecyclerViewAdapter(presenter)
        RECYCLERVIEWADAPTER = recyclerViewAdapter
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.initDataBase()
       // viewModel.getAllShortly()
        VIEWMODEL = viewModel
        VIEWMODEL.getAllShortly().observe(APP) { listShortly ->
            recyclerViewAdapter.updateArrayAdapter(listShortly.asReversed())
        }

        showMainFragment()

        editText.setOnClickListener(OnClickListener {
            editText.hint = "Shorten a link here ..."
            editText.setHintTextColor(Color.WHITE)
            editText.setBackgroundResource(R.drawable.body_for_edit_text)
        })

        button.setOnClickListener(OnClickListener {
            if (editText.text.toString() == "") {
                editText.hint = "Please add a link here"
                editText.setHintTextColor(Color.RED)
                editText.setBackgroundResource(R.drawable.body_for_edit_text_error)
            } else {
                presenter.handleButtonShortenItClick(editText.text.toString().trim())
            }
        })
    }

    fun insertInRoom(shortlyModel: ShortlyModel) {
        viewModel.insert(shortlyModel) {}
    }

    fun deleteRoom(shortlyModel: ShortlyModel) {
        viewModel.delete(shortlyModel) {}
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
                    //  HistoryFragment.newInstance(recyclerAdapter = recyclerViewAdapter)
                    HistoryFragment.newInstance()
                )
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.place_holder,
//                    HistoryFragment.newInstance(recyclerAdapter = recyclerViewAdapter)
                    HistoryFragment.newInstance()
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

    override fun showToastCopiedSuccessfully(textToShowCopied: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(textToShowCopied, textToShowCopied)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "COPIED!", Toast.LENGTH_SHORT).show()
    }

    override fun showToastDeletedSuccessfully() {
        Toast.makeText(this, "DELETED!", Toast.LENGTH_SHORT).show()
    }

    override fun updateRecyclerViewAdapter(response: ShortlyModel) {
        recyclerViewAdapter.insertDataInROOM(response)
    }

    override fun showInputErrorMessage() {
        editText.hint = "invalid url ..."
        editText.setHintTextColor(Color.RED)
        editText.setBackgroundResource(R.drawable.body_for_edit_text_error)
        editText.text = null
    }

    override fun showToastItemAlreadyInDB() {
        Toast.makeText(this, "This link is already in your list of History", Toast.LENGTH_SHORT)
            .show()
    }
}