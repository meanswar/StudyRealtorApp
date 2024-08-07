package com.nikitosii.studyrealtorapp.core.source.local.model.profile

import android.net.Uri
import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.db.entity.ProfileEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val name: String?,
    val surname: String?,
    val email: String?,
    val phone: String?,
    val photo: Uri?
): Parcelable {

    fun toEntity(): ProfileEntity = ProfileEntity(
        name = name,
        surname = surname,
        email = email,
        phone = phone,
        photo = photo
    )
    companion object {
        fun from(data: ProfileEntity?): Profile = Profile(
            name = data?.name,
            surname = data?.surname,
            email = data?.email,
            phone = data?.phone,
            photo = data?.photo
        )
    }
}