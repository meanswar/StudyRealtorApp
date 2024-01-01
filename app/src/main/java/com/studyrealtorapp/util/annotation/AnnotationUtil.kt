package com.studyrealtorapp.util.annotation

import com.studyrealtorapp.flow.base.BaseViewModel

object AnnotationUtil {
    fun findViewModelClass(any: Any): Class<out BaseViewModel> {
        return any.javaClass.getAnnotation(RequiresViewModel::class.java)!!.value.java
    }

    fun hasInject(any: Any) = any.javaClass.isAnnotationPresent(RequiresInject::class.java)

    fun hasViewModel(any: Any) = any.javaClass.isAnnotationPresent(RequiresViewModel::class.java)
}