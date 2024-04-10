package com.nikitosii.studyrealtorapp.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ViewRangeBinding
import com.nikitosii.studyrealtorapp.util.ext.getStringOrNull

class RangeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs){

    val binding = ViewRangeBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.RangeView) {
            setEndDrawable(getDrawable(R.styleable.RangeView_endDrawable))
        }
    }

    private fun setEndDrawable(drawable: Drawable?) {
        binding.etTo.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        binding.etFrom.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }
}