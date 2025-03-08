package com.stewsters.com.stewsters.lordOfFlame.game.action.walk

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.plus
import com.stewsters.lordOfFlame.map.HexMap
import com.stewsters.lordOfFlame.types.TerrainType

class WalkForwardAction(
    private val soldier: Soldier,
     hexMap: HexMap
) : Action {


    val currentHex = hexMap.grid.getByCubeCoordinate(soldier.pos).get()

    // next grid
    val nextCoord = soldier.pos.plus(soldier.facing)
    val nextGex = hexMap.grid.getByCubeCoordinate(nextCoord)

    // Make sure we can walk on it
    val nextTileData = nextGex.get().satelliteData.get()

    val nextGridTileType = nextTileData.type ?: TerrainType.GRASSLAND


    override fun canDo(): Boolean {
        if (!nextGex.isPresent) {
            println("off the edge")
            return false
        }

        if (nextGridTileType.blocksWalker) {
            println("Cant Walk There")
            return false
        }
        if (nextTileData.soldiers.isNotEmpty()) {
            "Units in target space"
            return false
        }

        return true
    }

    override fun doIt(): Int {

        println("Walk Forward")

        // Do it
        soldier.pos = nextCoord
        currentHex.satelliteData.get().soldiers.remove(soldier)
        nextGex.get().satelliteData.get().soldiers.add(soldier)

        // TODO: multiply by terrain speed?
        return soldier.soldierType.groundSpeed

    }

    override fun getDisplayName(): String = "Walk Forward"
    override fun getControl(): Char = 'w'

}