package com.stewsters.com.stewsters.lordOfFlame.game.action

interface Action {

    fun canDo(): Boolean

    fun doIt(): Int

    fun getDisplayName(): String
    fun getControl():Char
}


