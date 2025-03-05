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
        val nextTileData = nextGrid.get().satelliteData.get()

        val nextGridTileType = nextTileData.type ?: TerrainType.GRASSLAND
        if (nextGridTileType.blocksWalker) {
            println("Cant Walk There")
            return 0
        }

        if (nextTileData.soldiers.isNotEmpty()) {
            // if enemy, do an attack?
            val potentialTargets = nextTileData.soldiers.filter { it.faction != soldier.faction && it.flier == null }

            if (potentialTargets.isNotEmpty()) {
                val attack = soldier.soldierType.attacks.filter { it.range == 1 }.random()
                hexMap.damageTile(nextTileData, attack.damage, 10)
                return attack.timeCost
            }
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