package com.stewsters.com.stewsters.lordOfFlame.game.action

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.lordOfFlame.map.HexMap

interface Action {

    fun doIt(soldier: Soldier, hexMap: HexMap): Int
}


