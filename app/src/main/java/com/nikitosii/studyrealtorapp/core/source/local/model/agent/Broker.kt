package com.nikitosii.studyrealtorapp.core.source.local.model.agent

import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.BrokerResponseApi

data class Broker(
    val name: String?,
    val photo: Photo
) {
    companion object {
        fun from(data: BrokerResponseApi?): Broker = Broker(
            name = data?.name,
            photo = Photo.from(data?.photo)
        )
    }
}