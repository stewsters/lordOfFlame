package com.stewsters.com.stewsters.lordOfFlame.game.ai

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
import com.stewsters.com.stewsters.lordOfFlame.game.components.Soldier
import com.stewsters.com.stewsters.lordOfFlame.maths.calculateRingFrom
import com.stewsters.lordOfFlame.TileData
import com.stewsters.lordOfFlame.game.action.flight.RoarAction
import com.stewsters.lordOfFlame.map.HexMap
import org.hexworks.mixite.core.api.Hexagon


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
                recalculateView(soldier, hexMap)
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


        var visible = mutableSetOf<Hexagon<TileData>>()

        // Recalculate the view of the character
        fun recalculateView(mainCharacter: Soldier, hexMap: HexMap) {
            // reca

            val playerHex = hexMap.grid.getByCubeCoordinate(mainCharacter.pos).get()
            println(playerHex.cubeCoordinate)

            //  hexMap.calc.calculateRingFrom(playerHex, 2)
            val ring = calculateRingFrom(mainCharacter.pos, 4)
            println(ring)
            visible.clear()

//            visible.addAll(ring.map { hexMap.grid.getByCubeCoordinate(it)}.filter { it.isPresent }.map { it.get() }.toSet())
//            visible.add(hexMap.grid.getByCubeCoordinate(mainCharacter.pos).get())
//            return

            val height = mainCharacter.flier?.elevation ?: 0

            visible.clear()
            ring.forEach { edge ->
                val edgeHex = hexMap.grid.getByCubeCoordinate(edge)
                if (edgeHex.isPresent)
                    for (point in hexMap.calc.drawLine(playerHex, edgeHex.get())) {
                        visible.add(point)
                        if ((point.satelliteData.get().type?.height ?: 0) >= height) {
                            println(point.cubeCoordinate.toString())
                            // TODO: this doesnt break cleanly
                            return@forEach
                        }

                    }
            }

        }
    }

}