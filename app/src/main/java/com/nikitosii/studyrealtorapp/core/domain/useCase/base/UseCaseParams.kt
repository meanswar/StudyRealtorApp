package com.nikitosii.studyrealtorapp.core.domain.useCase.base

abstract class UseCaseParams<T, P> {
    abstract suspend fun execute(data: P): T
}