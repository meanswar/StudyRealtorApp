package com.nikitosii.studyrealtorapp.util.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class GenerateRoomMigrations(vararg val rules: KClass<*>)