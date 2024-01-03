package com.x.log.layout

interface MessageStrategy{
    fun createTag(tag: String?): String
}