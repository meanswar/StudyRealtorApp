package com.nikitosii.studyrealtorapp.util.ext.model

import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile

fun Profile.getFullName(): String? = if (name != null && surname != null) "$name $surname" else null