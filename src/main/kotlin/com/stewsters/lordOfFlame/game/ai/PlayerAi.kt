package com.stewsters.com.stewsters.lordOfFlame.game.ai

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.BankAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.BreathFireAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.ClimbAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.DiveAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.FlyForwardAction
import com.stewsters.lordOfFlame.game.action.flight.RoarAction
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
        private var nextAction: Action? = null

        fun keyTyped(key: Char, mainCharacter: Soldier, hexMap: HexMap) {
            val potentialAction =
                when (key) {
                    'w' -> FlyForwardAction(mainCharacter, hexMap)
                    'e' -> BankAction(mainCharacter, hexMap, right = true)
                    'q' -> BankAction(mainCharacter, hexMap, right = false)
//                'd' -> TurnRightAction()
//                'a' -> TurnLeftAction()
                    'x' -> DiveAction(mainCharacter, hexMap)
                    's' -> ClimbAction(mainCharacter, hexMap)
                    'f' -> BreathFireAction(mainCharacter, hexMap)
                    'r' -> RoarAction(mainCharacter, hexMap)
                    else -> null
                }

            if (potentialAction != null && potentialAction.canDo()) {
                nextAction = potentialAction
            }
        }

    }
}