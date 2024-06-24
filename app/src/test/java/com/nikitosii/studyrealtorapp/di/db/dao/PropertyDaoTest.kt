package com.nikitosii.studyrealtorapp.di.db.dao

import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.util.TestConstants.EXCEPTION_ITEM_NOT_EXISTS
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT

object PropertyDaoTest : PropertyDao {

    private val properties = mutableListOf<PropertyEntity>()

    override fun insertProperty(property: PropertyEntity): Long {
        if (!properties.any {  it.propertyId == property.propertyId })
        properties.add(property)
        else {
            properties.removeIf { it.propertyId == property.propertyId }
            properties.add(property)
        }
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

    override fun getLocalProperty(id: String): PropertyEntity {
        return if (id == ID_VALID_TEXT) properties.first { it.propertyId == id }
        else throw Exception(EXCEPTION_ITEM_NOT_EXISTS)
    }


    override fun deleteAllProperties() {
        properties.removeAll { true }
    }

    override fun remove(id: String) {
        properties.removeIf { it.propertyId == id }
    }
}