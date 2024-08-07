package com.andrewsaba.cityfinder.custom

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.andrewsaba.cityfinder.databinding.ViewSearchBinding


class SearchView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var binding: ViewSearchBinding =
        ViewSearchBinding.inflate(LayoutInflater.from(context),this,true)
    init {
        binding.openSearchButton.setOnClickListener { openSearch() }
        binding.closeSearchButton.setOnClickListener { closeSearch() }
        binding.executeSearchButton.setOnClickListener{executeSearch()}
        binding.searchInputText.setOnEditorActionListener {view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                keyEvent == null ||
                keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                executeSearch()
            }
            false
        }
    }

    private fun executeSearch() {
        dismissKeyboard(binding.searchInputText)
        val searchString=binding.searchInputText.text.toString()
        closeSearch()
        binding.searchInputText.setText(searchString)
    }

    private fun dismissKeyboard(view: View?) {
        if (view != null) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    private fun showKeyboard(view: View?) {
        if (view != null) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0)
        }
    }


    private fun openSearch() {
        binding.searchInputText.text.clear()
        binding.searchOpenView.visibility = View.VISIBLE
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            binding.searchOpenView,
            (    binding.openSearchButton.right +     binding.openSearchButton.left) / 2,
            (    binding.openSearchButton.top +     binding.openSearchButton.bottom) / 2,
            0f, width.toFloat()
        )
        circularReveal.duration = 300
        circularReveal.start()
        binding.searchInputText.requestFocus()
        showKeyboard(binding.searchInputText)
    }


    private fun closeSearch() {
        dismissKeyboard(binding.searchInputText)
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            binding.searchOpenView,
            (  binding.openSearchButton.right +   binding.openSearchButton.left) / 2,
            (  binding.openSearchButton.top +   binding.openSearchButton.bottom) / 2,
            width.toFloat(), 0f
        )

        circularConceal.duration = 300
        circularConceal.start()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) = Unit
            override fun onAnimationCancel(animation: Animator) = Unit
            override fun onAnimationStart(animation: Animator) = Unit
            override fun onAnimationEnd(animation: Animator) {
                binding.searchOpenView.visibility = View.INVISIBLE
                binding.searchInputText.text.clear()
                circularConceal.removeAllListeners()
            }
        })
    }

}