package com.x.log.layout

interface TagStrategy{
    fun createTag(tag: String?): String
}