package com.stewsters.lordOfFlame.generator

import com.stewsters.lordOfFlame.game.Facing
import com.stewsters.lordOfFlame.game.components.Faction
import com.stewsters.lordOfFlame.game.components.Soldier
import com.stewsters.lordOfFlame.game.components.SoldierType
import com.stewsters.lordOfFlame.map.HexMap
import com.stewsters.lordOfFlame.maths.getNeighbors
import kotlin.random.Random

fun populate(hexMap: HexMap): Soldier {

    val numCities = hexMap.cities.size


    val sortedCities = hexMap.cities.sortedBy { it.centerX }


    val startingCity = sortedCities.first()
    val dragon = Soldier(
        pos = startingCity.cubeCoordinate,
        facing = Facing.NORTHEAST,
        faction = Faction.PLAYER,
        soldierType = SoldierType.DRAGON,
        nextTurn = 0
    )
    hexMap.add(dragon)

    sortedCities.drop(1).forEachIndexed { i, it ->

        it.cubeCoordinate.getNeighbors().forEach { coord ->

            val solder = Soldier(
                pos = coord,
                facing = Facing.entries.random(),
                faction = if (i < (numCities / 2)) Faction.PLAYER else Faction.ENEMY,
                soldierType = SoldierType.entries.filter { it != SoldierType.DRAGON }.random(),
//            hp = TODO(),
//            morale = TODO(),
                nextTurn = Random.nextInt(100)
//            ai = TODO(),
//            order = TODO(),
//            flier = TODO(),
            )
            hexMap.add(solder)
        }


    }

    return dragon
}