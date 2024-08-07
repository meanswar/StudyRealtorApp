package com.nikitosii.studyrealtorapp.core.domain.useCase.base

abstract class UseCase<T> {
    abstract suspend fun execute(): T
}