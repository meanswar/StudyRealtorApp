package com.nikitosii.studyrealtorapp.util.ext.model

import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile

fun Profile.getFullName() = "$name $surname"