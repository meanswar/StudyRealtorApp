package com.studyrealtorapp.util.annotation

@MustBeDocumented
@Target(allowedTargets = [AnnotationTarget.CLASS])
@Retention(value = AnnotationRetention.RUNTIME)
annotation class RequiresInject