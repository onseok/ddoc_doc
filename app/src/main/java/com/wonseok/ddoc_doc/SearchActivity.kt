package com.wonseok.ddoc_doc

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.wonseok.ddoc_doc.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val editText: EditText by lazy {
        findViewById(R.id.searchActivityEditTextView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        autoKeyboardShow()


        binding.searchActivityBackButton.setOnClickListener {
            onBackPressed()
        }

    }

    private fun autoKeyboardShow() {
        //EditText에 포커싱
        editText.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideKeyboard()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }
}