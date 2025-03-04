package com.stewsters.com.stewsters.lordOfFlame.game.ai

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.lordOfFlame.map.HexMap

interface Ai {

    fun getAction(soldier: Soldier, hexMap: HexMap): Action?
}