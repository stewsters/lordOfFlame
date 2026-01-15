package com.stewsters.lordOfFlame.game.ai

import com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.lordOfFlame.game.components.Soldier
import com.stewsters.lordOfFlame.map.HexMap

interface Ai {

    fun getAction(soldier: Soldier, hexMap: HexMap): Action?
}