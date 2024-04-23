package com.nikitosii.studyrealtorapp.util.ext.model

import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.local.model.Property

fun Property.getName(): String =
    "${location?.address?.line}, ${location?.address?.city}, ${location?.county?.name}, ${location?.address?.state}"

fun Property.getAllPhotos(): List<Photo> = listOf(primaryPhoto).toMutableList().apply {
    addAll(1, photos?: listOf())
}
