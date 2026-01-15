package com.stewsters.lordOfFlame.game.ai

import com.stewsters.lordOfFlame.TileData
import com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.lordOfFlame.game.action.walk.TurnInPlaceAction
import com.stewsters.lordOfFlame.game.action.walk.WalkForwardAction
import com.stewsters.lordOfFlame.game.components.Soldier
import com.stewsters.lordOfFlame.map.HexMap
import com.stewsters.lordOfFlame.maths.add
import kaiju.pathfinder.Path
import kaiju.pathfinder.findGenericPath
import org.hexworks.mixite.core.api.Hexagon

class MeleeAi : Ai {

    override fun getAction(
        soldier: Soldier,
        hexMap: HexMap
    ): Action? {


        // turn or advance


        // If there is a nearby city that is ungarrisoned, do that

        // Ai objective - kill off other team,

        val order = soldier.order
        if (order != null) {
            // navigate to the order location
            val start = hexMap.grid.getByCubeCoordinate(soldier.pos).get()
            val end = hexMap.grid.getByCubeCoordinate(order.positionId).get()
            val path = findMeleePath(hexMap, start, end)


            // if its forward walk the next path
//            if(path.)

            if ((path?.size ?: 0) > 1) {
                val nextNode = path!!.get(1)
                if (soldier.facing.offset.add(soldier.pos) == nextNode.cubeCoordinate) {
                    val walk = WalkForwardAction(soldier, hexMap)
                    if (walk.canDo())
                        return walk
                } else {
                    val turn = TurnInPlaceAction(soldier, hexMap, true)
                    if (turn.canDo())
                        return turn
                }
            }
            // otherwise turn
        }

        val potentialActions = listOf(
            TurnInPlaceAction(soldier, hexMap, true),
            TurnInPlaceAction(soldier, hexMap, false),
            WalkForwardAction(soldier, hexMap)
        ).filter { it.canDo() }

        return potentialActions.random()
    }


    fun findMeleePath(hexMap: HexMap, start: Hexagon<TileData>, end: Hexagon<TileData>): List<Hexagon<TileData>>? {
        val p = findGenericPath(
            cost = { x, y ->
                val set = listOf(x, y).sortedBy { it.gridX * hexMap.widthTiles + it.gridZ }
                val key = Pair(set.first(), set.last())
                if (hexMap.rivers.contains(key))
                    5.0
                if (hexMap.roads.contains(key))
                    1.0
                else
                    y.satelliteData.get().type?.traversalCost ?: 100.0
            },
            heuristic = { s, t -> hexMap.calc.calculateDistanceBetween(s, t).toDouble() },
            neighbors = {
                hexMap.grid.getNeighborsOf(it)
                    .filterNot { it.satelliteData.get().type?.blocksWalker ?: true }.toList()
            },
            start = start,
            end = end,
        )

        return when (p) {
            is Path.Success -> p.data
            else -> null
        }
    }
}

