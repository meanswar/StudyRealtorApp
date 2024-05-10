package com.nikitosii.studyrealtorapp.util.ext.model

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.util.ext.formatPrice

fun Property.getAddress(): String =
    "${location?.address?.line}, ${location?.address?.city}, ${location?.address?.stateCode}"

fun Property.getPrice(): Int {

    return listPrice ?: ((listPriceMax + listPriceMin) / 2)
}

fun Property.getPriceStringFormat(): String {

    return (listPrice ?: ((listPriceMax + listPriceMin) / 2)).toString().formatPrice()
}
