package com.nikitosii.studyrealtorapp.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.MutableLiveData
import com.google.android.material.slider.RangeSlider
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ViewRangeBinding
import com.nikitosii.studyrealtorapp.util.ext.onTextChanged
import timber.log.Timber
import java.math.BigDecimal

class RangeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val coefficient by lazy { MutableLiveData(BigDecimal(1)) }
    private val valueMax by lazy { MutableLiveData(100f) }

    val binding = ViewRangeBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.RangeView) {
            setEndDrawable(getDrawable(R.styleable.RangeView_endDrawable))
            val minValue = getInt(R.styleable.RangeView_minValue, 0)
            val maxValue = getInt(R.styleable.RangeView_maxValue, 100)
            val decimal = BigDecimal(maxValue - minValue).setScale(9)
            coefficient.value = decimal.divide(BigDecimal(100))
            Timber.i("coefficient = ${coefficient.value} minValue = $minValue maxValue = $maxValue decimal = $decimal")
            valueMax.postValue(maxValue.toFloat())
            setValues()
            initListeners()
        }
    }

    private fun setEndDrawable(drawable: Drawable?) {
        binding.etTo.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        binding.etFrom.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }

    private fun setValues() {
        binding.rsEditor.setValues(0f, 100f)
    }

    private fun initListeners() {
        with(binding) {
            rsEditor.addOnChangeListener(RangeSlider.OnChangeListener { view, _, fromUser ->
                if (fromUser) {
                    val minValue = (view.values[0] * coefficient.value?.toFloat()!!).toInt()
                    val maxValue = (view.values[1] * coefficient.value?.toFloat()!!).toInt()
                    etFrom.setText(minValue.toString())
                    etTo.setText(maxValue.toString())
                }
            })
            etFrom.onTextChanged { calculateValues() }
            etTo.onTextChanged { calculateValues() }
        }
    }

    private fun calculateValues() {
        with(binding) {
            val minValueString = etFrom.text.toString()
            val minValue = if (minValueString.isEmpty()) 0f else minValueString.toFloat()

            val maxValueString = etTo.text.toString()
            val maxValue = if (maxValueString.isEmpty() || maxValueString.toFloat() > valueMax.value!!) valueMax.value!! else maxValueString.toFloat()
            Timber.i("minValue: $minValue, maxValue: $maxValue, coefficient: ${coefficient.value?.toFloat()!!}")
            setValues(minValue / coefficient.value?.toFloat()!!, maxValue / coefficient.value?.toFloat()!!)
        }
    }

    private fun setValues(from: Float, to: Float) {
        binding.rsEditor.values = mutableListOf(from, to)
    }
}