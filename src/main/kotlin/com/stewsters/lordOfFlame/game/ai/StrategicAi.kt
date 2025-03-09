package com.stewsters.com.stewsters.lordOfFlame.game.ai

import com.stewsters.com.stewsters.lordOfFlame.game.components.Faction
import com.stewsters.com.stewsters.lordOfFlame.game.components.Order
import com.stewsters.lordOfFlame.map.HexMap
import com.stewsters.lordOfFlame.types.TerrainType
import org.hexworks.mixite.core.api.CubeCoordinate


fun assignActions(faction: Faction, hexMap: HexMap) {

    val soldiers = hexMap.getSoldiers()

    val allies = soldiers.filter { it.faction == faction }.toMutableList()
    val enemies = soldiers.filter { it.faction != faction }.toMutableList()

    // cities
//        hexMap.cities.forEach {
//
//        }

    // for each city, assign a guard based on how close it is


    // all other units are drafted into army

    val xCenter = allies.sumOf { it.pos.gridX } / allies.size
    val zCenter = allies.sumOf { it.pos.gridZ } / allies.size
    val absCenter = CubeCoordinate.fromCoordinates(xCenter, zCenter)
    val absCenterGrid = hexMap.grid.getByCubeCoordinate(absCenter).get()

    // find closest spot
    val meetup = hexMap.grid.hexagons
        .filter { it.satelliteData.get().type == TerrainType.GRASSLAND }
        .minBy { hexMap.calc.calculateDistanceBetween(it, absCenterGrid) }
        .cubeCoordinate

    // center position of army is calculated

    // if far, muster all units to that center
    allies.forEach { ally ->
        ally.order = Order(meetup)
    }

    // else move towards enemy, slower than slowest member

    // when enemy are scouted, form a line

    // when they approach, mob em


}


