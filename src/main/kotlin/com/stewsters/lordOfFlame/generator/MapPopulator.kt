package com.stewsters.com.stewsters.lordOfFlame.generator

import com.stewsters.com.stewsters.lordOfFlame.game.Facing
import com.stewsters.com.stewsters.lordOfFlame.game.Faction
import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.SoldierType
import com.stewsters.lordOfFlame.map.HexMap

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

    return dragon
}