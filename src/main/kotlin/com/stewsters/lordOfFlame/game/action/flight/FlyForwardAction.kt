package com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.lordOfFlame.game.components.Soldier
import com.stewsters.lordOfFlame.map.HexMap
import com.stewsters.lordOfFlame.maths.plus

class FlyForwardAction(
    private val soldier: Soldier,
    hexMap: HexMap
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

        // if the next tile is higher than us, we would crash into the ground
        if (flier == null) {
            return false
        }
        val tileOver = nextHex.get().satelliteData.get()
        if (flier.elevation <= (tileOver.type?.height ?: 0)){
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
