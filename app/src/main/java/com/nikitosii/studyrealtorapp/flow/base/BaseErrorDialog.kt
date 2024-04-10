package com.nikitosii.studyrealtorapp.flow.base

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.DialogErrorBinding
import com.nikitosii.studyrealtorapp.util.ext.drawable
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.showText

class BaseErrorDialog(
    @DrawableRes
    private val background: Int? = null,
    private val description: CharSequence? = null,
    private val descriptionPrimaryButton: String? = null,
    private val descriptionRegularButton: String? = null,
    private val listenerPrimaryButton: View.OnClickListener? = null,
    private val listenerRegularButton: View.OnClickListener? = null,
    private val isHiding: Boolean? = true
) : BaseDialogFragment(R.layout.dialog_error) {

    private lateinit var binding: DialogErrorBinding
    override fun initBinding(view: View) {
        binding = DialogErrorBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        if (isHiding != null) isCancelable = isHiding

        with(binding) {
            clContent.background = background?.let { drawable(it) }
            tvDescription.text = description
            btnAccept.text = descriptionPrimaryButton
            btnCancel.showText(descriptionRegularButton)
            if (listenerPrimaryButton != null) btnAccept.setOnClickListener(listenerPrimaryButton)
            else btnAccept.onClick { dialog?.dismiss() }
            if (listenerRegularButton != null) btnAccept.setOnClickListener(listenerRegularButton)
            else btnCancel.onClick { dialog?.dismiss() }
        }
    }

    companion object {
        fun getInstance(
            @DrawableRes
            background: Int? = null,
            description: CharSequence? = null,
            descriptionPrimaryButton: String? = null,
            descriptionRegularButton: String? = null,
            listenerPrimaryButton: View.OnClickListener? = null,
            listenerRegularButton: View.OnClickListener? = null,
            isHiding: Boolean? = true
        ) = BaseErrorDialog(
            background,
            description,
            descriptionPrimaryButton,
            descriptionRegularButton,
            listenerPrimaryButton,
            listenerRegularButton,
            isHiding
        )
    }
}