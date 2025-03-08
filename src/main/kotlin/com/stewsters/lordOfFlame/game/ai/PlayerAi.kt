package com.stewsters.com.stewsters.lordOfFlame.game.ai

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.BankAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.BreathFireAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.ClimbAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.DiveAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.FlyForwardAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.LandAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.walk.MeleeAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.walk.TakeoffAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.walk.TurnInPlaceAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.walk.WalkForwardAction
import com.stewsters.lordOfFlame.game.action.flight.RoarAction
import com.stewsters.lordOfFlame.map.HexMap

// There should only be 1 player, may need to revisit if thats wrong
class PlayerAi : Ai {

    override fun getAction(
        soldier: Soldier,
        hexMap: HexMap
    ): Action? {
        val next = nextAction

        // Update that list of available actions
        if (next == null) {
            if (optionCache.isEmpty()) {
                getPossibleOptions(soldier, hexMap)
            }
        } else {
            optionCache = listOf()
        }

        nextAction = null
        return next
    }

    companion object {
        var optionCache = listOf<Action>()

        fun getPossibleOptions(
            mainCharacter: Soldier,
            hexMap: HexMap
        ) {
            val options = if (mainCharacter.flier != null) {
                listOf<Action>(
                    FlyForwardAction(mainCharacter, hexMap),
                    BankAction(mainCharacter, hexMap, right = true),
                    BankAction(mainCharacter, hexMap, right = false),
//                 TurnRightAction()
//                 TurnLeftAction()
                    DiveAction(mainCharacter, hexMap),
                    ClimbAction(mainCharacter, hexMap),
                    BreathFireAction(mainCharacter, hexMap),
                    RoarAction(mainCharacter, hexMap),
                    LandAction(mainCharacter, hexMap)
                )
            } else {
                listOf<Action>(
                    WalkForwardAction(mainCharacter, hexMap),
                    MeleeAction(mainCharacter, hexMap),
                    TurnInPlaceAction(mainCharacter, hexMap, right = true),
                    TurnInPlaceAction(mainCharacter, hexMap, right = false),
                    TakeoffAction(mainCharacter, hexMap)
                )
            }

            optionCache = options.filter { it.canDo() }
        }

        private var nextAction: Action? = null

        fun keyTyped(key: Char, mainCharacter: Soldier, hexMap: HexMap) {
            val potentialAction = if (mainCharacter.flier != null) {
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
                    'l' -> LandAction(mainCharacter, hexMap)
                    else -> null
                }
            } else {
                val walk = WalkForwardAction(mainCharacter, hexMap)
                when (key) {
                    'w' -> if (walk.canDo()) walk else MeleeAction(mainCharacter, hexMap)
                    'd' -> TurnInPlaceAction(mainCharacter, hexMap, right = true)
                    'a' -> TurnInPlaceAction(mainCharacter, hexMap, right = false)
                    'l' -> TakeoffAction(mainCharacter, hexMap)
                    else -> null
                }
            }
            if (potentialAction != null && potentialAction.canDo()) {
                nextAction = potentialAction
            }
        }

    }
}