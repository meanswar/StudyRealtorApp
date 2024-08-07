package com.nikitosii.studyrealtorapp.di.db.dao

import com.nikitosii.studyrealtorapp.core.source.db.dao.ImageDataDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.ImageDataEntity

object ImageDataDaoTest: ImageDataDao {

    private val imageList = mutableListOf<ImageDataEntity>()
    override fun insertImageData(entity: ImageDataEntity) {
        imageList.add(entity)
    }

    override fun getImage(id: String): ImageDataEntity? = imageList.firstOrNull { it.id == id }


    override fun deleteAll() {
        imageList.removeAll { true }
    }
}