package com.stewsters.com.stewsters.lordOfFlame.game.action.walk

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.plus
import com.stewsters.lordOfFlame.map.HexMap

class MeleeAction(
    private val soldier: Soldier,
    private val hexMap: HexMap
) : Action {

    val currentHex = hexMap.grid.getByCubeCoordinate(soldier.pos)

    val nextCoord = soldier.pos.plus(soldier.facing)
    val nextHex = hexMap.grid.getByCubeCoordinate(nextCoord)

    // Make sure we can walk on it
    val nextTileData = nextHex.get().satelliteData.get()

    //    val nextGridTileType = nextTileData.type ?: TerrainType.GRASSLAND
    val potentialTargets = nextTileData.soldiers.filter { it.faction != soldier.faction && it.flier == null }


    override fun canDo(): Boolean {
        if (!nextHex.isPresent) {
            println("off the edge")
            return false
        }

        if (potentialTargets.isEmpty()) {
            println("No one to attack")
            return false
        }

        return true
    }

    override fun doIt(): Int {
        println("Attack Forward")
        val attack = soldier.soldierType.attacks.filter { it.range == 1 }.random()
        hexMap.damageTile(nextTileData, attack.damage, 10)
        return attack.timeCost
    }

    override fun getDisplayName(): String = "Attack Forward"
    override fun getControl() = 'w'
}