package com.stewsters.com.stewsters.lordOfFlame.generator

import com.stewsters.com.stewsters.lordOfFlame.game.Facing
import com.stewsters.com.stewsters.lordOfFlame.game.Faction
import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.SoldierType
import com.stewsters.lordOfFlame.map.HexMap
import kotlin.random.Random

fun populate(hexMap: HexMap): Soldier {

    val startingCity = hexMap.cities.first()
    val dragon = Soldier(
        pos = startingCity.cubeCoordinate,
        facing = Facing.NORTHEAST,
        faction = Faction.PLAYER,
        soldierType = SoldierType.DRAGON,
        nextTurn = 0
    )
    hexMap.add(dragon)

    hexMap.cities.drop(1).forEachIndexed {i, it->
        val solder = Soldier(
            pos = it.cubeCoordinate,
            facing = Facing.entries.random(),
            faction = Faction.entries.get(i % 2),
            soldierType = SoldierType.SPEARMEN,
//            hp = TODO(),
//            morale = TODO(),
            nextTurn = Random.nextInt(100)
//            ai = TODO(),
//            order = TODO(),
//            flier = TODO(),
        )
        hexMap.add(solder)
    }

    return dragon
}