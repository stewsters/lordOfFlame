package com.stewsters.com.stewsters.lordOfFlame.game.action.walk

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.lordOfFlame.map.HexMap

class TurnInPlaceAction(
    private val soldier: Soldier,
    private val hexMap: HexMap,
    private val right: Boolean
) : Action {

    override fun canDo(): Boolean {
        return true
    }

    override fun doIt(
    ): Int {

        println("Turn ${right}")

        soldier.facing = if (right)
            soldier.facing.rotateRight()
        else
            soldier.facing.rotateLeft()

        return soldier.soldierType.groundTurnSpeed

    }
}