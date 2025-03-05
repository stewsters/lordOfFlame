package com.stewsters.com.stewsters.lordOfFlame.game.ai

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.action.walk.TurnInPlaceAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.walk.WalkForwardAction
import com.stewsters.lordOfFlame.map.HexMap

class MeleeAi : Ai {

    override fun getAction(
        soldier: Soldier,
        hexMap: HexMap
    ): Action? {


        // turn or advance


        // If there is a nearby city that is ungarrisoned, do that

        // if you

        // Ai objective - kill off other team,

        val potentialActions = listOf(
            TurnInPlaceAction(soldier, hexMap, true),
            TurnInPlaceAction(soldier, hexMap, false),
            WalkForwardAction(soldier, hexMap)
        ).filter { it.canDo() }

        return potentialActions.random()
    }

}