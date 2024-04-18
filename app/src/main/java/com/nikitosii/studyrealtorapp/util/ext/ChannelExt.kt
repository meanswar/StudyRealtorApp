package com.nikitosii.studyrealtorapp.util.ext

import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.channel.Status.ChannelState
import com.nikitosii.studyrealtorapp.core.source.channel.Status.ChannelState.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect

fun <T> T.asRefreshing() = Status.refreshing(this)
fun <T> T.asUpToDate() = Status.upToDate(this)
fun <T> T.asOnlyLocal() = Status.onlyLocal(this)

suspend fun <T: Any> T.sendToChannel(channel: MutableSharedFlow<T>) = channel.emit(this)
suspend fun <T> MutableSharedFlow<Status<T>>.setChannelState(state: ChannelState): Unit =
    this.collect { it.copy(channelState = state) }

suspend fun <T> MutableSharedFlow<Status<T>>.setAsRefreshing() = setChannelState(REFRESHING)
suspend fun <T> MutableSharedFlow<Status<T>>.setAsUpToDate() = setChannelState(UP_TO_DATE)
suspend fun <T> MutableSharedFlow<Status<T>>.setAsOnlyLocal() = setChannelState(ONLY_LOCAL)
