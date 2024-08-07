package com.nikitosii.studyrealtorapp.di

import com.nikitosii.studyrealtorapp.di.api.AgentsApiTest
import com.nikitosii.studyrealtorapp.di.api.ImageApiTest
import com.nikitosii.studyrealtorapp.di.api.PropertiesApiTest
import com.nikitosii.studyrealtorapp.di.modules.NetworkErrorModule
import dagger.Module

@Module(
    includes = [
        NetworkErrorModule::class,
        PropertiesApiTest::class,
        AgentsApiTest::class,
        ImageApiTest::class
    ]
)
class TestNetModule {
}