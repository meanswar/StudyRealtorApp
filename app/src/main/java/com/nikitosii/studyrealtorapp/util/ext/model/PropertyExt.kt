package com.nikitosii.studyrealtorapp.util.ext.model

import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.local.model.Property

fun Property.getName(): String =
    "${location?.address?.line}, ${location?.address?.city}, ${location?.address?.stateCode}"