package com.stewsters.com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.components.Soldier
import com.stewsters.com.stewsters.lordOfFlame.maths.plus
import com.stewsters.lordOfFlame.map.HexMap

class BankAction(
    private val soldier: Soldier,
    hexMap: HexMap,
    val right: Boolean
) : Action {

    val newFacing = if (right) soldier.facing.rotateRight() else soldier.facing.rotateLeft()

    val currentHex = hexMap.grid.getByCubeCoordinate(soldier.pos).get()

    // next grid
    val nextCoord = soldier.pos.plus(newFacing)
    val nextHex = hexMap.grid.getByCubeCoordinate(nextCoord)
    val flier = soldier.flier

    override fun canDo(): Boolean {

        if (!nextHex.isPresent) {
            println("off the edge")
            return false
        }

        if (flier == null) {
            return false
        }
        return true
    }


    override fun doIt(): Int {

        // remove from map
        println("Bank Right")

        // Do it
        soldier.facing = newFacing
        soldier.pos = nextCoord
        currentHex.satelliteData.get().soldiers.remove(soldier)
        nextHex.get().satelliteData.get().soldiers.add(soldier)

        flier!!.airspeed = if (flier.airspeed > flier.averageAirspeed)
            flier.airspeed - 1
        else flier.airspeed

        return Math.round(100f / Math.max(flier.airspeed, 1))

    }

    override fun getDisplayName(): String = "Bank ${if (right) "Right" else "Left"}"
    override fun getControl(): Char = if (right) 'e' else 'q'
}