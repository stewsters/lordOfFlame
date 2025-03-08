package com.stewsters.lordOfFlame.map

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.lordOfFlame.TileData
import com.stewsters.lordOfFlame.types.TerrainType
import kaiju.datastructure.PriorityQueue
import kaiju.pathfinder.Path
import kaiju.pathfinder.findGenericPath
import org.hexworks.mixite.core.api.Hexagon
import org.hexworks.mixite.core.api.HexagonalGrid
import org.hexworks.mixite.core.api.HexagonalGridBuilder
import org.hexworks.mixite.core.api.HexagonalGridCalculator
import kotlin.math.max

// Can build a city on this
val cityTiles = listOf(TerrainType.FOREST, TerrainType.GRASSLAND)
val waterTiles = listOf(TerrainType.DEEP_WATER, TerrainType.SHALLOW_WATER)
val farmland = listOf(TerrainType.GRASSLAND)


class HexMap(builder: HexagonalGridBuilder<TileData>) {
    val grid: HexagonalGrid<TileData> = builder.build()
    val calc: HexagonalGridCalculator<TileData> = builder.buildCalculatorFor(grid)

    val widthTiles = builder.getGridWidth()
    val heightTiles = builder.getGridHeight()
    val radius = builder.getRadius()

    // part of the map
    val cities = mutableListOf<Hexagon<TileData>>()
    val roads = mutableSetOf<Pair<Hexagon<TileData>, Hexagon<TileData>>>()
    val rivers = mutableSetOf<Pair<Hexagon<TileData>, Hexagon<TileData>>>()
    val soldiers = mutableSetOf<Soldier>()

    // This is used to control whos turn is next
    val turnQueue = PriorityQueue<Soldier>(100) { t, o -> t.nextTurn.compareTo(o.nextTurn) }


    // For roads
    fun getPath(start: Hexagon<TileData>, end: Hexagon<TileData>): List<Hexagon<TileData>>? {
        val p = findGenericPath(
            cost = { x, y ->
                val set = listOf(x, y).sortedBy { it.gridX * widthTiles + it.gridZ }
                val key = Pair(set.first(), set.last())
                if (rivers.contains(key))
                    5.0
                if (roads.contains(key))
                    1.0
                else
                    y.satelliteData.get().type?.traversalCost ?: 100.0
            },
            heuristic = { s, t -> calc.calculateDistanceBetween(s, t).toDouble() },
            neighbors = { grid.getNeighborsOf(it).toList() },
            start = start,
            end = end,
        )

        return when (p) {
            is Path.Success -> p.data
            else -> null
        }
    }

    fun add(soldier: Soldier) {
        val hexagon = grid.getByCubeCoordinate(soldier.pos).get()
        val tileData = hexagon.satelliteData.get()
        tileData.soldiers.add(soldier)
        turnQueue.add(soldier)
        soldiers.add(soldier)
    }


    fun takeTurn() {
        var turnsCounter = 0

        while (turnsCounter < 10) {
            val turnTaker = turnQueue.poll()
            if (turnTaker == null) {
                return // No one to process
            }

            // Ask ai for an action
            val nextAction = turnTaker.soldierType.defaultAi.getAction(turnTaker, this)

            // if we have one, do it
            if (nextAction != null && nextAction.canDo()) {
                val cost = nextAction.doIt()
                turnTaker.nextTurn += cost
                turnsCounter++
            }
            // if we are still alive, add em back into the queue
            turnQueue.add(turnTaker)

            if (nextAction == null) {
                break // this means its a player
            }
        }
    }

    fun damageTile(tileData: TileData, damage: Int, morale: Int) {

        tileData.soldiers.forEach { soldier ->
            soldier.hp -= damage
            soldier.morale = max(0, soldier.morale - morale)
        }
        val dead = tileData.soldiers.filter { it -> it.hp <= 0 }

        dead.forEach { soldier ->
            turnQueue.remove(soldier)
            tileData.soldiers.remove(soldier)
            soldiers.remove(soldier)
        }
    }

    fun getSoldiers(): List<Soldier> {
        return soldiers.toList()
    }

}
