package com.stewsters.com.stewsters.lordOfFlame.game.ai

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.lordOfFlame.map.HexMap

// There should only be 1 player, may need to revisit if thats wrong
class PlayerAi : Ai {

    override fun getAction(
        soldier: Soldier,
        hexMap: HexMap
    ): Action? {

        val next = nextAction
        nextAction = null
        return next
    }

    companion object {
        var nextAction: Action? = null
    }
}