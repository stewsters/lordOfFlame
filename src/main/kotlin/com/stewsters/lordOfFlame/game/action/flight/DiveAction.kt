package com.stewsters.com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.plus
import com.stewsters.lordOfFlame.map.HexMap
import kotlin.math.min

class DiveAction : Action {


    override fun doIt(
        soldier: Soldier,
        hexMap: HexMap
    ): Int {

        println("Climb")
        val grid = hexMap.grid.getByCubeCoordinate(soldier.pos)

        // next grid
        val nextCoord = soldier.pos.plus(soldier.facing)
        val nextGrid = hexMap.grid.getByCubeCoordinate(nextCoord)

        if (!nextGrid.isPresent) {
            println("off the edge")
            return 0
        }

        val flier = soldier.flier
        if (flier == null || flier.elevation <= nextGrid.get().satelliteData.get().type!!.height) {
            return 0
        }

        // Do it
        soldier.pos = nextCoord
        grid.get().satelliteData.get().soldiers.remove(soldier)
        nextGrid.get().satelliteData.get().soldiers.add(soldier)

        flier.elevation--
        flier.airspeed = min(flier.airspeed + 1, flier.maxAirspeed)

        // high airspeed reduces time to fly a hex
        return Math.round(100f / Math.max(flier.airspeed, 1))

    }
}