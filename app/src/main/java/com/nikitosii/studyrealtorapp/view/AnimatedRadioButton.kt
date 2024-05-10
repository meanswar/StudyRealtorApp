package com.nikitosii.studyrealtorapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.radiobutton.MaterialRadioButton
import com.nikitosii.studyrealtorapp.databinding.ViewCustomRadioButtonBinding

class AnimatedRadioButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    MaterialRadioButton(context, attrs, defStyleAttr) {

    val binding = ViewCustomRadioButtonBinding.inflate(LayoutInflater.from(context))

    init { onClicks() }

    fun onClicks() {
        this.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.plPulse.startAnimation()
            else binding.plPulse.stopAnimation()
        }
    }
}