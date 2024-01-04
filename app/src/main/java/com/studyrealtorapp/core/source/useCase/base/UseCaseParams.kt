package com.studyrealtorapp.core.source.useCase.base

abstract class UseCaseParams<T, P> {
    abstract suspend fun execute(data: P): T
}