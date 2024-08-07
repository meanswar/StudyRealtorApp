package com.nikitosii.studyrealtorapp.test

import java.util.LinkedList
import java.util.Queue
import java.util.Stack

class Detection {
    fun detection() {
        val list = LinkedList(mutableListOf(Any(), Any(), Any(), Any()))
        val hashSet = HashSet<Int>()
        var isLoop: Boolean = false
        list.forEach {
            if (hashSet.contains(it.hashCode())) {
                isLoop = true
                return@forEach
            }
            hashSet.add(it.hashCode())
        }
    }

    fun leftRotate(data: Array<Int>) {
        data.filter { true }.toTypedArray()
        val hashSet = HashSet<Int>()
        val string = "das as dads "
    }
}