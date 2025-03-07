package com.stewsters.com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.plus
import com.stewsters.lordOfFlame.map.HexMap

class FlyForwardAction(
    private val soldier: Soldier,
    private val hexMap: HexMap
) : Action {

    val currentHex = hexMap.grid.getByCubeCoordinate(soldier.pos).get()
    val nextCoord = soldier.pos.plus(soldier.facing)
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

        println("Fly Forward")
        // Do it
        soldier.pos = nextCoord
        currentHex.satelliteData.get().soldiers.remove(soldier)
        nextHex.get().satelliteData.get().soldiers.add(soldier)

        flier!!.airspeed = if (flier.airspeed > flier.averageAirspeed)
            flier.airspeed - 1
        else if (flier.airspeed < flier.averageAirspeed)
            flier.airspeed + 1
        else flier.airspeed

        // high airspeed reduces time to fly a hex
        return Math.round(100f / Math.max(flier.airspeed, 1))
    }

    override fun getDisplayName(): String = "Fly Forward"
    override fun getControl(): Char = 'w'
}
