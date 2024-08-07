package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.source.AgentResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Agent(val officeName: String? = null) : Parcelable {
    companion object {
        fun from(data: AgentResponseApi?): Agent = Agent(data?.officeName)
    }
}