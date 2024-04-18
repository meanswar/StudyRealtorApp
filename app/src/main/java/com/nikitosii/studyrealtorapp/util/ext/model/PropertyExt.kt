package com.nikitosii.studyrealtorapp.util.ext.model

import com.nikitosii.studyrealtorapp.core.source.local.model.Property

fun Property.getName(): String =
    "${location?.address?.city}, ${location?.county?.name}, ${location?.address?.state}}"
