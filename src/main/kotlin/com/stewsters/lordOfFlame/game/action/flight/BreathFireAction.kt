package com.stewsters.com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.plus
import com.stewsters.lordOfFlame.map.HexMap

class BreathFireAction(
    private val soldier: Soldier,
    private val hexMap: HexMap
) : Action {

    val flier = soldier.flier
    val currentHex = hexMap.grid.getByCubeCoordinate(soldier.pos).get()

    // next grid
    val nextCoord = soldier.pos.plus(soldier.facing)
    val nextHex = hexMap.grid.getByCubeCoordinate(nextCoord)


    override fun canDo(): Boolean {

        if (flier == null) {
            return false
        }

        if (!nextHex.isPresent) {
            println("off the edge")
            return false
        }

        return true
    }


    override fun doIt(): Int {

        println("Fly Forward")

        // Burn em all
        var nextTileData = nextHex.get().satelliteData.get()
        hexMap.damageTile(nextTileData, 50, 30)

        soldier.pos = nextCoord
        currentHex.satelliteData.get().soldiers.remove(soldier)
        nextTileData.soldiers.add(soldier)

        flier!!.airspeed = if (flier.airspeed > flier.averageAirspeed)
            flier.airspeed - 1
        else if (flier.airspeed < flier.averageAirspeed)
            flier.airspeed + 1
        else flier.airspeed

        // high airspeed reduces time to fly a hex
        return Math.round(100f / Math.max(flier.airspeed, 1))

    }

    override fun getDisplayName(): String = "Breath Fire"
    override fun getControl(): Char = 'f'
}