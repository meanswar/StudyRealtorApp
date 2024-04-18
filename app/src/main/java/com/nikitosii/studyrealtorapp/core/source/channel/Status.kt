package com.nikitosii.studyrealtorapp.core.source.channel

data class Status<T>(val obj: T?, val channelState: ChannelState) {

    enum class ChannelState {
        REFRESHING,
        UP_TO_DATE,
        ONLY_LOCAL
    }

    companion object {
        fun <T> refreshing(data: T) = Status(data, ChannelState.REFRESHING)
        fun <T> upToDate(data: T) = Status(data, ChannelState.UP_TO_DATE)
        fun <T> onlyLocal(data: T) = Status(data, ChannelState.ONLY_LOCAL)
    }
}