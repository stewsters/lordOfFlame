package com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.lordOfFlame.game.components.Soldier
import com.stewsters.lordOfFlame.map.HexMap
import com.stewsters.lordOfFlame.maths.plus

class RallyAction(
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

        println("Rally while Fly Forward")
        // Do it

        // set the army rally point to where you are
        soldier.commander?.rally(hexMap, currentHex.cubeCoordinate, soldier.facing)

        // fly forward
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

    override fun getDisplayName(): String = "Rally Troops"
    override fun getControl(): Char = 't'
}
