package com.nikitosii.studyrealtorapp.util.ext.model

import com.nikitosii.studyrealtorapp.core.source.local.model.parcelize.PhotoContainer

fun PhotoContainer.getTransitionName(): String? = photos[idStart].url