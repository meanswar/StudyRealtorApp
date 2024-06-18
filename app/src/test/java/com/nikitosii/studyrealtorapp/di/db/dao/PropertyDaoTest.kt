package com.nikitosii.studyrealtorapp.di.db.dao

import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity

object PropertyDaoTest : PropertyDao {

    private val properties = mutableListOf<PropertyEntity>()
    override fun insertProperty(property: PropertyEntity): Long {
        properties.add(property)
        return properties.indexOf(property).toLong()
    }

    override fun insertProperties(properties: List<PropertyEntity>): List<Long> {
        this.properties.addAll(properties)
        val ids = this.properties.mapIndexed { index, propertyEntity ->
            if (properties.contains(propertyEntity)) index else -1
        }
        return ids.filter { it != -1 }.map { it.toLong() }
    }

    override fun getProperties(): List<PropertyEntity> = properties

    override fun getFavoriteProperties(ids: List<String>): List<PropertyEntity> =
        properties.filter { it.favorite && ids.contains(it.propertyId) }


    override fun getLocalProperties(ids: List<String>): List<PropertyEntity> =
        properties.filter { ids.contains(it.propertyId) }

    override fun getLocalProperty(id: String): PropertyEntity =
        properties.first { it.propertyId == id }

    override fun deleteAllProperties() {
        properties.removeAll { true }
    }

    override fun remove(id: String) {
        properties.removeIf { it.propertyId == id }
    }
}