package com.stewsters.com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.plus
import com.stewsters.lordOfFlame.map.HexMap

class BankRightAction : Action {


    override fun doIt(
        soldier: Soldier,
        hexMap: HexMap
    ): Int {

        // remove from map
        println("Bank Right")

        val newFacing = soldier.facing.rotateRight()

        val grid = hexMap.grid.getByCubeCoordinate(soldier.pos)

        // next grid
        val nextCoord = soldier.pos.plus(newFacing);
        val nextGrid = hexMap.grid.getByCubeCoordinate(nextCoord)

        if (!nextGrid.isPresent) {
            println("off the edge")
            return 0
        }

        val flier = soldier.flier
        if (flier == null) {
            return 0
        }

        // Do it
        soldier.facing = newFacing
        soldier.pos = nextCoord
        grid.get().satelliteData.get().soldiers.remove(soldier)
        nextGrid.get().satelliteData.get().soldiers.add(soldier)

        flier.airspeed = if (flier.airspeed > flier.averageAirspeed)
            flier.averageAirspeed - 1
        else flier.airspeed

        return Math.round(100f / Math.max(flier.airspeed, 1))

    }
}