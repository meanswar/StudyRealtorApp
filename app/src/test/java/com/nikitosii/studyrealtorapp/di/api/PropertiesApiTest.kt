package com.nikitosii.studyrealtorapp.di.api

import com.nikitosii.studyrealtorapp.TestConstants.ADDRESS_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.ADDRESS_VALID
import com.nikitosii.studyrealtorapp.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.DEEP_LINK_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.DEEP_LINK_VALID
import com.nikitosii.studyrealtorapp.TestConstants.EMAIL_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.EMAIL_VALID
import com.nikitosii.studyrealtorapp.TestConstants.ID_INVALID_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.IMAGE_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.IMAGE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.LATITUDE_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.LATITUDE_UKRAINE
import com.nikitosii.studyrealtorapp.TestConstants.LATITUDE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.LONGITUDE_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.LONGITUDE_UKRAINE
import com.nikitosii.studyrealtorapp.TestConstants.LONGITUDE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.NAME_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.NAME_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHOTO_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PRICE_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.PRICE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.STATUS_FOR_RENT
import com.nikitosii.studyrealtorapp.TestConstants.STATUS_FOR_SALE
import com.nikitosii.studyrealtorapp.TestConstants.VALUE_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.VALUE_VALID
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
                                                number = PHONE_INVALID,
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
                                            number = PHONE_INVALID,
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
                                    phone = PHONE_INVALID
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

        override suspend fun getPropertyDetails(id: String): BaseSinglePropertyResponseApi =
            BaseSinglePropertyResponseApi(
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
                        pool = "None",
                        text = "Spacious townhouse with modern amenities",
                        type = HouseType.TOWNHOMES,
                        rooms = 8
                    ),
                    details = listOf(
                        DetailsResponseApi(
                            category = "Interior Features",
                            parentCategory = "Property Details",
                            text = listOf(
                                "Hardwood floors",
                                "Granite countertops",
                                "Walk-in closets"
                            )
                        )
                    ),
                    lastUpdateDate = "2022-12-12T00:00:00",
                    listDate = "2022-12-12T00:00:00",
                    listPrice = 550000,
                    listingId = "12345ABC",
                    location = DetailedLocationResponseApi(
                        address = AddressResponseApi(
                            city = "New York",
                            coordinate = CoordinateApi(lat = 40.7128, lon = -74.0060),
                            line = "123 Main St",
                            postalCode = "10001",
                            state = "New York",
                            stateCode = "NY"
                        ),
                        country = "USA",
                        line = "123 Main St",
                        state = "New York",
                        stateCode = "NY",
                        county = CountyResponseApi(
                            fipsCode = "36061",
                            name = "New York"
                        )
                    ),
                    photos = listOf(
                        PhotoResponseApi(url = "https://photo3.com"),
                        PhotoResponseApi(url = "https://photo4.com")
                    ),
                    schools = EducationFacilitiesResponseApi(
                        schools = listOf(
                            SchoolResponseApi(
                                id = "school1",
                                name = "PS 123",
                                distanceInMiles = 0.5,
                                educationLevels = listOf("Elementary"),
                                fundingType = "Public",
                                grades = listOf("K", "1", "2", "3", "4", "5"),
                                studentCount = 500,
                                studentTeacherRatio = 15.0,
                                parentRating = 4,
                                rating = 5,
                                reviewCount = 100,
                                slug = "ps-123",
                                slugId = "ps-123-id"
                            )
                        ),
                        total = 1
                    ),
                    source = SourceResponseApi(
                        agents = listOf(
                            AgentResponseApi(
                                officeName = "Real Estate Agency"
                            )
                        ),
                        id = "source123",
                        planId = "plan123",
                        specId = "spec123",
                        type = "MLS"
                    ),
                    propertyId = "PROP123",
                    status = "Active",
                    tags = listOf("new", "luxury", "spacious"),
                    virtualTours = listOf(
                        VrTourResponseApi(
                            href = "https://virtualtour3.com",
                            type = "3D"
                        )
                    )
                )
            )

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
                                            number = PHONE_INVALID,
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
                                        number = PHONE_INVALID,
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
                                phone = PHONE_INVALID
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