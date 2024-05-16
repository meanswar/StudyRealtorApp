package com.nikitosii.studyrealtorapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.navigation.findNavController
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ViewToolbarBinding
import com.nikitosii.studyrealtorapp.util.ext.getStringOrNull
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.onAnimCompleted
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show

class RealtorToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val binding =
        ViewToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.RealtorToolbar) {
            getFloat(R.styleable.RealtorToolbar_startAnimationSpeed, 3.0f).run {
                setStartAnimationSpeed(this)
            }
            getBoolean(R.styleable.RealtorToolbar_isSearchVisible, false).run { isSearchVisible(this) }
            getBoolean(R.styleable.RealtorToolbar_isBackVisible, true).run { isBtnBackVisible(this) }
            getStringOrNull(R.styleable.RealtorToolbar_toolbar_title)?.run { setTitle(this) }
        }
        onBackClick()
    }

    private fun setStartAnimationSpeed(speed: Float) {
        binding.btnBack.speed = speed
    }

    private fun isSearchVisible(show: Boolean) {
        binding.ivSearch.show(show)
    }

    fun setSearchText(text: String) {
        binding.etSearch.setText(text)
    }

    private fun isBtnBackVisible(show: Boolean) {
        binding.btnBack.show(show)
    }

    private fun setTitle(title: String) {
        binding.tbFilter.title = title
    }

    private fun onBackClick() {
        with(binding.btnBack) {
            onClick { playAnimation() }
            onAnimCompleted { findNavController().navigateUp() }
        }
    }

    fun onSearchClick(action: () -> Unit) {
        binding.ivSearch.onClick { action() }
    }

    fun showSearchContent() {
        with(binding) {
            etSearch.show()
            ivSearch.hide()
            tbFilter.hide()
        }
    }

    fun showEndButton() {
        binding.btnEnd.show()
    }

    fun initEndBtnAnimation(targetView: ViewGroup) {
        binding.btnEnd.initAnimation(targetView)
    }
}