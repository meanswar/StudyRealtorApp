package com.nikitosii.studyrealtorapp.di.api

import com.nikitosii.studyrealtorapp.util.TestConstants
import com.nikitosii.studyrealtorapp.util.TestConstants.ADDRESS_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.ADDRESS_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DOUBLE
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.DEEP_LINK_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.DEEP_LINK_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.EMAIL_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.EMAIL_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_INVALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.IMAGE_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.IMAGE_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.LATITUDE_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.LATITUDE_UKRAINE
import com.nikitosii.studyrealtorapp.util.TestConstants.LATITUDE_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.LONGITUDE_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.LONGITUDE_UKRAINE
import com.nikitosii.studyrealtorapp.util.TestConstants.LONGITUDE_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.NAME_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.NAME_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PHONE_EXT_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PHONE_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PHOTO_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PRICE_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PRICE_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.SERVER_DATE_PATTERN
import com.nikitosii.studyrealtorapp.util.TestConstants.STATUS_FOR_RENT
import com.nikitosii.studyrealtorapp.util.TestConstants.STATUS_FOR_SALE
import com.nikitosii.studyrealtorapp.util.TestConstants.URL_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.VALUE_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.VALUE_VALID
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.net.model.advertiser.AdvertiserResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseHomeSearch
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseResponse
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseSinglePropertyResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.data.BrandingResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.description.DescriptionResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.description.DetailsResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.flags.FlagsResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.DetailedLocationResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.LocationResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.CoordinateApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.CountyResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.office.OfficeResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.phone.PhoneResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyDetailsResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.property.TaxRecordResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.school.EducationFacilitiesResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.school.SchoolResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.source.AgentResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.source.SourceResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.vrtour.VrTourResponseApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PropertiesApiTest {

    @Provides
    @Singleton
    internal fun providesPropertiesApi() = object : PropertiesApi {
        override suspend fun getPropertiesForSale(
            location: String?,
            house: String?,
            priceMin: Int?,
            priceMax: Int?,
            bedsMin: Int?,
            bedsMax: Int?,
            bathsMin: Int?,
            bathsMax: Int?,
            sqftMin: Int?,
            sqftMax: Int?,
            page: Int?,
            sort: String?
        ): BaseResponse {
            return BaseResponse(
                BaseHomeSearch(
                    count = 3,
                    results = listOf(
                        PropertyResponseApi(
                            advertisers = listOf(
                                AdvertiserResponseApi(
                                    email = EMAIL_VALID,
                                    fulfillment_id = ID_VALID_TEXT,
                                    href = "https://advertiser1.com",
                                    mls_set = ANY_TEXT,
                                    name = NAME_VALID,
                                    nrds_id = ID_VALID_TEXT,
                                    office = OfficeResponseApi(
                                        name = NAME_VALID,
                                        href = "https://office1.com",
                                        fulfillment_id = ID_VALID_TEXT,
                                        phones = listOf(
                                            PhoneResponseApi(
                                                ext = "123",
                                                number = PHONE_VALID,
                                                primary = true,
                                                trackable = true,
                                                type = "mobile"
                                            )
                                        ),
                                        photo = PhotoResponseApi(url = PHOTO_VALID),
                                        email = EMAIL_VALID,
                                        mls_set = ANY_TEXT
                                    ),
                                    phones = listOf(
                                        PhoneResponseApi(
                                            ext = "123",
                                            number = PHONE_VALID,
                                            primary = true,
                                            trackable = true,
                                            type = "mobile"
                                        )
                                    )
                                )
                            ),
                            branding = listOf(
                                BrandingResponseApi(
                                    name = NAME_VALID,
                                    photo = IMAGE_VALID,
                                    type = ANY_TEXT,
                                    accentColor = VALUE_VALID,
                                    phone = PHONE_VALID
                                )
                            ),
                            comingSoonDate = "2022-10-12T00:00:00",
                            description = DescriptionResponseApi(
                                baths = 2,
                                bathsGtr1 = 1,
                                bathsGtr3 = 1,
                                bathsFull = 1,
                                bathsHalf = 1,
                                beds = 3,
                                garage = 2,
                                lotSqft = 2000,
                                name = NAME_VALID,
                                sqft = 1500,
                                pool = ANY_TEXT,
                                text = ANY_TEXT,
                                type = HouseType.APARTMENT,
                                rooms = 6
                            ),
                            flags = FlagsResponseApi(
                                isComingSoon = true,
                                isContingent = false,
                                isForeclosure = false,
                                isNewConstruction = true,
                                isNewListing = true,
                                isPending = false,
                                isPlan = false,
                                isPriceReduced = false,
                                isSubdivision = false
                            ),
                            lastUpdateDate = "2022-10-12T00:00:00",
                            listDate = "2022-10-12T00:00:00",
                            listPrice = PRICE_VALID,
                            listPriceMax = PRICE_VALID + 10000,
                            listPriceMin = PRICE_VALID - 10000,
                            listingId = ID_VALID_TEXT,
                            location = LocationResponseApi(
                                address = AddressResponseApi(
                                    city = ADDRESS_VALID,
                                    coordinate = CoordinateApi(LATITUDE_VALID, LONGITUDE_VALID),
                                    line = ADDRESS_VALID,
                                    postalCode = VALUE_VALID,
                                    state = VALUE_VALID,
                                    stateCode = VALUE_VALID
                                ),
                                county = CountyResponseApi(
                                    fipsCode = VALUE_VALID,
                                    name = VALUE_VALID
                                ),
                                streetViewUrl = DEEP_LINK_VALID
                            ),
                            photos = listOf(PhotoResponseApi(url = PHOTO_VALID)),
                            priceReducedAmount = VALUE_VALID,
                            primaryPhoto = PhotoResponseApi(url = PHOTO_VALID),
                            propertyId = ID_VALID_TEXT,
                            status = STATUS_FOR_SALE,
                            tags = listOf("new", "featured"),
                            taxRecord = TaxRecordResponseApi(publicRecordId = VALUE_VALID),
                            virtualTours = listOf(
                                VrTourResponseApi(
                                    href = "https://virtualtour1.com",
                                    type = VALUE_VALID
                                )
                            )
                        ),
                        PropertyResponseApi(
                            advertisers = listOf(
                                AdvertiserResponseApi(
                                    email = EMAIL_INVALID,
                                    fulfillment_id = ID_INVALID_TEXT,
                                    href = "https://advertiser2.com",
                                    mls_set = ANY_TEXT,
                                    name = NAME_INVALID,
                                    nrds_id = ID_INVALID_TEXT,
                                    office = OfficeResponseApi(
                                        name = NAME_INVALID,
                                        href = "https://office2.com",
                                        fulfillment_id = ID_INVALID_TEXT,
                                        phones = listOf(
                                            PhoneResponseApi(
                                                ext = "456",
                                                number = PHONE_VALID,
                                                primary = false,
                                                trackable = false,
                                                type = "home"
                                            )
                                        ),
                                        photo = PhotoResponseApi(url = PHOTO_VALID),
                                        email = EMAIL_VALID,
                                        mls_set = ANY_TEXT
                                    ),
                                    phones = listOf(
                                        PhoneResponseApi(
                                            ext = "456",
                                            number = PHONE_VALID,
                                            primary = false,
                                            trackable = false,
                                            type = "home"
                                        )
                                    )
                                )
                            ),
                            branding = listOf(
                                BrandingResponseApi(
                                    name = NAME_INVALID,
                                    photo = IMAGE_INVALID,
                                    type = ANY_TEXT,
                                    accentColor = VALUE_INVALID,
                                    phone = PHONE_VALID
                                )
                            ),
                            comingSoonDate = "2022-11-12T00:00:00",
                            description = DescriptionResponseApi(
                                baths = 1,
                                bathsGtr1 = 0,
                                bathsGtr3 = 0,
                                bathsFull = 1,
                                bathsHalf = 0,
                                beds = 2,
                                garage = 1,
                                lotSqft = 1500,
                                name = NAME_INVALID,
                                sqft = 1000,
                                pool = ANY_TEXT,
                                text = ANY_TEXT,
                                type = HouseType.CONDOS,
                                rooms = 4
                            ),
                            flags = FlagsResponseApi(
                                isComingSoon = false,
                                isContingent = true,
                                isForeclosure = false,
                                isNewConstruction = false,
                                isNewListing = true,
                                isPending = true,
                                isPlan = false,
                                isPriceReduced = true,
                                isSubdivision = false
                            ),
                            lastUpdateDate = "2022-11-12T00:00:00",
                            listDate = "2022-11-12T00:00:00",
                            listPrice = PRICE_INVALID,
                            listPriceMax = PRICE_INVALID + 5000,
                            listPriceMin = PRICE_INVALID - 5000,
                            listingId = ID_INVALID_TEXT,
                            location = LocationResponseApi(
                                address = AddressResponseApi(
                                    city = ADDRESS_INVALID,
                                    coordinate = CoordinateApi(LATITUDE_INVALID, LONGITUDE_INVALID),
                                    line = ADDRESS_INVALID,
                                    postalCode = VALUE_INVALID,
                                    state = VALUE_INVALID,
                                    stateCode = VALUE_INVALID
                                ),
                                county = CountyResponseApi(
                                    fipsCode = VALUE_INVALID,
                                    name = VALUE_INVALID
                                ),
                                streetViewUrl = DEEP_LINK_INVALID
                            ),
                            photos = listOf(PhotoResponseApi(url = "https://photo2.com")),
                            priceReducedAmount = VALUE_INVALID,
                            primaryPhoto = PhotoResponseApi(url = "https://primaryphoto2.com"),
                            propertyId = ID_INVALID_TEXT,
                            status = STATUS_FOR_SALE,
                            tags = listOf("sale", "discounted"),
                            taxRecord = TaxRecordResponseApi(publicRecordId = VALUE_INVALID),
                            virtualTours = listOf(
                                VrTourResponseApi(
                                    href = "https://virtualtour2.com",
                                    type = VALUE_INVALID
                                )
                            )
                        ),
                        PropertyResponseApi(
                            advertisers = listOf(
                                AdvertiserResponseApi(
                                    email = EMAIL_VALID,
                                    fulfillment_id = ID_VALID_TEXT,
                                    href = "https://advertiser3.com",
                                    mls_set = ANY_TEXT,
                                    name = NAME_VALID,
                                    nrds_id = ID_VALID_TEXT,
                                    office = OfficeResponseApi(
                                        name = NAME_VALID,
                                        href = "https://office3.com",
                                        fulfillment_id = ID_VALID_TEXT,
                                        phones = listOf(
                                            PhoneResponseApi(
                                                ext = "789",
                                                number = PHONE_VALID,
                                                primary = true,
                                                trackable = true,
                                                type = "work"
                                            )
                                        ),
                                        photo = PhotoResponseApi(url = PHOTO_VALID),
                                        email = EMAIL_VALID,
                                        mls_set = ANY_TEXT
                                    ),
                                    phones = listOf(
                                        PhoneResponseApi(
                                            ext = "789",
                                            number = PHONE_VALID,
                                            primary = true,
                                            trackable = true,
                                            type = "work"
                                        )
                                    )
                                )
                            ),
                            branding = listOf(
                                BrandingResponseApi(
                                    name = NAME_VALID,
                                    photo = IMAGE_VALID,
                                    type = ANY_TEXT,
                                    accentColor = VALUE_VALID,
                                    phone = PHONE_VALID
                                )
                            ),
                            comingSoonDate = "2022-12-12T00:00:00",
                            description = DescriptionResponseApi(
                                baths = 3,
                                bathsGtr1 = 2,
                                bathsGtr3 = 2,
                                bathsFull = 2,
                                bathsHalf = 1,
                                beds = 4,
                                garage = 3,
                                lotSqft = 3000,
                                name = NAME_VALID,
                                sqft = 2000,
                                pool = ANY_TEXT,
                                text = ANY_TEXT,
                                type = HouseType.TOWNHOMES,
                                rooms = 8
                            ),
                            flags = FlagsResponseApi(
                                isComingSoon = true,
                                isContingent = false,
                                isForeclosure = true,
                                isNewConstruction = false,
                                isNewListing = false,
                                isPending = false,
                                isPlan = true,
                                isPriceReduced = false,
                                isSubdivision = true
                            ),
                            lastUpdateDate = "2022-12-12T00:00:00",
                            listDate = "2022-12-12T00:00:00",
                            listPrice = PRICE_VALID * 2,
                            listPriceMax = (PRICE_VALID * 2) + 15000,
                            listPriceMin = (PRICE_VALID * 2) - 15000,
                            listingId = ID_VALID_TEXT,
                            location = LocationResponseApi(
                                address = AddressResponseApi(
                                    city = ADDRESS_VALID,
                                    coordinate = CoordinateApi(LATITUDE_UKRAINE, LONGITUDE_UKRAINE),
                                    line = ADDRESS_VALID,
                                    postalCode = VALUE_VALID,
                                    state = VALUE_VALID,
                                    stateCode = VALUE_VALID
                                ),
                                county = CountyResponseApi(
                                    fipsCode = VALUE_VALID,
                                    name = VALUE_VALID
                                ),
                                streetViewUrl = DEEP_LINK_VALID
                            ),
                            photos = listOf(PhotoResponseApi(url = "https://photo3.com")),
                            priceReducedAmount = VALUE_VALID,
                            primaryPhoto = PhotoResponseApi(url = "https://primaryphoto3.com"),
                            propertyId = ID_VALID_TEXT,
                            status = STATUS_FOR_SALE,
                            tags = listOf("luxury", "spacious"),
                            taxRecord = TaxRecordResponseApi(publicRecordId = VALUE_VALID),
                            virtualTours = listOf(
                                VrTourResponseApi(
                                    href = "https://virtualtour3.com",
                                    type = VALUE_VALID
                                )
                            )
                        )
                    )
                )
            )
        }

        override suspend fun getPropertyDetails(id: String): BaseSinglePropertyResponseApi {
            if (id != ID_VALID_TEXT) throw Exception(TestConstants.EXCEPTION_WRONG_PARAMS)
            return BaseSinglePropertyResponseApi(
                result = PropertyDetailsResponseApi(
                    branding = listOf(
                        BrandingResponseApi(
                            name = NAME_VALID,
                            photo = IMAGE_VALID,
                            type = ANY_TEXT,
                            accentColor = VALUE_VALID,
                            phone = PHONE_VALID
                        )
                    ),
                    comingSoonDate = SERVER_DATE_PATTERN,
                    description = DescriptionResponseApi(
                        baths = ANY_DIGITS,
                        bathsGtr1 = ANY_DIGITS,
                        bathsGtr3 = ANY_DIGITS,
                        bathsFull = ANY_DIGITS,
                        bathsHalf = ANY_DIGITS,
                        beds = ANY_DIGITS,
                        garage = ANY_DIGITS,
                        lotSqft = ANY_DIGITS,
                        name = NAME_VALID,
                        sqft = ANY_DIGITS,
                        pool = ANY_TEXT,
                        text = ANY_TEXT,
                        type = HouseType.TOWNHOMES,
                        rooms = ANY_DIGITS
                    ),
                    details = listOf(
                        DetailsResponseApi(
                            category = ANY_TEXT,
                            parentCategory = ANY_TEXT,
                            text = listOf(
                                ANY_TEXT,
                                ANY_TEXT,
                                ANY_TEXT
                            )
                        )
                    ),
                    lastUpdateDate = SERVER_DATE_PATTERN,
                    listDate = SERVER_DATE_PATTERN,
                    listPrice = ANY_DIGITS,
                    listingId = ANY_TEXT,
                    location = DetailedLocationResponseApi(
                        address = AddressResponseApi(
                            city = ANY_TEXT,
                            coordinate = CoordinateApi(lat = LATITUDE_VALID, lon = LONGITUDE_VALID),
                            line = ANY_TEXT,
                            postalCode = ANY_TEXT,
                            state = ANY_TEXT,
                            stateCode = ANY_TEXT
                        ),
                        country = ANY_TEXT,
                        line = ANY_TEXT,
                        state = ANY_TEXT,
                        stateCode = ANY_TEXT,
                        county = CountyResponseApi(
                            fipsCode = ANY_TEXT,
                            name = ANY_TEXT
                        )
                    ),
                    photos = listOf(
                        PhotoResponseApi(url = PHOTO_VALID),
                        PhotoResponseApi(url = PHOTO_VALID)
                    ),
                    schools = EducationFacilitiesResponseApi(
                        schools = listOf(
                            SchoolResponseApi(
                                id = ID_VALID_TEXT,
                                name = ANY_TEXT,
                                distanceInMiles = ANY_DOUBLE,
                                educationLevels = listOf(ANY_TEXT),
                                fundingType = ANY_TEXT,
                                grades = listOf(
                                    ANY_TEXT,
                                    ANY_TEXT,
                                    ANY_TEXT,
                                    ANY_TEXT,
                                    ANY_TEXT,
                                    ANY_TEXT
                                ),
                                studentCount = ANY_DIGITS,
                                studentTeacherRatio = ANY_DOUBLE,
                                parentRating = ANY_DIGITS,
                                rating = ANY_DIGITS,
                                reviewCount = ANY_DIGITS,
                                slug = ANY_TEXT,
                                slugId = ANY_TEXT
                            )
                        ),
                        total = 1
                    ),
                    source = SourceResponseApi(
                        agents = listOf(
                            AgentResponseApi(
                                officeName = ANY_TEXT
                            )
                        ),
                        id = ANY_TEXT,
                        planId = ANY_TEXT,
                        specId = ANY_TEXT,
                        type = ANY_TEXT
                    ),
                    propertyId = ANY_TEXT,
                    status = ANY_TEXT,
                    tags = listOf(ANY_TEXT, ANY_TEXT, ANY_TEXT),
                    virtualTours = listOf(
                        VrTourResponseApi(
                            href = URL_VALID,
                            type = ANY_TEXT
                        )
                    )
                )
            )
        }

        override suspend fun getPropertiesForRent(
            location: String?,
            house: String?,
            priceMin: Int?,
            priceMax: Int?,
            bedsMin: Int?,
            bedsMax: Int?,
            bathsMin: Int?,
            bathsMax: Int?,
            sqftMin: Int?,
            sqftMax: Int?,
            cats: Boolean?,
            dogs: Boolean?,
            page: Int?,
            sort: String?
        ): BaseHomeSearch<PropertyResponseApi> {
            return BaseHomeSearch(
                count = 3,
                results = listOf(
                    PropertyResponseApi(
                        advertisers = listOf(
                            AdvertiserResponseApi(
                                email = EMAIL_VALID,
                                fulfillment_id = ID_VALID_TEXT,
                                href = URL_VALID,
                                mls_set = ANY_TEXT,
                                name = NAME_VALID,
                                nrds_id = ID_VALID_TEXT,
                                office = OfficeResponseApi(
                                    name = NAME_VALID,
                                    href = URL_VALID,
                                    fulfillment_id = ID_VALID_TEXT,
                                    phones = listOf(
                                        PhoneResponseApi(
                                            ext = PHONE_EXT_VALID,
                                            number = PHONE_VALID,
                                            primary = true,
                                            trackable = true,
                                            type = ANY_TEXT
                                        )
                                    ),
                                    photo = PhotoResponseApi(url = PHOTO_VALID),
                                    email = EMAIL_VALID,
                                    mls_set = ANY_TEXT
                                ),
                                phones = listOf(
                                    PhoneResponseApi(
                                        ext = PHONE_EXT_VALID,
                                        number = PHONE_VALID,
                                        primary = true,
                                        trackable = true,
                                        type = ANY_TEXT
                                    )
                                )
                            )
                        ),
                        branding = listOf(
                            BrandingResponseApi(
                                name = NAME_VALID,
                                photo = IMAGE_VALID,
                                type = ANY_TEXT,
                                accentColor = VALUE_VALID,
                                phone = PHONE_VALID
                            )
                        ),
                        comingSoonDate = SERVER_DATE_PATTERN,
                        description = DescriptionResponseApi(
                            baths = 2,
                            bathsGtr1 = 1,
                            bathsGtr3 = 1,
                            bathsFull = 1,
                            bathsHalf = 1,
                            beds = 3,
                            garage = 2,
                            lotSqft = 2000,
                            name = NAME_VALID,
                            sqft = 1500,
                            pool = ANY_TEXT,
                            text = ANY_TEXT,
                            type = HouseType.APARTMENT,
                            rooms = 6
                        ),
                        flags = FlagsResponseApi(
                            isComingSoon = true,
                            isContingent = false,
                            isForeclosure = false,
                            isNewConstruction = true,
                            isNewListing = true,
                            isPending = false,
                            isPlan = false,
                            isPriceReduced = false,
                            isSubdivision = false
                        ),
                        lastUpdateDate = "2022-10-12T00:00:00",
                        listDate = "2022-10-12T00:00:00",
                        listPrice = PRICE_VALID,
                        listPriceMax = PRICE_VALID + 10000,
                        listPriceMin = PRICE_VALID - 10000,
                        listingId = ID_VALID_TEXT,
                        location = LocationResponseApi(
                            address = AddressResponseApi(
                                city = ADDRESS_VALID,
                                coordinate = CoordinateApi(LATITUDE_VALID, LONGITUDE_VALID),
                                line = ADDRESS_VALID,
                                postalCode = VALUE_VALID,
                                state = VALUE_VALID,
                                stateCode = VALUE_VALID
                            ),
                            county = CountyResponseApi(
                                fipsCode = VALUE_VALID,
                                name = VALUE_VALID
                            ),
                            streetViewUrl = DEEP_LINK_VALID
                        ),
                        photos = listOf(PhotoResponseApi(url = PHOTO_VALID)),
                        priceReducedAmount = VALUE_VALID,
                        primaryPhoto = PhotoResponseApi(url = PHOTO_VALID),
                        propertyId = ID_VALID_TEXT,
                        status = STATUS_FOR_RENT,
                        tags = listOf("new", "featured"),
                        taxRecord = TaxRecordResponseApi(publicRecordId = VALUE_VALID),
                        virtualTours = listOf(
                            VrTourResponseApi(
                                href = "https://virtualtour1.com",
                                type = VALUE_VALID
                            )
                        )
                    ),
                    PropertyResponseApi(
                        advertisers = listOf(
                            AdvertiserResponseApi(
                                email = EMAIL_INVALID,
                                fulfillment_id = ID_INVALID_TEXT,
                                href = "https://advertiser2.com",
                                mls_set = ANY_TEXT,
                                name = NAME_INVALID,
                                nrds_id = ID_INVALID_TEXT,
                                office = OfficeResponseApi(
                                    name = NAME_INVALID,
                                    href = "https://office2.com",
                                    fulfillment_id = ID_INVALID_TEXT,
                                    phones = listOf(
                                        PhoneResponseApi(
                                            ext = "456",
                                            number = PHONE_VALID,
                                            primary = false,
                                            trackable = false,
                                            type = "home"
                                        )
                                    ),
                                    photo = PhotoResponseApi(url = PHOTO_VALID),
                                    email = EMAIL_VALID,
                                    mls_set = ANY_TEXT
                                ),
                                phones = listOf(
                                    PhoneResponseApi(
                                        ext = "456",
                                        number = PHONE_VALID,
                                        primary = false,
                                        trackable = false,
                                        type = "home"
                                    )
                                )
                            )
                        ),
                        branding = listOf(
                            BrandingResponseApi(
                                name = NAME_INVALID,
                                photo = IMAGE_INVALID,
                                type = ANY_TEXT,
                                accentColor = VALUE_INVALID,
                                phone = PHONE_VALID
                            )
                        ),
                        comingSoonDate = "2022-11-12T00:00:00",
                        description = DescriptionResponseApi(
                            baths = 1,
                            bathsGtr1 = 0,
                            bathsGtr3 = 0,
                            bathsFull = 1,
                            bathsHalf = 0,
                            beds = 2,
                            garage = 1,
                            lotSqft = 1500,
                            name = NAME_INVALID,
                            sqft = 1000,
                            pool = ANY_TEXT,
                            text = ANY_TEXT,
                            type = HouseType.CONDOS,
                            rooms = 4
                        ),
                        flags = FlagsResponseApi(
                            isComingSoon = false,
                            isContingent = true,
                            isForeclosure = false,
                            isNewConstruction = false,
                            isNewListing = true,
                            isPending = true,
                            isPlan = false,
                            isPriceReduced = true,
                            isSubdivision = false
                        ),
                        lastUpdateDate = "2022-11-12T00:00:00",
                        listDate = "2022-11-12T00:00:00",
                        listPrice = PRICE_INVALID,
                        listPriceMax = PRICE_INVALID + 5000,
                        listPriceMin = PRICE_INVALID - 5000,
                        listingId = ID_INVALID_TEXT,
                        location = LocationResponseApi(
                            address = AddressResponseApi(
                                city = ADDRESS_INVALID,
                                coordinate = CoordinateApi(LATITUDE_INVALID, LONGITUDE_INVALID),
                                line = ADDRESS_INVALID,
                                postalCode = VALUE_INVALID,
                                state = VALUE_INVALID,
                                stateCode = VALUE_INVALID
                            ),
                            county = CountyResponseApi(
                                fipsCode = VALUE_INVALID,
                                name = VALUE_INVALID
                            ),
                            streetViewUrl = DEEP_LINK_INVALID
                        ),
                        photos = listOf(PhotoResponseApi(url = "https://photo2.com")),
                        priceReducedAmount = VALUE_INVALID,
                        primaryPhoto = PhotoResponseApi(url = "https://primaryphoto2.com"),
                        propertyId = ID_INVALID_TEXT,
                        status = STATUS_FOR_RENT,
                        tags = listOf("sale", "discounted"),
                        taxRecord = TaxRecordResponseApi(publicRecordId = VALUE_INVALID),
                        virtualTours = listOf(
                            VrTourResponseApi(
                                href = "https://virtualtour2.com",
                                type = VALUE_INVALID
                            )
                        )
                    ),
                    PropertyResponseApi(
                        advertisers = listOf(
                            AdvertiserResponseApi(
                                email = EMAIL_VALID,
                                fulfillment_id = ID_VALID_TEXT,
                                href = "https://advertiser3.com",
                                mls_set = ANY_TEXT,
                                name = NAME_VALID,
                                nrds_id = ID_VALID_TEXT,
                                office = OfficeResponseApi(
                                    name = NAME_VALID,
                                    href = "https://office3.com",
                                    fulfillment_id = ID_VALID_TEXT,
                                    phones = listOf(
                                        PhoneResponseApi(
                                            ext = "789",
                                            number = PHONE_VALID,
                                            primary = true,
                                            trackable = true,
                                            type = "work"
                                        )
                                    ),
                                    photo = PhotoResponseApi(url = PHOTO_VALID),
                                    email = EMAIL_VALID,
                                    mls_set = ANY_TEXT
                                ),
                                phones = listOf(
                                    PhoneResponseApi(
                                        ext = "789",
                                        number = PHONE_VALID,
                                        primary = true,
                                        trackable = true,
                                        type = "work"
                                    )
                                )
                            )
                        ),
                        branding = listOf(
                            BrandingResponseApi(
                                name = NAME_VALID,
                                photo = IMAGE_VALID,
                                type = ANY_TEXT,
                                accentColor = VALUE_VALID,
                                phone = PHONE_VALID
                            )
                        ),
                        comingSoonDate = "2022-12-12T00:00:00",
                        description = DescriptionResponseApi(
                            baths = 3,
                            bathsGtr1 = 2,
                            bathsGtr3 = 2,
                            bathsFull = 2,
                            bathsHalf = 1,
                            beds = 4,
                            garage = 3,
                            lotSqft = 3000,
                            name = NAME_VALID,
                            sqft = 2000,
                            pool = ANY_TEXT,
                            text = ANY_TEXT,
                            type = HouseType.TOWNHOMES,
                            rooms = 8
                        ),
                        flags = FlagsResponseApi(
                            isComingSoon = true,
                            isContingent = false,
                            isForeclosure = true,
                            isNewConstruction = false,
                            isNewListing = false,
                            isPending = false,
                            isPlan = true,
                            isPriceReduced = false,
                            isSubdivision = true
                        ),
                        lastUpdateDate = "2022-12-12T00:00:00",
                        listDate = "2022-12-12T00:00:00",
                        listPrice = PRICE_VALID * 2,
                        listPriceMax = (PRICE_VALID * 2) + 15000,
                        listPriceMin = (PRICE_VALID * 2) - 15000,
                        listingId = ID_VALID_TEXT,
                        location = LocationResponseApi(
                            address = AddressResponseApi(
                                city = ADDRESS_VALID,
                                coordinate = CoordinateApi(LATITUDE_UKRAINE, LONGITUDE_UKRAINE),
                                line = ADDRESS_VALID,
                                postalCode = VALUE_VALID,
                                state = VALUE_VALID,
                                stateCode = VALUE_VALID
                            ),
                            county = CountyResponseApi(
                                fipsCode = VALUE_VALID,
                                name = VALUE_VALID
                            ),
                            streetViewUrl = DEEP_LINK_VALID
                        ),
                        photos = listOf(PhotoResponseApi(url = "https://photo3.com")),
                        priceReducedAmount = VALUE_VALID,
                        primaryPhoto = PhotoResponseApi(url = "https://primaryphoto3.com"),
                        propertyId = ID_VALID_TEXT,
                        status = STATUS_FOR_RENT,
                        tags = listOf("luxury", "spacious"),
                        taxRecord = TaxRecordResponseApi(publicRecordId = VALUE_VALID),
                        virtualTours = listOf(
                            VrTourResponseApi(
                                href = "https://virtualtour3.com",
                                type = VALUE_VALID
                            )
                        )
                    )
                )
            )
        }
    }
}