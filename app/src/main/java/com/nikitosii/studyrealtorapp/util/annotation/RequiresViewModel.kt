package com.nikitosii.studyrealtorapp.util.annotation

import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import kotlin.reflect.KClass

@MustBeDocumented
@Target(allowedTargets = [AnnotationTarget.CLASS])
@Retention(value = AnnotationRetention.RUNTIME)
annotation class RequiresViewModel(val value : KClass<out BaseViewModel>)