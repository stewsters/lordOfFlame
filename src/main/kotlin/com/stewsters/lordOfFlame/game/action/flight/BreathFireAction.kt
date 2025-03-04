package com.stewsters.com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.plus
import com.stewsters.lordOfFlame.map.HexMap

class BreathFireAction : Action {


    override fun doIt(
        soldier: Soldier,
        hexMap: HexMap
    ): Int {

        println("Fly Forward")
        val grid = hexMap.grid.getByCubeCoordinate(soldier.pos)

        // next grid
        val nextCoord = soldier.pos.plus(soldier.facing)
        val nextGrid = hexMap.grid.getByCubeCoordinate(nextCoord)

        if (!nextGrid.isPresent) {
            println("off the edge")
            return 0
        }

        // Do it
        nextGrid.get().satelliteData.get().soldiers.forEach { soldier -> hexMap.takeDamage( soldier, 30) }

        soldier.pos = nextCoord
        grid.get().satelliteData.get().soldiers.remove(soldier)
        nextGrid.get().satelliteData.get().soldiers.add(soldier)


        val flier = soldier.flier
        if (flier == null) {
            return 0
        }

        flier.airspeed = if (flier.airspeed > flier.averageAirspeed)
            flier.averageAirspeed - 1
        else if (flier.airspeed < flier.averageAirspeed)
            flier.averageAirspeed + 1
        else flier.airspeed

        // high airspeed reduces time to fly a hex
        return Math.round(100f / Math.max(flier.airspeed, 1))

    }
}