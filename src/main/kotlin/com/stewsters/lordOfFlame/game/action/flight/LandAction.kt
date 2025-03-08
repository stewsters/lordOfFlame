package com.stewsters.com.stewsters.lordOfFlame.game.action.flight

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.lordOfFlame.map.HexMap


class LandAction(
    private val soldier: Soldier,
    hexMap: HexMap
) : Action {

    val currentHex = hexMap.grid.getByCubeCoordinate(soldier.pos).get()
    val tile = currentHex.satelliteData.get()
    val terrainType = tile.type
    override fun canDo(): Boolean {

        val flier = soldier.flier
        if (flier == null)
            return false


        if (terrainType == null || terrainType.blocksWalker || terrainType.height != flier.elevation - 1) {
            return false
        }

        if (tile.soldiers.filterNot { it == soldier }.isNotEmpty()) {
            // cant land on occupied terrain
            return false
        }

        return true
    }

    override fun doIt(): Int {
        // covert to landed
        soldier.flier = null

        return 30

    }

    override fun getDisplayName() = "Land"
    override fun getControl() = 'l'

}
