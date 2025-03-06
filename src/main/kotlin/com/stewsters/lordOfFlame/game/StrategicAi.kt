package com.stewsters.com.stewsters.lordOfFlame.game

import com.stewsters.lordOfFlame.map.HexMap
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
    val center = CubeCoordinate.fromCoordinates(xCenter, zCenter)

    // center position of army is calculated

    // if far, muster all units to that center
    allies.forEach { ally ->
        ally.order = Order(center)
    }

    // else move towards enemy, slower than slowest member

    // when enemy are scouted, form a line

    // when they approach, mob em


}


