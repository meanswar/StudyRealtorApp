package com.nikitosii.studyrealtorapp.core.source.useCase.base

import androidx.annotation.WorkerThread
import com.nikitosii.studyrealtorapp.util.Flow

abstract class FlowUseCase<T> {
    @WorkerThread
    abstract fun execute(): Flow<T>
}