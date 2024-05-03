package com.nikitosii.studyrealtorapp.core.source.local.model.parcelize

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoContainer(val photos: List<Photo>, val idStart: Int) : Parcelable