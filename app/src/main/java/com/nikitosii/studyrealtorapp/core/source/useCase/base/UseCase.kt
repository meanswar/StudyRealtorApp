package com.nikitosii.studyrealtorapp.core.source.useCase.base

abstract class UseCase<T> {
    abstract suspend fun execute(): T
}