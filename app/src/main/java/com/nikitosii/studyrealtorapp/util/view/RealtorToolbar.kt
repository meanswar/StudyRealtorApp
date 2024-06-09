package com.nikitosii.studyrealtorapp.util.view

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
            getFloat(R.styleable.RealtorToolbar_startAnimationSpeed, 6.0f).run {
                setStartAnimationSpeed(this)
            }
            getStringOrNull(R.styleable.RealtorToolbar_toolbar_title)?.run { setTitle(this) }
            getBoolean(R.styleable.RealtorToolbar_endBtnVisible, true).run { setEndBtnVisible(this) }
            getBoolean(R.styleable.RealtorToolbar_firstBtnVisible, true).run { setFirstBtnVisible(this) }
        }
        onBackClick()
    }

    private fun setStartAnimationSpeed(speed: Float) {
        binding.btnBack.speed = speed
    }

    private fun setEndBtnVisible(isVisible: Boolean) {
        binding.btnEnd.show(isVisible)
    }

    private fun setFirstBtnVisible(isVisible: Boolean) {
        binding.btnBack.show(isVisible, false)
    }

    private fun setTitle(title: String) {
        binding.tbFilter.title = title
    }

    private fun onBackClick() {
        with(binding.btnBack) {
            onClick { playAnimation() }
            onAnimCompleted({ findNavController().navigateUp() })
        }
    }

    fun showEndButton() {
        binding.btnEnd.show()
    }

    fun initEndBtnAnimation(targetView: ViewGroup) {
        binding.btnEnd.initAnimation(targetView)
    }
}