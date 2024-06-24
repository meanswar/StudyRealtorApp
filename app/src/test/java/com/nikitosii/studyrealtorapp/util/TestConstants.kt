package com.nikitosii.studyrealtorapp.util

import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType

object TestConstants {

    const val EMPTY_TEXT: String = ""
    const val BOOLEAN_VALID: Boolean = true
    const val BOOLEAN_INVALID: Boolean = false
    val SEARCH_TYPE_RENT: RequestType = RequestType.RENT
    val SEARCH_TYPE_SALE: RequestType = RequestType.SALE
    const val PHOTO_VALID =
        "https://images.pexels.com/photos/1045535/pexels-photo-1045535.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"

    const val ANY_TEXT = "TEXT"
    const val ANY_DIGITS = 120
    const val ANY_LONG = 1L
    const val ANY_FLOAT = 1.0f
    const val ANY_DOUBLE = 1.0

    const val REQUEST_ID_VALID = 1
    const val REQUEST_ID_INVALID = 0

    const val PHONE_EXT_VALID = "+122"
    const val PHONE_VALID = "8745624"

    const val STATUS_FOR_RENT = "rent"
    const val STATUS_FOR_SALE = "sale"

    const val EMAIL_VALID = "mail@gmail.com"
    const val EMAIL_INVALID = "mail"

    const val NAME_VALID = "name_valid"
    const val NAME_INVALID = "test"

    const val LOCATION_VALID = "location_valid"
    const val LOCATION_INVALID = "test"

    const val PAGE_VALID = 1
    const val PAGE_INVALID = 0

    const val ID_VALID = 1
    const val ID_INVALID = 0
    const val ID_FOR_CHANGES = 0

    const val ID_VALID_TEXT = "id_valid"
    const val ID_VALID_TEXT_FOR_CHANGES = "id_valid_for_changes"
    const val ID_VALID_FOR_REMOVING = "id_valid_for_removing"
    const val ID_INVALID_TEXT = "id_invalid"

    const val URL_VALID = "url_valid"
    const val URL_INVALID = "url_invalid"

    const val IMAGE_VALID = "image_id_valid"
    const val IMAGE_INVALID = "image_id_invalid"
    const val IMAGE_TEXT = "https://images.pexels.com/photos/1045535/pexels-photo-1045535.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"

    const val VALUE_VALID = "value_valid"
    const val VALUE_INVALID = "value_invalid"

    const val PRICE_VALID = 1
    const val PRICE_INVALID = 0

    const val REQUEST_TEXT_VALID = "request_text_valid"
    const val REQUEST_TEXT_INVALID = "request_text_invalid"

    const val REQUEST_DIGITS_VALID = 1
    const val REQUEST_DIGITS_INVALID = 0

    const val ADDRESS_VALID = "address_valid"
    const val ADDRESS_INVALID = "address_invalid"

    const val DEEP_LINK_VALID = "deep_link_valid"
    const val DEEP_LINK_INVALID = ""

    const val LATITUDE_VALID = 1.0
    const val LATITUDE_INVALID = 0.0

    const val LATITUDE_UKRAINE = 48.3
    const val LONGITUDE_UKRAINE = 31.1

    const val LONGITUDE_VALID = 1.0
    const val LONGITUDE_INVALID = 0.0

    const val SERVER_DATE_PATTERN = "2022-10-12T00:00:00"

    const val EXCEPTION_WRONG_PARAMS = "wrong params"
    const val EXCEPTION_ITEM_NOT_EXISTS = "item not exists"
}