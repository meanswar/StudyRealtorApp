package com.nikitosii.studyrealtorapp.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.nikitosii.studyrealtorapp.databinding.ViewSelectionBinding

class SelectionView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), OnItemSelectedListener {

    private val binding = ViewSelectionBinding.inflate(LayoutInflater.from(context))
    private lateinit var onItemSelected: (String) -> Unit

    init {
        addView(binding.root)
        setTitles(listOf())
        onSelection()
    }

    fun onItemSelected(listener: (String) -> Unit) {
        onItemSelected = listener
    }

    fun setTitles(titles: List<String>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, titles)
        binding.spSort.adapter = adapter
    }

    private fun onSelection() {
        binding.spSort.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onItemSelected(parent?.getItemAtPosition(position).toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}