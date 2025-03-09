package com.stewsters.com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.components.Soldier
import com.stewsters.com.stewsters.lordOfFlame.maths.plus
import com.stewsters.lordOfFlame.map.HexMap
import kotlin.math.max

class ClimbAction(
    private val soldier: Soldier,
    hexMap: HexMap
) : Action {

    val currentHex = hexMap.grid.getByCubeCoordinate(soldier.pos).get()

    // next grid
    val nextCoord = soldier.pos.plus(soldier.facing)
    val nextHex = hexMap.grid.getByCubeCoordinate(nextCoord)
    val flier = soldier.flier


    override fun canDo(): Boolean {
        if (!nextHex.isPresent) {
            println("off the edge")
            return false
        }
        if (flier == null || flier.elevation >= flier.maxHeight) {
            return false
        }
        return true
    }

    override fun doIt(): Int {

        println("Climb")


        // Do it
        soldier.pos = nextCoord
        currentHex.satelliteData.get().soldiers.remove(soldier)
        nextHex.get().satelliteData.get().soldiers.add(soldier)

        flier!!.elevation++
        flier.airspeed = max(1, flier.airspeed - 1)

        // high airspeed reduces time to fly a hex
        return Math.round(100f / Math.max(flier.airspeed, 1))

    }

    override fun getDisplayName(): String = "Climb"
    override fun getControl(): Char = 's'
}

