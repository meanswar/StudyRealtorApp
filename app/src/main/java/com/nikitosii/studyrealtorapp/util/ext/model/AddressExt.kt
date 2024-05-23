package com.nikitosii.studyrealtorapp.util.ext.model

import com.nikitosii.studyrealtorapp.core.source.local.model.Address

fun Address.getAddress() = "$city, $stateCode"