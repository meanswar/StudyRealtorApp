package com.nikitosii.studyrealtorapp.util

import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Advertiser
import com.nikitosii.studyrealtorapp.core.source.local.model.Branding
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.County
import com.nikitosii.studyrealtorapp.core.source.local.model.Description
import com.nikitosii.studyrealtorapp.core.source.local.model.Flags
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.Location
import com.nikitosii.studyrealtorapp.core.source.local.model.Office
import com.nikitosii.studyrealtorapp.core.source.local.model.Phone
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.local.model.VrTour

object PropertyTestUtils {
    fun getExpectedProperties(): List<PropertyEntity> = listOf(
        PropertyEntity(
            advertisers = listOf(
                Advertiser(
                    email = TestConstants.EMAIL_VALID,
                    id = TestConstants.ID_VALID_TEXT,
                    href = TestConstants.URL_VALID,
                    mls_set = TestConstants.STATUS_FOR_SALE,
                    name = TestConstants.NAME_VALID,
                    nrds_id = TestConstants.ID_VALID_TEXT,
                    office = Office(
                        email = TestConstants.EMAIL_VALID,
                        id = TestConstants.ID_VALID_TEXT,
                        href = TestConstants.URL_VALID,
                        mls_set = TestConstants.STATUS_FOR_SALE,
                        name = TestConstants.NAME_VALID,
                        phones = listOf(
                            Phone(
                                ext = TestConstants.PHONE_EXT_VALID,
                                number = TestConstants.PHONE_VALID,
                                primary = TestConstants.BOOLEAN_VALID,
                                trackable = TestConstants.BOOLEAN_VALID,
                                type = TestConstants.ANY_TEXT
                            )
                        ),
                        image = TestConstants.IMAGE_VALID
                    ),
                    phones = listOf(
                        Phone(
                            ext = TestConstants.PHONE_EXT_VALID,
                            number = TestConstants.PHONE_VALID,
                            primary = TestConstants.BOOLEAN_VALID,
                            trackable = TestConstants.BOOLEAN_VALID,
                            type = TestConstants.ANY_TEXT
                        )
                    )
                )
            ),
            branding = listOf(
                Branding(
                    name = TestConstants.NAME_VALID,
                    photo = TestConstants.PHOTO_VALID,
                    type = TestConstants.ANY_TEXT,
                    accentColor = TestConstants.ANY_TEXT,
                    phone = TestConstants.PHONE_VALID
                )
            ),
            comingSoonDate = TestConstants.SERVER_DATE_PATTERN,
            description = Description(
                baths = TestConstants.ANY_DIGITS,
                bathsGtr1 = TestConstants.ANY_DIGITS,
                bathsGtr3 = TestConstants.ANY_DIGITS,
                bathsFull = TestConstants.ANY_DIGITS,
                bathsHalf = TestConstants.ANY_DIGITS,
                beds = TestConstants.ANY_DIGITS,
                garage = TestConstants.ANY_DIGITS,
                lotSqft = TestConstants.ANY_DIGITS,
                name = TestConstants.NAME_VALID,
                sqft = TestConstants.ANY_DIGITS,
                type = HouseType.APARTMENT,
                rooms = TestConstants.ANY_DIGITS,
                pool = TestConstants.ANY_TEXT,
                text = TestConstants.ANY_TEXT
            ),
            flags = Flags(
                isComingSoon = TestConstants.BOOLEAN_VALID,
                isContingent = TestConstants.BOOLEAN_VALID,
                isForeclosure = TestConstants.BOOLEAN_VALID,
                isNewConstruction = TestConstants.BOOLEAN_VALID,
                isNewListing = TestConstants.BOOLEAN_VALID,
                isPending = TestConstants.BOOLEAN_VALID,
                isPlan = TestConstants.BOOLEAN_VALID,
                isPriceReduced = TestConstants.BOOLEAN_VALID,
                isSubdivision = TestConstants.BOOLEAN_VALID
            ),
            lastUpdateDate = TestConstants.SERVER_DATE_PATTERN,
            listDate = TestConstants.SERVER_DATE_PATTERN,
            listPrice = TestConstants.PRICE_VALID,
            listPriceMax = TestConstants.ANY_DIGITS,
            listPriceMin = TestConstants.PRICE_VALID,
            location = Location(
                address = Address(
                    city = TestConstants.LOCATION_VALID,
                    coordinate = Coordinate(
                        latitude = TestConstants.LATITUDE_VALID,
                        longitude = TestConstants.LONGITUDE_VALID
                    ),
                    line = TestConstants.ADDRESS_VALID,
                    postalCode = TestConstants.ANY_TEXT,
                    state = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                ),
                county = County(TestConstants.NAME_VALID),
                streetViewUrl = TestConstants.URL_VALID
            ),
            photos = listOf(
                Photo(url = TestConstants.PHOTO_VALID),
                Photo(url = TestConstants.PHOTO_VALID)
            ),
            priceReducedAmount = TestConstants.ANY_TEXT,
            primaryPhoto = Photo(url = TestConstants.PHOTO_VALID),
            propertyId = TestConstants.ID_VALID_TEXT,
            status = TestConstants.STATUS_FOR_SALE,
            tags = listOf(TestConstants.ANY_TEXT, TestConstants.ANY_TEXT),
            virtualTours = listOf(
                VrTour(href = TestConstants.URL_VALID, type = TestConstants.ANY_TEXT),
                VrTour(href = TestConstants.URL_VALID, type = TestConstants.ANY_TEXT)
            ),
            favorite = TestConstants.BOOLEAN_VALID
        ),
        PropertyEntity(
            advertisers = listOf(
                Advertiser(
                    email = TestConstants.EMAIL_VALID,
                    id = TestConstants.ID_VALID_TEXT,
                    href = TestConstants.URL_VALID,
                    mls_set = TestConstants.STATUS_FOR_RENT,
                    name = TestConstants.NAME_VALID,
                    nrds_id = TestConstants.ID_VALID_TEXT,
                    office = Office(
                        email = TestConstants.EMAIL_VALID,
                        id = TestConstants.ID_VALID_TEXT,
                        href = TestConstants.URL_VALID,
                        mls_set = TestConstants.STATUS_FOR_RENT,
                        name = TestConstants.NAME_VALID,
                        phones = listOf(
                            Phone(
                                ext = TestConstants.PHONE_EXT_VALID,
                                number = TestConstants.PHONE_VALID,
                                primary = TestConstants.BOOLEAN_VALID,
                                trackable = TestConstants.BOOLEAN_VALID,
                                type = TestConstants.ANY_TEXT
                            )
                        ),
                        image = TestConstants.IMAGE_VALID
                    ),
                    phones = listOf(
                        Phone(
                            ext = TestConstants.PHONE_EXT_VALID,
                            number = TestConstants.PHONE_VALID,
                            primary = TestConstants.BOOLEAN_VALID,
                            trackable = TestConstants.BOOLEAN_VALID,
                            type = TestConstants.ANY_TEXT
                        )
                    )
                )
            ),
            branding = listOf(
                Branding(
                    name = TestConstants.NAME_VALID,
                    photo = TestConstants.PHOTO_VALID,
                    type = TestConstants.ANY_TEXT,
                    accentColor = TestConstants.ANY_TEXT,
                    phone = TestConstants.PHONE_VALID
                )
            ),
            comingSoonDate = TestConstants.SERVER_DATE_PATTERN,
            description = Description(
                baths = TestConstants.ANY_DIGITS,
                bathsGtr1 = TestConstants.ANY_DIGITS,
                bathsGtr3 = TestConstants.ANY_DIGITS,
                bathsFull = TestConstants.ANY_DIGITS,
                bathsHalf = TestConstants.ANY_DIGITS,
                beds = TestConstants.ANY_DIGITS,
                garage = TestConstants.ANY_DIGITS,
                lotSqft = TestConstants.ANY_DIGITS,
                name = TestConstants.NAME_VALID,
                sqft = TestConstants.ANY_DIGITS,
                type = HouseType.TOWNHOMES,
                rooms = TestConstants.ANY_DIGITS,
                pool = TestConstants.ANY_TEXT,
                text = TestConstants.ANY_TEXT
            ),
            flags = Flags(
                isComingSoon = TestConstants.BOOLEAN_VALID,
                isContingent = TestConstants.BOOLEAN_VALID,
                isForeclosure = TestConstants.BOOLEAN_VALID,
                isNewConstruction = TestConstants.BOOLEAN_VALID,
                isNewListing = TestConstants.BOOLEAN_VALID,
                isPending = TestConstants.BOOLEAN_VALID,
                isPlan = TestConstants.BOOLEAN_VALID,
                isPriceReduced = TestConstants.BOOLEAN_VALID,
                isSubdivision = TestConstants.BOOLEAN_VALID
            ),
            lastUpdateDate = TestConstants.SERVER_DATE_PATTERN,
            listDate = TestConstants.SERVER_DATE_PATTERN,
            listPrice = TestConstants.PRICE_VALID,
            listPriceMax = TestConstants.ANY_DIGITS,
            listPriceMin = TestConstants.PRICE_VALID,
            location = Location(
                address = Address(
                    city = TestConstants.LOCATION_VALID,
                    coordinate = Coordinate(
                        latitude = TestConstants.LATITUDE_VALID,
                        longitude = TestConstants.LONGITUDE_VALID
                    ),
                    line = TestConstants.ADDRESS_VALID,
                    postalCode = TestConstants.ANY_TEXT,
                    state = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                ),
                county = County(TestConstants.NAME_VALID),
                streetViewUrl = TestConstants.URL_VALID
            ),
            photos = listOf(
                Photo(url = TestConstants.PHOTO_VALID),
                Photo(url = TestConstants.PHOTO_VALID)
            ),
            priceReducedAmount = TestConstants.ANY_TEXT,
            primaryPhoto = Photo(url = TestConstants.PHOTO_VALID),
            propertyId = TestConstants.ID_VALID_TEXT_FOR_CHANGES,
            status = TestConstants.STATUS_FOR_RENT,
            tags = listOf(TestConstants.ANY_TEXT, TestConstants.ANY_TEXT),
            virtualTours = listOf(
                VrTour(href = TestConstants.URL_VALID, type = TestConstants.ANY_TEXT),
                VrTour(href = TestConstants.URL_VALID, type = TestConstants.ANY_TEXT)
            ),
            favorite = TestConstants.BOOLEAN_VALID
        ),
        PropertyEntity(
            advertisers = listOf(
                Advertiser(
                    email = TestConstants.EMAIL_VALID,
                    id = TestConstants.ID_VALID_TEXT,
                    href = TestConstants.URL_VALID,
                    mls_set = TestConstants.STATUS_FOR_SALE,
                    name = TestConstants.NAME_VALID,
                    nrds_id = TestConstants.ID_VALID_TEXT,
                    office = Office(
                        email = TestConstants.EMAIL_VALID,
                        id = TestConstants.ID_VALID_TEXT,
                        href = TestConstants.URL_VALID,
                        mls_set = TestConstants.STATUS_FOR_SALE,
                        name = TestConstants.NAME_VALID,
                        phones = listOf(
                            Phone(
                                ext = TestConstants.PHONE_EXT_VALID,
                                number = TestConstants.PHONE_VALID,
                                primary = TestConstants.BOOLEAN_VALID,
                                trackable = TestConstants.BOOLEAN_VALID,
                                type = TestConstants.ANY_TEXT
                            )
                        ),
                        image = TestConstants.IMAGE_VALID
                    ),
                    phones = listOf(
                        Phone(
                            ext = TestConstants.PHONE_EXT_VALID,
                            number = TestConstants.PHONE_VALID,
                            primary = TestConstants.BOOLEAN_VALID,
                            trackable = TestConstants.BOOLEAN_VALID,
                            type = TestConstants.ANY_TEXT
                        )
                    )
                )
            ),
            branding = listOf(
                Branding(
                    name = TestConstants.NAME_VALID,
                    photo = TestConstants.PHOTO_VALID,
                    type = TestConstants.ANY_TEXT,
                    accentColor = TestConstants.ANY_TEXT,
                    phone = TestConstants.PHONE_VALID
                )
            ),
            comingSoonDate = TestConstants.SERVER_DATE_PATTERN,
            description = Description(
                baths = TestConstants.ANY_DIGITS,
                bathsGtr1 = TestConstants.ANY_DIGITS,
                bathsGtr3 = TestConstants.ANY_DIGITS,
                bathsFull = TestConstants.ANY_DIGITS,
                bathsHalf = TestConstants.ANY_DIGITS,
                beds = TestConstants.ANY_DIGITS,
                garage = TestConstants.ANY_DIGITS,
                lotSqft = TestConstants.ANY_DIGITS,
                name = TestConstants.NAME_VALID,
                sqft = TestConstants.ANY_DIGITS,
                type = HouseType.APARTMENT,
                rooms = TestConstants.ANY_DIGITS,
                pool = TestConstants.ANY_TEXT,
                text = TestConstants.ANY_TEXT
            ),
            flags = Flags(
                isComingSoon = TestConstants.BOOLEAN_VALID,
                isContingent = TestConstants.BOOLEAN_VALID,
                isForeclosure = TestConstants.BOOLEAN_VALID,
                isNewConstruction = TestConstants.BOOLEAN_VALID,
                isNewListing = TestConstants.BOOLEAN_VALID,
                isPending = TestConstants.BOOLEAN_VALID,
                isPlan = TestConstants.BOOLEAN_VALID,
                isPriceReduced = TestConstants.BOOLEAN_VALID,
                isSubdivision = TestConstants.BOOLEAN_VALID
            ),
            lastUpdateDate = TestConstants.SERVER_DATE_PATTERN,
            listDate = TestConstants.SERVER_DATE_PATTERN,
            listPrice = TestConstants.PRICE_VALID,
            listPriceMax = TestConstants.ANY_DIGITS,
            listPriceMin = TestConstants.PRICE_VALID,
            location = Location(
                address = Address(
                    city = TestConstants.LOCATION_VALID,
                    coordinate = Coordinate(
                        latitude = TestConstants.LATITUDE_VALID,
                        longitude = TestConstants.LONGITUDE_VALID
                    ),
                    line = TestConstants.ADDRESS_VALID,
                    postalCode = "12345",
                    state = "TX",
                    stateCode = "TX"
                ),
                county = County(TestConstants.NAME_VALID),
                streetViewUrl = TestConstants.URL_VALID
            ),
            photos = listOf(
                Photo(url = TestConstants.PHOTO_VALID),
                Photo(url = TestConstants.PHOTO_VALID)
            ),
            priceReducedAmount = TestConstants.ANY_TEXT,
            primaryPhoto = Photo(url = TestConstants.PHOTO_VALID),
            propertyId = TestConstants.ID_VALID_TEXT + 2,
            status = TestConstants.STATUS_FOR_SALE,
            tags = listOf(TestConstants.ANY_TEXT, TestConstants.ANY_TEXT),
            virtualTours = listOf(
                VrTour(href = TestConstants.URL_VALID, type = TestConstants.ANY_TEXT),
                VrTour(href = TestConstants.URL_VALID, type = TestConstants.ANY_TEXT)
            ),
            favorite = TestConstants.BOOLEAN_VALID
        ),
        PropertyEntity(
            advertisers = listOf(
                Advertiser(
                    email = TestConstants.EMAIL_VALID,
                    id = TestConstants.ID_VALID_TEXT,
                    href = TestConstants.URL_VALID,
                    mls_set = TestConstants.STATUS_FOR_RENT,
                    name = TestConstants.NAME_VALID,
                    nrds_id = TestConstants.ID_VALID_TEXT,
                    office = Office(
                        email = TestConstants.EMAIL_VALID,
                        id = TestConstants.ID_VALID_TEXT,
                        href = TestConstants.URL_VALID,
                        mls_set = TestConstants.STATUS_FOR_RENT,
                        name = TestConstants.NAME_VALID,
                        phones = listOf(
                            Phone(
                                ext = TestConstants.PHONE_EXT_VALID,
                                number = TestConstants.PHONE_VALID,
                                primary = TestConstants.BOOLEAN_VALID,
                                trackable = TestConstants.BOOLEAN_VALID,
                                type = TestConstants.ANY_TEXT
                            )
                        ),
                        image = TestConstants.IMAGE_VALID
                    ),
                    phones = listOf(
                        Phone(
                            ext = TestConstants.PHONE_EXT_VALID,
                            number = TestConstants.PHONE_VALID,
                            primary = TestConstants.BOOLEAN_VALID,
                            trackable = TestConstants.BOOLEAN_VALID,
                            type = TestConstants.ANY_TEXT
                        )
                    )
                )
            ),
            branding = listOf(
                Branding(
                    name = TestConstants.NAME_VALID,
                    photo = TestConstants.PHOTO_VALID,
                    type = TestConstants.ANY_TEXT,
                    accentColor = TestConstants.ANY_TEXT,
                    phone = TestConstants.PHONE_VALID
                )
            ),
            comingSoonDate = TestConstants.SERVER_DATE_PATTERN,
            description = Description(
                baths = TestConstants.ANY_DIGITS,
                bathsGtr1 = TestConstants.ANY_DIGITS,
                bathsGtr3 = TestConstants.ANY_DIGITS,
                bathsFull = TestConstants.ANY_DIGITS,
                bathsHalf = TestConstants.ANY_DIGITS,
                beds = TestConstants.ANY_DIGITS,
                garage = TestConstants.ANY_DIGITS,
                lotSqft = TestConstants.ANY_DIGITS,
                name = TestConstants.NAME_VALID,
                sqft = TestConstants.ANY_DIGITS,
                type = HouseType.TOWNHOMES,
                rooms = TestConstants.ANY_DIGITS,
                pool = TestConstants.ANY_TEXT,
                text = TestConstants.ANY_TEXT
            ),
            flags = Flags(
                isComingSoon = TestConstants.BOOLEAN_VALID,
                isContingent = TestConstants.BOOLEAN_VALID,
                isForeclosure = TestConstants.BOOLEAN_VALID,
                isNewConstruction = TestConstants.BOOLEAN_VALID,
                isNewListing = TestConstants.BOOLEAN_VALID,
                isPending = TestConstants.BOOLEAN_VALID,
                isPlan = TestConstants.BOOLEAN_VALID,
                isPriceReduced = TestConstants.BOOLEAN_VALID,
                isSubdivision = TestConstants.BOOLEAN_VALID
            ),
            lastUpdateDate = TestConstants.SERVER_DATE_PATTERN,
            listDate = TestConstants.SERVER_DATE_PATTERN,
            listPrice = TestConstants.PRICE_VALID,
            listPriceMax = TestConstants.ANY_DIGITS,
            listPriceMin = TestConstants.PRICE_VALID,
            location = Location(
                address = Address(
                    city = TestConstants.LOCATION_VALID,
                    coordinate = Coordinate(
                        latitude = TestConstants.LATITUDE_VALID,
                        longitude = TestConstants.LONGITUDE_VALID
                    ),
                    line = TestConstants.ADDRESS_VALID,
                    postalCode = "12345",
                    state = "FL",
                    stateCode = "FL"
                ),
                county = County(TestConstants.NAME_VALID),
                streetViewUrl = TestConstants.URL_VALID
            ),
            photos = listOf(
                Photo(url = TestConstants.PHOTO_VALID),
                Photo(url = TestConstants.PHOTO_VALID)
            ),
            priceReducedAmount = TestConstants.ANY_TEXT,
            primaryPhoto = Photo(url = TestConstants.PHOTO_VALID),
            propertyId = TestConstants.ID_VALID_FOR_REMOVING,
            status = TestConstants.STATUS_FOR_RENT,
            tags = listOf(TestConstants.ANY_TEXT, TestConstants.ANY_TEXT),
            virtualTours = listOf(
                VrTour(href = TestConstants.URL_VALID, type = TestConstants.ANY_TEXT),
                VrTour(href = TestConstants.URL_VALID, type = TestConstants.ANY_TEXT)
            ),
            favorite = TestConstants.BOOLEAN_VALID
        )
    )

    fun getPropertyId(): String = getExpectedProperty().propertyId

    fun getExpectedProperty(): PropertyEntity = getExpectedProperties().first()
}