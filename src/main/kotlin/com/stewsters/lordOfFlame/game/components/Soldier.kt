package com.stewsters.lordOfFlame.game.components

import com.stewsters.lordOfFlame.game.Facing
import com.stewsters.lordOfFlame.game.ai.Ai
import org.hexworks.mixite.core.api.CubeCoordinate

class Soldier(
    var pos: CubeCoordinate,  // id of Hexagon<TileData>
    var facing: Facing,
    val faction: Faction,
    val soldierType: SoldierType,
    var hp: Int = soldierType.maxHp,
    var morale: Int = soldierType.maxMorale,
    var nextTurn: Int,
    val ai: Ai = soldierType.defaultAi,
    var order: Order? = null,
    var flier: Flier? = if (soldierType == SoldierType.DRAGON)
        Flier(airspeed = 2, elevation = 2)
    else null
)