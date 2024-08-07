package com.nikitosii.studyrealtorapp.flow.base

import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.util.ext.string
import dagger.android.support.DaggerAppCompatActivity

abstract class AbsActivity: DaggerAppCompatActivity() {


    fun openDialogNoConnection() =
        BaseErrorDialog.getInstance().show(supportFragmentManager, "BaseErrorDialog")

    fun openError(errorText: String = string(R.string.description_error), isHiding: Boolean = true) {
        BaseErrorDialog.getInstance(
            description = errorText,
            descriptionPrimaryButton = string(R.string.ok),
            isHiding = isHiding
        ).show(supportFragmentManager, BaseErrorDialog::class.java.name)
    }
}