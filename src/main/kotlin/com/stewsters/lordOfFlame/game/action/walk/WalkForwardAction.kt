package com.stewsters.com.stewsters.lordOfFlame.game.action.walk

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.plus
import com.stewsters.lordOfFlame.map.HexMap
import com.stewsters.lordOfFlame.types.TerrainType

class WalkForwardAction : Action {


    override fun doIt(
        soldier: Soldier,
        hexMap: HexMap
    ): Int {

        println("Walk Forward")
        val grid = hexMap.grid.getByCubeCoordinate(soldier.pos)

        // next grid
        val nextCoord = soldier.pos.plus(soldier.facing)
        val nextGrid = hexMap.grid.getByCubeCoordinate(nextCoord)

        if (!nextGrid.isPresent) {
            println("off the edge")
            return 0
        }

        // Make sure we can walk on it
        val nextGridTileType = nextGrid.get().satelliteData?.get()?.type ?: TerrainType.GRASSLAND

        if (nextGridTileType.blocksWalker) {
            println("Cant Walk There")
            return 0
        }

        // Do it
        soldier.pos = nextCoord
        grid.get().satelliteData.get().soldiers.remove(soldier)
        nextGrid.get().satelliteData.get().soldiers.add(soldier)

        // TODO: some transitions should cost more
        return soldier.soldierType.groundSpeed

    }
}