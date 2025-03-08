package com.stewsters.com.stewsters.lordOfFlame.game.action.walk

import com.stewsters.com.stewsters.lordOfFlame.game.Flier
import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.SoldierType
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.lordOfFlame.map.HexMap

class TakeoffAction(
    private val soldier: Soldier,
     hexMap: HexMap
) : Action {

    val currentHex = hexMap.grid.getByCubeCoordinate(soldier.pos).get()
    val tile = currentHex.satelliteData.get()
    val terrainType = tile.type
    override fun canDo(): Boolean {

        val flier = soldier.flier
        if (flier != null)
            return false

        if (soldier.soldierType != SoldierType.DRAGON) {
            // Only dragons fly right now
            return false
        }

        return true
    }

    override fun doIt(): Int {
        // covert to landed
        soldier.flier = Flier(
            airspeed = 1,
            elevation = (terrainType?.height ?: 0) + 1,
        )

        return 50
    }

    override fun getDisplayName() = "Land"
    override fun getControl() = 'l'

}