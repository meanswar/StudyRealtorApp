package com.nikitosii.studyrealtorapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.MutableLiveData
import com.google.android.material.slider.RangeSlider
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ViewRangeBinding
import timber.log.Timber
import java.math.BigDecimal

class RangeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val coefficient by lazy { MutableLiveData(BigDecimal(1)) }
    private val valueMax by lazy { MutableLiveData(100) }
    private val valueMin by lazy { MutableLiveData(0) }
    private val rangeType by lazy { MutableLiveData(RANGE_TYPE_MONEY) }

    val binding = ViewRangeBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.RangeView) {
            setTitle(getString(R.styleable.RangeView_rangeTitle))
            getInteger(R.styleable.RangeView_rangeType, RANGE_TYPE_OTHER).let { setRangeType(it) }
            val minValue = getInt(R.styleable.RangeView_minValue, 0)
            val maxValue = getInt(R.styleable.RangeView_maxValue, 100)
            val decimal = BigDecimal(maxValue - minValue).setScale(9)
            setValues(minValue, maxValue)
            coefficient.value = decimal.divide(BigDecimal(100))
            Timber.i("coefficient = ${coefficient.value} minValue = $minValue maxValue = $maxValue decimal = $decimal")
            setValues()
            initListeners()
        }
    }

    fun setTitle(title: String?) {
        binding.tvTitle.text = title
    }

    fun setRangeType(rangeType: Int) {
        this.rangeType.postValue(rangeType)
    }

    private fun setValues() {
        binding.rsEditor.setValues(0f, 100f)
    }

    fun onRangeChanged(action: (Int, Int) -> Unit) {
        binding.rsEditor.addOnChangeListener(RangeSlider.OnChangeListener { view, _, _ ->
            _onRangeChanged(view)
            val minValue = view.values[0]
            val maxValue = view.values[1]
            action(
                if (minValue == 0f) RANGE_VALUE_ANY else calculateValue(view.values[0]),
                if (maxValue == 100f) RANGE_VALUE_ANY else calculateValue(view.values[1])
            )
        })
    }

    private fun _onRangeChanged(view: RangeSlider) {
        val minValue = calculateValue(view.values[0])
        val maxValue = calculateValue(view.values[1])
        val minValueInt = view.values[0].toInt()
        val maxValueInt = view.values[1].toInt()

        binding.tvResult.text = when {
            maxValueInt == 100 && minValueInt == 0 ->
                context.getString(
                    if (isMoneyType()) R.string.layout_filters_filter_result_money_any
                    else R.string.layout_filters_filter_result_any
                )

            maxValueInt == 100 && minValueInt > 0 ->
                context.getString(
                    if (isMoneyType()) R.string.layout_filters_filter_result_money_min
                    else R.string.layout_filters_filter_result_min,
                    minValue
                )

            maxValueInt < 100 && minValueInt == 0 ->
                context.getString(
                    if (isMoneyType()) R.string.layout_filters_filter_result_money_max
                    else R.string.layout_filters_filter_result_max, maxValue
                )

            maxValueInt < 100 && minValueInt > 0 ->
                context.getString(
                    if (isMoneyType()) R.string.layout_filters_filter_result_money
                    else R.string.layout_filters_filter_result,
                    minValue,
                    maxValue
                )

            else -> throw IllegalStateException("Wrong values")
        }
    }

    private fun initListeners() {
        with(binding) {
            rsEditor.addOnChangeListener(RangeSlider.OnChangeListener { view, _, _ ->
                _onRangeChanged(
                    view
                )
            })
        }
    }

    private fun calculateValue(value: Float): Int = (value * coefficient.value?.toFloat()!!).toInt()

    private fun setValues(from: Int, to: Int) {
        binding.rsEditor.values = mutableListOf(from.toFloat(), to.toFloat())
        valueMax.postValue(to)
        valueMin.postValue(from)
    }

    private fun isMoneyType(): Boolean = rangeType.value == RANGE_TYPE_MONEY

    fun getValues(): List<Float> = listOf(
        calculateValue(binding.rsEditor.values[0]).toFloat(),
        calculateValue(binding.rsEditor.values[1]).toFloat()
    )

    fun initResult(from: Int?, to: Int?) {
        with(binding.rsEditor) {
            when {
                from == null && to == null -> setValues(0f, 100f)
                from == null && to != null -> setValues(0f, calculateSetValue(to))
                from != null && to == null -> setValues(calculateSetValue(from), 100f)
                else -> setValues(calculateSetValue(from!!), calculateSetValue(to!!))
            }
        }
    }
    
    private fun calculateSetValue(value: Int): Float =
        value.toFloat().div(coefficient.value!!.toFloat())

    companion object {
        private const val RANGE_TYPE_MONEY = 0
        private const val RANGE_TYPE_OTHER = 1

        const val RANGE_VALUE_ANY = -1
    }
}